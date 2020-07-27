package com.sagesurfer.nami.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.constant.Oauth;
import com.sagesurfer.nami.library.DeviceInfo;
import com.sagesurfer.nami.logger.Logger;
import com.sagesurfer.nami.model.Token_;
import com.sagesurfer.nami.oauth.RefreshToken;
import com.sagesurfer.nami.secure.KeyMaker_;
import com.sagesurfer.nami.secure.UrlEncoder_;
import com.sagesurfer.nami.storage.preferences.OauthPreferences;
import com.sagesurfer.nami.storage.preferences.Preferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author Monika M (monikam@sagesurfer.com)
 * Created on 13/03/2018
 * Last Modified on
 */

@SuppressWarnings("ConstantConditions")
public class NetworkCall_ {

    private static final String TAG = NetworkCall_.class.getSimpleName();
    private static RefreshToken refreshToken;

    public static RequestBody make(HashMap<String, String> map, String url, String tag, Context _context) {
        /*String device_id = "";
        TelephonyManager tManager = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        if (tManager != null) {
            device_id = tManager.getDeviceId();
        }*/
        //HashMap<String, String> requestMap = new HashMap<>();
        //String deviceId = Settings.Secure.getString(_context.getContentResolver(), Settings.Secure.ANDROID_ID);
        map.put(General.IMEI, DeviceInfo.getDeviceId(_context));

        refreshToken = new RefreshToken(_context);
        Request request = new Request.Builder()
                .url(url)
                .post(makeBody(map))
                .tag(tag)
                .build();
        String body = bodyToString(request);
        Logger.debug(tag, url + "?" + body, _context);
        String token = getToken(_context);
        if (token != null) {
            return finalBody(body, token);
        }
        return null;
    }

    public static RequestBody make(HashMap<String, String> map, String url, String tag,
                                   Context _context, Activity activity) {
        //HashMap<String, String> requestMap = new HashMap<>();
        /*String deviceID = DeviceInfo.getDeviceId(activity);
        String deviceMAC = DeviceInfo.getDeviceMAC(activity);
        String deviceIDMEI = DeviceInfo.getImei(activity);
        String deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);*/

        map.put(General.IMEI, DeviceInfo.getDeviceId(activity));
        map.put(General.VERSION, DeviceInfo.getVersion());
        map.put(General.MODELNO, DeviceInfo.getDeviceName());
        refreshToken = new RefreshToken(_context);
        Request request = new Request.Builder()
                .url(url)
                .post(makeBody(map))
                .tag(tag)
                .build();
        String body = bodyToString(request);
        Logger.debug(tag, url + "?" + body, _context);
        String token = getToken(_context);
        if (token != null) {
            return finalBody(body, token);
        }
        return null;
    }

    public static RequestBody make_one(HashMap<String, String> map, String url, String tag,
                                       Context _context, Activity activity) {
        map.put(General.IMEI, DeviceInfo.getDeviceId(activity));
        Request request = new Request.Builder()
                .url(url)
                .post(makeBody(map))
                .tag(tag)
                .build();
        String body = bodyToString(request);
        Logger.debug(tag, url + "?" + body, _context);

        return null;
    }

