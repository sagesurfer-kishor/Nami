package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;

import okhttp3.RequestBody;

public class EditProfileActvity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = EditProfileActvity.class.getSimpleName();

    EditText edFirstName_editprofile_user;
    EditText edLastName_editprofile_user;
    EditText edEmailId_editprofile_usre;
    EditText edMobile_editprofile_user;

    Button btnSave_Editprofile;
    TextView btnChangePassword;

    String fName, lName, emailId, Mobileno;
    String FirstName, LastName, EmailId, MobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_editprofile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Preferences.initialize(getApplicationContext());

        edFirstName_editprofile_user = findViewById(R.id.edFirstName_editprofile_user);
        edLastName_editprofile_user = findViewById(R.id.edLastName_editprofile_user);
        edEmailId_editprofile_usre = findViewById(R.id.edEmail_editprofile_user);
        edMobile_editprofile_user = findViewById(R.id.edmobileNumber_editprofile_user);

        edFirstName_editprofile_user.setText(Preferences.get(General.FIRST_NAME));
        edLastName_editprofile_user.setText(Preferences.get(General.LAST_NAME));
        edEmailId_editprofile_usre.setText(Preferences.get(General.EMAIL));
        edMobile_editprofile_user.setText(Preferences.get(General.PHONE));

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnSave_Editprofile = findViewById(R.id.btnSave_editprofile);

        btnChangePassword.setOnClickListener(this);
        btnSave_Editprofile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // click on save button
            case R.id.btnSave_editprofile:
                saveInformation();
                break;
            //click on change password
            case R.id.btnChangePassword:
                ChangePassword();
                break;

        }
    }

    private void saveInformation() {
        fName = edFirstName_editprofile_user.getText().toString().trim();
        lName = edLastName_editprofile_user.getText().toString().trim();
        emailId = edEmailId_editprofile_usre.getText().toString().trim();
        Mobileno = edMobile_editprofile_user.getText().toString().trim();

        submitUserSetting();

    }

    private void submitUserSetting() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.EDIT_PROFILE);
        requestMap.put(General.FIRST_NAME, fName);
        requestMap.put(General.LAST_NAME, lName);
        requestMap.put(General.PHONE, Mobileno);
        requestMap.put(General.USER_ID, Preferences.get(General.USERID));

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_USER_SETTING;
        RequestBody requestBody = NetworkCall_.make(requestMap, url, TAG, getApplicationContext(), this);

        if (requestBody != null) {
            try {
                String response = NetworkCall_.post(url, requestBody, TAG, getApplicationContext(), this);
                if (response != null) {
                    JsonObject jsonObject = GetJson_.getJson(response);
                    JsonObject jsonAddJournal = jsonObject.getAsJsonObject(Actions_.EDIT_PROFILE);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void ChangePassword() {
        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
        Intent forgotIntent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(forgotIntent, bundleAnimation);
    }



}
