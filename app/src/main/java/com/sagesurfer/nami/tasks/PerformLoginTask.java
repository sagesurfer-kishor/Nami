package com.sagesurfer.nami.tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.library.DeviceInfo;
import com.sagesurfer.nami.network.MakeCall;
import com.sagesurfer.nami.network.Urls_;
import com.sagesurfer.nami.secure.KeyMaker_;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class PerformLoginTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = PerformLoginTask.class.getSimpleName();
    private final String email;
    private final String password;

    @SuppressLint("StaticFieldLeak")
    private final Activity activity;

    public PerformLoginTask(String email, String password, Activity activity) {
        this.email = email;
        this.password = password;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String, String> keyMap = KeyMaker_.getKey();
        String info = DeviceInfo.get(activity);
        RequestBody loginBody = null;
        loginBody = new FormBody.Builder()
                .add(General.EMAIL, email)
                .add(General.PASSWORD, password)
                .build();
        try {
            return MakeCall.post(Preferences.get(General.DOMAIN) + Urls_.LOGIN_URL, loginBody, TAG, activity.getApplicationContext(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
