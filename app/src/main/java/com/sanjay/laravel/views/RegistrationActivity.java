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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.sanjay.laravel.R;
import com.sanjay.laravel.models.registrationModel.RegisterRequest;
import com.sanjay.laravel.models.registrationModel.RegisterSuccessResponse;
import com.sanjay.laravel.retroFit.ApiClient;
import com.sanjay.laravel.retroFit.ApiInterface;
import com.sanjay.laravel.utils.SessionManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.regex.Pattern;

import static com.sanjay.laravel.MyApplication.getContext;

public class RegistrationActivity extends AppCompatActivity {

    public static ApiInterface apiInterface;

    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputnumber;
    private ProgressDialog pDialog;
    private SessionManager session;
    String[] domain = null;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        inputFullName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputnumber = findViewById(R.id.user_number);
        btnRegister = findViewById(R.id.btnRegister);
        btnLinkToLogin = findViewById(R.id.btnLinkToLoginScreen);

//        //adding validation to edittexts
        //    awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
//        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.nameerror);
//        awesomeValidation.addValidation(this, R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.nameerror);
//        awesomeValidation.addValidation(this, R.id.editTextDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);
//        awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);
//        if (awesomeValidation.validate()) {
//            Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();

        //process the data further
//        }

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegistrationActivity.this,
                    DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String user_number = inputnumber.getText().toString().trim();
                domain = email.split(Pattern.quote("@"));
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !user_number.isEmpty()) {
                    Registercall(name, email, password, user_number);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void Registercall(String name, String email, String password, String number) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(RegistrationActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName(name);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setPasswordConfirmation(password);
        registerRequest.setUserPhoneNumber(number);


        Observable<RegisterSuccessResponse> observable = apiInterface.REGISTER_SUCCESS_RESPONSE_OBSERVABLE(registerRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<RegisterSuccessResponse>() {

            @Override
            public void onError(Throwable e) {
                progressDoalog.hide();
                MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(RegistrationActivity.this)
                        .setTitle("Error!")
                        .setDescription(e.toString())
                        .build();
                dialog.show();
                Toast.makeText(getContext(), "error" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                progressDoalog.hide();
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                i.putExtra("domain", domain[1]);
                startActivity(i);
                finish();
                Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RegisterSuccessResponse registerSuccessResponse) {


            }

        });

    }
}


