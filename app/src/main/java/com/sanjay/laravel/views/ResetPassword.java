package com.sanjay.laravel.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sanjay.laravel.R;
import com.sanjay.laravel.models.forgetPasswordModel.ForgetPasswordRequest;
import com.sanjay.laravel.models.forgetPasswordModel.ForgetPasswordResponse;
import com.sanjay.laravel.retroFit.ApiClient;
import com.sanjay.laravel.retroFit.ApiInterface;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.sanjay.laravel.MyApplication.getContext;

public class ResetPassword extends AppCompatActivity {
    public static ApiInterface apiInterface;
    protected static Button resetpassword, btnLinkToRegister;
    protected static EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resetpassword = findViewById(R.id.btn_resetpassword);
        btnLinkToRegister = findViewById(R.id.btnLinkToRegisterScreen);
        email = findViewById(R.id.email_forget);
        final String emailstring = email.getText().toString().trim();
        // Link to Register Screen
        resetpassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (!emailstring.isEmpty()) {
                    // login user
                    Forgetpassword(emailstring);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter your email", Toast.LENGTH_LONG)
                            .show();
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

    public void Forgetpassword(String email) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ResetPassword.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        final ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setEmail("");

        Observable<ForgetPasswordResponse> observable = apiInterface.FORGET_PASSWORD_RESPONSE_OBSERVABLE(forgetPasswordRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<ForgetPasswordResponse>() {

            @Override
            public void onError(Throwable e) {
                progressDoalog.hide();
                Toast.makeText(getContext(), "error" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                progressDoalog.hide();
                Toast.makeText(getContext(), "Email sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ForgetPasswordResponse Listdata) {

            }

        });

    }

}
