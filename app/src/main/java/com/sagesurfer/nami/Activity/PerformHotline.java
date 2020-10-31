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
import com.sagesurfer.nami.tasks.PerformLoginTask;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class PerformHotline extends AsyncTask<Void, Void, String> {
    private static final String TAG = PerformLoginTask.class.getSimpleName();

    private final String debug;
    @SuppressLint("StaticFieldLeak")
    private final Activity activity;

    public PerformHotline(String debug, Activity activity) {

        this.debug = debug;
        this.activity = activity;
    }


    @Override
    protected String doInBackground(Void... params) {
        HashMap<String, String> keyMap = KeyMaker_.getKey();
        String info = DeviceInfo.get(activity);
        RequestBody loginBody = null;
        loginBody = new FormBody.Builder()
                .add(General.DEBUG, debug)
                .build();
        try {
            return MakeCall.post(Preferences.get(General.DOMAIN) + Urls_.MOBILE_HELPLINE_NO, loginBody, TAG, activity.getApplicationContext(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}