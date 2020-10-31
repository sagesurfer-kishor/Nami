package com.sagesurfer.nami.Activity;

;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.Actions_;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.network.NetworkCall_;
import com.sagesurfer.nami.network.Urls_;
import com.sagesurfer.nami.parser.GetJson_;
import com.sagesurfer.nami.parser.Login_;
import com.sagesurfer.nami.storage.preferences.Preferences;
import com.sagesurfer.nami.tasks.PerformLoginTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import okhttp3.RequestBody;

public class SplashScreenActivity extends RuntimePermissionsActivity {

    private ArrayList<HashMap<String, String>> instanceList;

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS = 20;

    private Activity activity;
    String debug = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences.initialize(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Preferences.initialize(getApplicationContext());

        getLine();
    }


    @Override
    public void onPermissionsGranted(int requestCode) {

        String s = Preferences.get(General.IS_LOGIN);
        if (Preferences.contains(General.IS_LOGIN) && Preferences.get(General.IS_LOGIN).equalsIgnoreCase("1")) {
            mainIntent();
        } else {
            loginIntent();
        }
    }

    private void mainIntent() {
        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
        Intent forgotIntent = new Intent(getApplicationContext(), LoginActivity.class);
        forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
        startActivity(forgotIntent, bundleAnimation);
        finish();
    }

    private void loginIntent() {
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


    private void getLine() {
        String response = null;
        try {
            response = new PerformHotline(debug, this).execute().get();
            Log.e("Login", "" + response);
            if (response != null) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

                if (jsonObject.has("details")) {
                    JsonObject object = jsonObject.get("details").getAsJsonObject();
                    String number = object.get(General.HOTLINE).getAsString();
                    Preferences.save(General.HOTLINE, number);
                }
            }

        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

}


