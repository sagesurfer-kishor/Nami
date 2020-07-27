package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<HashMap<String, String>> instanceList;

    EditText edUserId;
    EditText edPassword;

    Button btnLogin;
    CheckBox chkReminderme;

    LinearLayout txtRegister;
    TextView txtHotline;
    TextView txtForgotPassword;

    String userId;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        edUserId = findViewById(R.id.edUserId_login_user);
        edPassword = findViewById(R.id.edPassword_login_user);

        btnLogin = findViewById(R.id.btnLogin_user_login);
        chkReminderme = findViewById(R.id.chkReminderme_login_user);
        txtHotline = findViewById(R.id.txtHotlinenumber_login);
        txtRegister = findViewById(R.id.txtRegister_user_login);
        txtForgotPassword = findViewById(R.id.txtForgotPassword_login_user);

        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin_user_login:
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getApplicationContext(), UserVerifcationActivity.class);
                forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
                startActivity(forgotIntent, bundleAnimation);
                break;

            case R.id.txtRegister_user_login:
                RegisterUser();
                break;

            case R.id.txtForgotPassword_login_user:
                ForgotPassword();
                break;
        }
    }

    private void ForgotPassword() {
        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
        Intent forgotIntent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
        startActivity(forgotIntent, bundleAnimation);
    }

    private void RegisterUser() {
        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        registerIntent.putExtra(General.INSTANCE_KEY, instanceList);
        startActivity(registerIntent, bundleAnimation);
    }
}
