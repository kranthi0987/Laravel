package com.sanjay.laravel;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {

    public Button checkmail, loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        String j = null;
        if (b != null) {
            j = (String) b.get("domain");
        }

        checkmail = findViewById(R.id.checkmail);
        loginbtn = findViewById(R.id.loginbtn);
        final String finalJ = j;
        checkmail.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (finalJ.contains("gmail")) {
                    PackageManager manager = getPackageManager();
                    Intent i = manager.getLaunchIntentForPackage("com.google.android.gmail");
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("www." + finalJ));
                    startActivity(i);
                }
                finish();
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}
