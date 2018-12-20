package com.sanjay.laravel.views.activties;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.sanjay.laravel.R;
import com.sanjay.laravel.app.AppConstants;
import com.sanjay.laravel.app.MyApplication;
import com.sanjay.laravel.databinding.ActivityProfileBinding;
import com.sanjay.laravel.models.userModel.UserSuccessResponse;
import com.sanjay.laravel.network.retroFit.ApiClient;
import com.sanjay.laravel.network.retroFit.ApiInterface;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.sanjay.laravel.app.MyApplication.session;
import static com.sanjay.laravel.utils.CommonUsedMethods.logoutUser;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static ApiInterface apiInterface;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        ImageView nav_avatar = hView.findViewById(R.id.nav_avatar);
        TextView nav_user = hView.findViewById(R.id.nav_name);
        TextView nav_email = hView.findViewById(R.id.nav_email);
        nav_user.setText(session.getName());
        nav_email.setText(session.getEmail());
        Glide.with(MyApplication.getContext()).load(AppConstants.BASE_URL + session.getAvatar()).into(nav_avatar);
        navigationView.setNavigationItemSelectedListener(this);
        renderProfile();
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

    /**
     * Renders user profile data
     */
    private void renderProfile() {
        UserSuccessResponse model = new UserSuccessResponse();
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ProfileActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<UserSuccessResponse> observable = apiInterface.USER_SUCCESS_RESPONSE_OBSERVABLE(session.getToken_type() + " " + session.getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<UserSuccessResponse>() {

            @Override
            public void onError(Throwable e) {
//                Toast.makeText(getContext(), "error" + e, Toast.LENGTH_SHORT).show();
                Log.i("login", "onError: " + e);
                MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(ProfileActivity.this)
                        .setTitle("Error!")
                        .setIcon(R.drawable.error)
                        .withIconAnimation(true)
                        .withDialogAnimation(true)
                        .withDarkerOverlay(true)
                        .setHeaderColor(R.color.background)
                        .setDescription(e.toString())
                        .build();

                dialog.show();
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserSuccessResponse Listdata) {
                progressDoalog.hide();
                model.setEmail(Listdata.getEmail());
                model.setUserName(Listdata.getUserName());
                model.setUserAvatar(Listdata.getUserAvatar());
                model.setUserPhoneNumber(Listdata.getUserPhoneNumber());
                model.setUserOtherDetails(Listdata.getUserOtherDetails());
//                model.setAddressId(Listdata.getAddressId());
                binding.setUser(model);
            }

        });

    }

    public class MyClickHandlers {

        public void onFabClicked(View view) {
            Toast.makeText(getApplicationContext(), "FAB clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}


