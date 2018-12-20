package com.sanjay.laravel.views.activties;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.sanjay.laravel.R;
import com.sanjay.laravel.app.AppConstants;
import com.sanjay.laravel.app.MyApplication;
import com.sanjay.laravel.models.products.ProductsResponse;
import com.sanjay.laravel.network.retroFit.ApiClient;
import com.sanjay.laravel.network.retroFit.ApiInterface;
import com.sanjay.laravel.utils.SessionManager;
import com.sanjay.laravel.views.adapters.ProductDataAdapter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import java.util.ArrayList;
import java.util.List;

import static com.sanjay.laravel.app.MyApplication.getContext;
import static com.sanjay.laravel.utils.CommonUsedMethods.logoutUser;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static ApiInterface apiInterface;
    private TextView txtName;
    private TextView txtEmail;
    //    private Button btnLogout;
    public SessionManager session;
    Realm realm;
    String token = null;

    private RecyclerView mRecyclerView;

    private ProductDataAdapter mProductAdapter;

    private ArrayList<ProductsResponse> mProductArraylist;

    private FloatingSearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSearchView = findViewById(R.id.floating_search_view);
        Realm realm = Realm.getDefaultInstance();


//        txtName = findViewById(R.id.name);
//        txtEmail = findViewById(R.id.email);
//        btnLogout = findViewById(R.id.btnLogout);
        // session manager
        session = new SessionManager(DashboardActivity.this);
        token = "Bearer " + session.getToken();
//        viewusercall();
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mSearchView.attachNavigationDrawerToMenuButton(drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        ImageView nav_avatar = hView.findViewById(R.id.nav_avatar);
        TextView nav_user = hView.findViewById(R.id.nav_name);
        TextView nav_email = hView.findViewById(R.id.nav_email);
        nav_user.setText(session.getName());
        nav_email.setText(session.getEmail());
        Glide.with(MyApplication.getContext()).load(AppConstants.BASE_URL + session.getAvatar()).into(nav_avatar);
        navigationView.setNavigationItemSelectedListener(this);

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                filter(newQuery);
                Toast.makeText(getApplicationContext(), "" + oldQuery + "" + newQuery, Toast.LENGTH_SHORT).show();
                //get suggestions based on newQuery

                //pass them on to the search view
                //   mSearchView.swapSuggestions();
            }
        });

        // Logout button click event
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                logoutUser();
//            }
//        });
        initRecyclerView();
        loadproductlist();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
    }

    private void initRecyclerView() {

        mRecyclerView = findViewById(R.id.product_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initviews() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tracking) {
            Intent i = new Intent(getApplicationContext(),
                    TrackingActivity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_maps) {
            Intent i = new Intent(getApplicationContext(),
                    MapActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(getApplicationContext(),
                    ProfileActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            logoutUser();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadproductlist() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(DashboardActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();


        Observable<List<ProductsResponse>> observable = apiInterface.PRODUCTS_RESPONSE_OBSERVABLE()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<List<ProductsResponse>>() {

            @Override
            public void onError(Throwable e) {
                progressDoalog.hide();
                MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(DashboardActivity.this)
                        .setTitle("Error!")
                        .setDescription(e.toString())
                        .build();
                dialog.show();
                Toast.makeText(getContext(), "error" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                progressDoalog.hide();


//                MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(DashboardActivity.this)
//                        .setTitle("Error!")
//                        .setDescription("user deatils retrived")
//                        .build();
//                dialog.show();
//                Toast.makeText(getContext(), "user details retrieved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ProductsResponse> Listdata) {
                handleResponse(Listdata);


            }

        });

    }

    private void handleResponse(List<ProductsResponse> productsResponseList) {

        mProductArraylist = new ArrayList<>(productsResponseList);
        mProductAdapter = new ProductDataAdapter(mProductArraylist, DashboardActivity.this);
        mRecyclerView.setAdapter(mProductAdapter);
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<ProductsResponse> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ProductsResponse s : filterdNames) {
            //if the existing elements contains the search input
            if (s.getName().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mProductAdapter.filterList(filterdNames);
    }


}
