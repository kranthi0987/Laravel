package com.sanjay.laravel.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.sanjay.laravel.R;

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
                assert finalJ != null;
                if (finalJ.contains("gmail")) {
                    Intent mailClient = new Intent(Intent.ACTION_VIEW);
                    mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
                    startActivity(mailClient);
                } else {
                    String url = "www." + finalJ + ".com";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
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
