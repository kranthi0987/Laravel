package com.sanjay.laravel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.sanjay.laravel.models.LoginPassRequest;
import com.sanjay.laravel.models.LoginResponse;
import com.sanjay.laravel.retroFit.ApiClient;
import com.sanjay.laravel.retroFit.ApiInterface;
import com.sanjay.laravel.utils.SessionManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import static com.sanjay.laravel.MyApplication.getContext;

public class LoginActivity extends AppCompatActivity {
    public static ApiInterface apiInterface;


    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private CheckBox remember_me;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLinkToRegister = findViewById(R.id.btnLinkToRegisterScreen);
        remember_me = findViewById(R.id.remember_me);


        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
// Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                Boolean check;
                check = remember_me.isChecked();
                // Check for empty data in the form
                if (testing()) {

                } else {
                    if (!email.isEmpty() && !password.isEmpty()) {
                        // login user
                        logincall(email, password, check);
                    } else {
                        // Prompt user to enter credentials
                        Toast.makeText(getApplicationContext(),
                                "Please enter the credentials!", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    public boolean testing() {
        logincall("test@gmail.com", "123456", true);
        return true;
    }

    public void logincall(String email, String password, boolean check) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(LoginActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        final LoginPassRequest logindata = new LoginPassRequest();
        logindata.setEmail(email);
        logindata.setPassword(password);
        logindata.setRememberMe(check);

        Observable<LoginResponse> observable = apiInterface.LOGIN_RESPONSE_OBSERVABLE(logindata)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<LoginResponse>() {

            @Override
            public void onError(Throwable e) {
                progressDoalog.hide();
                Toast.makeText(getContext(), "error" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                progressDoalog.hide();
                Intent i = new Intent(getApplicationContext(),
                        DashboardActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginResponse Listdata) {
                session.setToken(Listdata.getAccessToken());
            }

        });

    }

}
