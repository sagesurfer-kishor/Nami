package com.sagesurfer.nami.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.Actions_;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.network.NetworkCall_;
import com.sagesurfer.nami.network.Urls_;
import com.sagesurfer.nami.parser.GetJson_;
import com.sagesurfer.nami.storage.preferences.Preferences;
import com.sagesurfer.nami.validater.LoginValidator;

import java.util.HashMap;

import okhttp3.RequestBody;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();

    EditText edOldPassword;
    EditText edNewPassoword;
    EditText edConfirmnewPassword;

    Button btnUpdate;

    String Oldpassword;
    String newPassword;
    String confirmpassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_changepassword);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        edOldPassword = findViewById(R.id.edOldpassword);
        edNewPassoword = findViewById(R.id.edNewPassword);
        edConfirmnewPassword = findViewById(R.id.edCofirm_NewPassword);

        btnUpdate = findViewById(R.id.btnChange_user_password);

        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // click on change password button

            case R.id.btnChange_user_password:
                Oldpassword = edOldPassword.getText().toString().trim();
                newPassword = edNewPassoword.getText().toString().trim();
                confirmpassword = edConfirmnewPassword.getText().toString().trim();

                changePassword();

                break;

        }
    }

    private void changePassword() {
        String id = Preferences.get(General.USERID);
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.RESET_PASSWORD);
        requestMap.put(General.OLD_PASSWORD, Oldpassword);
        requestMap.put(General.NEW_PASSWORD, newPassword);
        requestMap.put(General.USER_ID, id);

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_USER_SETTING;
        RequestBody requestBody = NetworkCall_.make(requestMap, url, TAG, getApplicationContext(), this);

        if (requestBody != null) {
            try {
                String response = NetworkCall_.post(url, requestBody, TAG, getApplicationContext(), this);
                if (response != null) {
                    JsonObject jsonObject = GetJson_.getJson(response);
                    JsonObject jsonAddJournal = jsonObject.getAsJsonObject(Actions_.RESET_PASSWORD);
                    if (jsonAddJournal.get(General.STATUS).getAsInt() == 1) {
                        Toast.makeText(ChangePasswordActivity.this, jsonAddJournal.get(General.MSG).getAsString(), Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, jsonAddJournal.get(General.ERROR).getAsString(), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //validate all fields
    private boolean validate() {
        String current_password = edOldPassword.getText().toString().trim();
        String new_password = edNewPassoword.getText().toString().trim();
        String confirm_password = edConfirmnewPassword.getText().toString().trim();

        String password = Preferences.get(General.PASSWORD);
        if (current_password.length() <= 0 || !current_password.equalsIgnoreCase(password)) {
            edOldPassword.setError("Please enter current password");
            return false;
        }
        if (new_password.length() <= 0 || !LoginValidator.isPassword(new_password)) {
            edNewPassoword.setError("Please enter new password");
            return false;
        }
        if (confirm_password == null || confirm_password.trim().length() <= 0) {
            edConfirmnewPassword.setError("Please enter confirm password");
            return false;
        } else if (!new_password.equals(confirm_password)) {
            edConfirmnewPassword.setError("New password and confirm password did not match");
            return false;
        }

        return true;
    }

}
