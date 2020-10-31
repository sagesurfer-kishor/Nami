package com.sagesurfer.nami.tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.library.DeviceInfo;
import com.sagesurfer.nami.network.MakeCall;
import com.sagesurfer.nami.network.Urls_;
import com.sagesurfer.nami.secure.KeyMaker_;
import com.sagesurfer.nami.storage.preferences.Preferences;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author Monika M (monikam@sagesurfer.com)
 * Created on 13/03/2018
 * Last Modified on
 */

public class PerformLogoutTask {

    private static final String TAG = PerformLogoutTask.class.getSimpleName();

    public static void logout(Activity activity) {
        Logout logout = new Logout(activity);
        logout.execute();
    }

    private static class Logout extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog mProgressDialog;
        @SuppressLint("StaticFieldLeak")
        final Activity activity;

        Logout(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(activity);
            }

            // for quote layout showing
            Preferences.save("showQuote", false);

            mProgressDialog.setMessage("Logout Successfully");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HashMap<String, String> keyMap = KeyMaker_.getKey();
            RequestBody logoutBody = new FormBody.Builder()
                    .add(General.USERID, Preferences.get(General.LOGIN_USER_ID))
                    .add(General.KEY, keyMap.get(General.KEY))
                    .add(General.TOKEN, keyMap.get(General.TOKEN))
                    .add(General.IMEI, DeviceInfo.getDeviceId(activity))
                    .build();
            try {
                MakeCall.post(Preferences.get(General.DOMAIN) + "/" + Urls_.LOGOUT_URL, logoutBody, TAG, activity.getApplicationContext(), activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
            }
        }
    }




}
