package com.sagesurfer.nami.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.Actions_;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.datetime.GetTime;
import com.sagesurfer.nami.library.DeviceInfo;
import com.sagesurfer.nami.modules.Crisis.CrisisFragment;
import com.sagesurfer.nami.network.NetworkCall_;
import com.sagesurfer.nami.network.Urls_;
import com.sagesurfer.nami.parser.GetJson_;
import com.sagesurfer.nami.secure._Base64;
import com.sagesurfer.nami.snack.ShowLoader;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.util.HashMap;

import okhttp3.RequestBody;

public class EditFriendlistActvity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = EditFriendlistActvity.class.getSimpleName();
    EditText friendName, friendPhone;
    Button btnSubmit;

    String friend_name;
    String friend_phone_number;
    private ShowLoader showLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend_list);

        showLoader = new ShowLoader();

        friendName = findViewById(R.id.edFriend_name);
        friendPhone = findViewById(R.id.edFriend_phone_number);

        btnSubmit = findViewById(R.id.btnadd_friend_call_list);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // click on login button
            case R.id.btnadd_friend_call_list:
                friend_name = friendName.getText().toString().trim();
                friend_phone_number = friendPhone.getText().toString().trim();

                if (!TextUtils.isEmpty(friend_name)) {
                    if (!TextUtils.isEmpty(friend_phone_number)) {

                        addFriendinList("1", friend_name, friend_phone_number, view);
                    } else {
                        friendPhone.setError("enter friend phone number");
                    }
                } else {
                    friendName.setError("enter friend name");
                }
                break;

        }
    }


    // add new friend in list
    private void addFriendinList(String user_id, String friendName, String friendPhone, View view) {
        int status = 12;
        String info = DeviceInfo.get(this);
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.TIMEZONE, Preferences.get(General.TIMEZONE));
        requestMap.put(General.ACTION, Actions_.ADD_FRIEND);
        requestMap.put(General.USER_ID, user_id);
        requestMap.put(General.NAME, friendName);
        requestMap.put(General.PHONE, friendPhone);
        requestMap.put(General.END_TIME, GetTime.getChatTimestamp());
        requestMap.put(General.INFO, _Base64.encode(info));
        requestMap.put(General.IP, DeviceInfo.getDeviceMAC(this));

        //String url = Preferences.get(General.DOMAIN) + "/" + Urls_.Mobile_CRISIS;
        String url = Urls_.Mobile_CRISIS;
        RequestBody requestBody = NetworkCall_.make(requestMap, url, TAG, this, this);
        if (requestBody != null) {
            try {
                String response = NetworkCall_.post(url, requestBody, TAG, this, this);
                if (response != null) {
                    Log.e("addfriend", response);

                    JsonArray jsonArray = GetJson_.getArray(response, Actions_.ADD_FRIEND);
                    if (jsonArray != null) {
                        JsonObject object = jsonArray.get(0).getAsJsonObject();
                        if (object.has(General.STATUS)) {
                            status = object.get(General.STATUS).getAsInt();
                        } else {
                            status = 11;
                        }
                    } else {
                        status = 11;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        showResponses(status, view);
    }

    private void showResponses(int status, View view) {
        showLoader.dismissPostingDialog();
        String message;
        if (status == 1) {
            message = this.getResources().getString(R.string.successful);
        } else {
            message = this.getResources().getString(R.string.action_failed);
        }

        SubmitSnackResponse.showSnack(status, message, view, getApplicationContext());
        if (status == 1) {
            onBackPressed();
        }
    }


}