    public static String post(String url, RequestBody formBody, String tag, Context _context, Activity activity) throws Exception {
        MyCall myCall = new MyCall(url, formBody, tag, _context, activity);
        return myCall.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    @SuppressLint("StaticFieldLeak")
    private static final class MyCall extends AsyncTask<Void, Void, String> {
        final String url;
        final String tag;
        final RequestBody formBody;
        final Context _context;
        final Activity activity;

        MyCall(String url, RequestBody formBody, String tag, Context _context, Activity activity) {
            this.url = url;
            this.formBody = formBody;
            this.tag = tag;
            this._context = _context;
            this.activity = activity;
        }

        @Override
        protected String doInBackground(Void... params) {
            //OkHttpClient client = new OkHttpClient();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .tag(tag)
                    .build();
            Logger.debug(tag, url + "?" + bodyToString(request), _context);
            String s = url + "?" + bodyToString(request);

            try {
                Response response = client.newCall(request).execute();
                String res = response.body().string();
                if (res.trim().length() > 0) {
                    JsonElement jelement = new JsonParser().parse(res);
                    JsonObject jsonObject1 = jelement.getAsJsonObject();
                    JSONObject jsonObject = new JSONObject(res);
                    Logger.debug(tag, res, _context);

                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String post(String url, RequestBody formBody, String tag, Context _context) throws Exception {
        MyCallCounter myCall = new MyCallCounter(url, formBody, tag, _context);
        return myCall.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    @SuppressLint("StaticFieldLeak")
    private static final class MyCallCounter extends AsyncTask<Void, Void, String> {
        final String url;
        final String tag;
        final RequestBody formBody;
        final Context _context;

        MyCallCounter(String url, RequestBody formBody, String tag, Context _context) {
            this.url = url;
            this.formBody = formBody;
            this.tag = tag;
            this._context = _context;
        }

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .tag(tag)
                    .build();
            Logger.debug(tag, url + "?" + bodyToString(request), _context);
            String s = url + "?" + bodyToString(request);

            try {
                Response response = client.newCall(request).execute();
                String res = response.body().string();
                if (res.trim().length() > 0) {
                    JSONObject jsonObject = new JSONObject(res);
                    Logger.debug(tag, res, _context);
                    if (response.isSuccessful()) {
                        if (jsonObject.has(General.STATUS) && jsonObject.getInt(General.STATUS) == 13) {
                            return "13";
                        } /*else if (jsonObject.has(General.STATUS) && jsonObject.getInt(General.STATUS) == 13) {
                            PerformLogoutTask.logout(activity);
                        } else if (jsonObject.has(General.STATUS) && jsonObject.getInt(General.STATUS) == 13) {
                            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + my_packagename));
                            final String appPackageName = _context.getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                _context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                _context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }*/ else {
                            return res;
                        }
                    } else {
                        if (jsonObject.has(General.STATUS) && jsonObject.getInt(General.STATUS) == 13) {
                            return "13";
                        } else {
                            return null;
                        }
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    // make call to fetch oauth token
    private static String getToken(Context _context) {
        String access_token = null;
        Token_ token;

        OauthPreferences.initialize(_context);
        String i = OauthPreferences.get(Oauth.EXPIRES_AT);
        try {
            if (System.currentTimeMillis() >= Long.parseLong(OauthPreferences.get(Oauth.EXPIRES_AT))) {
                token = refreshToken.getRefreshToken(Preferences.get(Oauth.CLIENT_ID), Preferences.get(Oauth.CLIENT_SECRET), Preferences.get(General.DOMAIN).replaceAll(General.INSATNCE_NAME, ""), _context);
                if (token.getStatus() == 1) {
                    access_token = token.getAccessToken();
                } else if (token.getStatus() == 12) {
                    getToken(_context);
                }/* else {
                 PerformLogoutTask.logout(activity);
            }*/
            } else {
                access_token = OauthPreferences.get(Oauth.ACCESS_TOKEN);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }

    // make final url with encrypted parameters and agent name
    private static RequestBody finalBody(String mainBody, String token) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder
                .add("akujs", UrlEncoder_.encrypt(mainBody))
                .add("d", "a")
                .add(Oauth.ACCESS_TOKEN, token);
        return formBuilder.build();
    }

    // convert encrypted final body to normal string
    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    // make com.sagesurfer.network call body with user parameters with remaining common parameters
    // token, key, timezone and user id
    private static RequestBody makeBody(HashMap<String, String> map) {
        HashMap<String, String> keyMap = KeyMaker_.getKey();
        map.put(General.TOKEN, keyMap.get(General.TOKEN));
        map.put(General.KEY, keyMap.get(General.KEY));
        map.put(General.USER_ID, Preferences.get(General.USER_ID)); //logged in user id
        map.put(General.TIMEZONE, Preferences.get(General.TIMEZONE));
        map.put(General.DOMAIN_CODE, Preferences.get(General.DOMAIN_CODE));
        Set keys = map.keySet();
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Object object : keys) {
            String key = (String) object;
            String value = map.get(key);
            formBuilder.add(key, value);
        }
        return formBuilder.build();
    }
}