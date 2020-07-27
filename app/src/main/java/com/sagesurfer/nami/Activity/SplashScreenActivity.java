package com.sagesurfer.nami.Activity;

;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;


import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashScreenActivity extends RuntimePermissionsActivity {

    private ArrayList<HashMap<String, String>> instanceList;

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences.initialize(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Preferences.initialize(getApplicationContext());
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
        Intent forgotIntent = new Intent(getApplicationContext(), LoginActivity.class);
        forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
        startActivity(forgotIntent, bundleAnimation);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Preferences.save("debug", "1");
        Preferences.save("info", "1");
        Preferences.save("error", "1");

        new CountDownTimer(1900, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                SplashScreenActivity.super.requestAppPermissions(new
                                String[]{
                                android.Manifest.permission.INTERNET,
                                android.Manifest.permission.ACCESS_NETWORK_STATE,
                                android.Manifest.permission.ACCESS_WIFI_STATE
                        }, R.string
                                .runtime_permissions_txt
                        , REQUEST_PERMISSIONS);
            }
        }.start();
    }
}


