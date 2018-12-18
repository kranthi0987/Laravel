package com.sanjay.laravel.views;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sanjay.laravel.R;
import com.sanjay.laravel.network.ApplicationUtility;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        checkPerm();
    }
    private void checkPerm() {
        PermissionListener permissionlistener = new PermissionListener() {

            @Override
            public void onPermissionGranted() {
//                Toast.makeText(Splash_activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                Timer t = new Timer();
                boolean checkConnection = new ApplicationUtility().checkConnection(SplashActivity.this);
                if (checkConnection) {
                    t.schedule(new splash(), 3000);
                    Toast.makeText(SplashActivity.this,
                            "Internet permission granted", 3000).show();
                } else {
                    Toast.makeText(SplashActivity.this,
                            "connection not found...plz check connection", 3000).show();
                    t.schedule(new splash(), 3000);
                }
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(SplashActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

            }

        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TedPermission.with(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .check();
        }
    }

    class splash extends TimerTask {
        @Override
        public void run() {
            Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
            finish();
            startActivity(i);
        }
    }
}
