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

public class PerformRegisterTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = PerformRegisterTask.class.getSimpleName();
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phone;
    private final String password;
    private final String terms;
    private final String debug;

    @SuppressLint("StaticFieldLeak")
    private final Activity activity;

    public PerformRegisterTask(String firstname, String lastname, String email, String phone, String password, String terms, String debug, Activity activity) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.terms = terms;
        this.debug = debug;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String, String> keyMap = KeyMaker_.getKey();
        String info = DeviceInfo.get(activity);
        RequestBody loginBody = null;
        loginBody = new FormBody.Builder()
                .add(General.FIRST_NAME, firstname)
                .add(General.LAST_NAME, lastname)
                .add(General.EMAIL, email)
                .add(General.PHONE, phone)
                .add(General.PASSWORD, password)
                .add(General.DEBUG, debug)
                .add(General.TERMS, terms)
                .build();
        try {
            return MakeCall.post(Preferences.get(General.DOMAIN) + Urls_.MOBILE_USER_REGISTRATION, loginBody, TAG, activity.getApplicationContext(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
