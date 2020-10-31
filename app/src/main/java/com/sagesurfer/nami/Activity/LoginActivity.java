package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.model.UserInfo_;
import com.sagesurfer.nami.parser.Login_;
import com.sagesurfer.nami.storage.preferences.Preferences;
import com.sagesurfer.nami.tasks.PerformLoginTask;
import com.sagesurfer.nami.validater.LoginValidator;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEmailId;
    EditText edPassword;

    TextView txtHotline;
    TextView txtForgotPassword;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    Button btnLogin;
    CheckBox chkReminderme;

    LinearLayout txtRegister;

    String emailId;
    String password;

    private Boolean saveLogin;
    String errorMsg;
    private String debug = "1";

    private UserInfo_ userInfo;
    private AlertDialog alertDialog;

    private LottieAnimationView animationView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Preferences.initialize(getApplicationContext());

        progressDialog = new ProgressDialog(LoginActivity.this);

        // define edittext view
        edEmailId = findViewById(R.id.edEmail_login_user);
        edPassword = findViewById(R.id.edPassword_login_user);

        //setting OnFocusChangeListener
        edEmailId.setOnFocusChangeListener(onFocusChange);
        edPassword.setOnFocusChangeListener(onFocusChange);

        btnLogin = findViewById(R.id.btnLogin_user_login);
        chkReminderme = findViewById(R.id.chkRememberme_login_user);
        txtHotline = findViewById(R.id.txtHotlinenumber_login);
        txtRegister = findViewById(R.id.txtRegister_user_login);
        txtForgotPassword = findViewById(R.id.txtForgotPassword_login_user);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin) {
            edEmailId.setText(loginPreferences.getString("email", ""));
            edPassword.setText(loginPreferences.getString("server_code", ""));
            chkReminderme.setChecked(false);
        }
        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);

        txtHotline.setText(Preferences.get("helplineno"));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // click on login button
            case R.id.btnLogin_user_login:
                getLoginUser();
                break;
            //click on register user button
            case R.id.txtRegister_user_login:
                RegisterUser();
                break;
            //click on forgot password button
            case R.id.txtForgotPassword_login_user:
                ForgotPassword();
                break;
        }
    }

    // check login user credentials and validation
    private void getLoginUser() {
        emailId = edEmailId.getText().toString().trim();
        password = edPassword.getText().toString().trim();

        if (chkReminderme.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("email", emailId);
            loginPrefsEditor.putString("password", password);
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        if (TextUtils.isEmpty(emailId)) {
            edEmailId.setError("Please enter email");
        } else if (!LoginValidator.isEmail(emailId)) {
            edEmailId.setError("Invalid Input");
        } else {

            if (TextUtils.isEmpty(password)) {
                edPassword.setError("Please enter password");
            } else if (!LoginValidator.isPassword(password)) {
                edPassword.setError("Invalid Input");
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doLogin(emailId, password);
                    }
                }, 2000);
            }
        }

    }

    // forgot password screen
    private void ForgotPassword() {
        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
        Intent forgotIntent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(forgotIntent, bundleAnimation);
    }

    // register user screen
    private void RegisterUser() {
        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
        Intent forgotIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(forgotIntent, bundleAnimation);
        finish();
    }

    private final View.OnFocusChangeListener onFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.edEmail_login_user:
                    break;

                case R.id.edPassword_login_user:
                    break;
            }
        }
    };

    //Performing login- logging in with the username, password, if user login success
    private void doLogin(String email, String password) {
        int status = 12;
        String response = null;
        try {
            response = new PerformLoginTask(email, password, this).execute().get();
            Log.e("Login", "" + response);

            if (response != null) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                if (jsonObject.has("drawer")) {
                    if (jsonObject.has("details")) {
                        JsonObject object = jsonObject.get("details").getAsJsonObject();
                        if (object.has(General.STATUS)) {
                            status = object.get(General.STATUS).getAsInt();
                            if (status == 3) {
                                if (object.get("error") != null) {
                                    errorMsg = object.get("error").getAsString();
                                }
                            }
                        } else {
                            status = 11;
                        }


                        Preferences.save(General.PASSWORD, password);
                        Preferences.save(General.FIRST_NAME, object.get(General.FIRST_NAME).getAsString());
                        Preferences.save(General.LAST_NAME, object.get(General.LAST_NAME).getAsString());
                        Preferences.save(General.EMAIL, object.get(General.EMAIL).getAsString());
                        Preferences.save(General.PHONE, object.get(General.PHONE).getAsString());
                        Preferences.save(General.USERID, object.get(General.USERID).getAsString());


                    } else if (jsonObject.has("details")) {
                        JsonObject object = jsonObject.get("details").getAsJsonObject();
                        if (status == 3) {
                            if (object.get("error") != null) {
                                errorMsg = object.get("error").getAsString();
                            }
                        }
                        if (object.has(General.STATUS)) {
                            status = object.get(General.STATUS).getAsInt();
                        }

                    } else {
                        status = 11;
                    }
                }
            }

        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            e.printStackTrace();
        }

        if (status == 1) {
            Preferences.save(General.IS_LOGIN, 1);
            userInfo = Login_.userInfoParser(response, "details", getApplicationContext());

            Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                    (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
            Intent forgotIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(forgotIntent, bundleAnimation);
            finish();
            return;
        }
        showResponses(status);
    }

    //Showing login response in snackbar
    private void showResponses(int status) {
        String message = null;
        if (status == 1) {
            message = "Login was successful";
        } else if (status == 3) {
            message = getApplicationContext().getResources().getString(R.string.username_password_not_match);
        } else if (status == 5) {
            if (errorMsg != null) {
                showDialog(errorMsg);
            }
        }
        SubmitSnackResponse.showSnack(status, message, btnLogin, getApplicationContext());
    }

    // error msg dialog
    private void showDialog(String errorMsg) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.error_popup, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(dialogView);

        alertDialog = builder.create();

        final TextView okBtn = dialogView.findViewById(R.id.ok_btn);
        final TextView error = dialogView.findViewById(R.id.message_txt);
        error.setText(errorMsg);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    //Storing user data in preferences onSuccess login, onSuccessful oAuth token generation
    private void saveData() {
        String password = edPassword.getText().toString().trim();
        Preferences.save(General.PASSWORD, password);
        Preferences.save(General.USERID, userInfo.getUserId());

        Preferences.save(General.FIRST_NAME, userInfo.getFirstName());
        Preferences.save(General.LAST_NAME, userInfo.getLastName());
        Preferences.save(General.USERNAME, userInfo.getUsername());
        Preferences.save(General.EMAIL, userInfo.getEmail());
        Preferences.save(General.PHONE, userInfo.getPhone());
    }

}
