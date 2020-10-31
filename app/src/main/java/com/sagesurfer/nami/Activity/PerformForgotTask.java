package com.sagesurfer.nami.Activity;

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

public class PerformForgotTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = PerformForgotTask.class.getSimpleName();
    private final String emailid;
    @SuppressLint("StaticFieldLeak")
    private final Activity activity;

    public PerformForgotTask(String emailid, Activity activity) {
        this.emailid = emailid;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String, String> keyMap = KeyMaker_.getKey();
        String info = DeviceInfo.get(activity);
        RequestBody loginBody = null;
        loginBody = new FormBody.Builder()
                .add(General.EMAIL, emailid)
                .build();
        try {
            return MakeCall.post(Preferences.get(General.DOMAIN) + Urls_.MOBILE_FORGOT_PASSWORD, loginBody, TAG, activity.getApplicationContext(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
