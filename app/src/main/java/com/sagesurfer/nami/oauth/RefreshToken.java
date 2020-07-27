package com.sagesurfer.nami.oauth;

import android.content.Context;
import android.os.AsyncTask;


import com.sagesurfer.nami.constant.Oauth;
import com.sagesurfer.nami.model.Token_;
import com.sagesurfer.nami.network.Call_;
import com.sagesurfer.nami.network.Urls_;
import com.sagesurfer.nami.storage.preferences.OauthPreferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.RequestBody;



public class RefreshToken {

    private static final String TAG = RefreshToken.class.getSimpleName();

    public RefreshToken(Context _context) {
        OauthPreferences.initialize(_context);
    }

    public Token_ getRefreshToken(String user_name, String password, String domain, Context _context) {

        RequestBody authBody = new FormBody.Builder()
                .add("client_id", user_name)
                .add("client_secret", password)
                .add("refresh_token", OauthPreferences.get(Oauth.REFRESH_TOKEN))
                .add("scope", "android")
                .add("grant_type", "refresh_token")
                .build();

        try {
            Call_ call_ = new Call_(domain + Urls_.TOKEN, authBody, TAG, _context);
            String json = call_.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                return JsonParser_.token_(jsonObject);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
