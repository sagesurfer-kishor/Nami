package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.model.RegisterUser;
import com.sagesurfer.nami.model.UserInfo_;
import com.sagesurfer.nami.validater.LoginValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisterThreeStepActivity extends AppCompatActivity implements View.OnClickListener {
    CoordinatorLayout coordinatorLayout;

    private ArrayList<HashMap<String, String>> instanceList;

    TextView txtBack_second_step;
    TextView txt_setp_three_first, txt_setp_three_two, txt_setp_three_three;
    View view_step_three_first, view_step_three_two;

    Button btnSubmit_user_registration;
    CheckBox chkterms_condition_register_user;

    EditText edpassword;
    EditText edconfirmPassword;

    String Password;
    String confirmPassword;

    String firstName, lastName, emailId, mobileNumber, terms;
    private ArrayList<RegisterUser> registerUserArrayList;
    private AlertDialog registrarAlert;

    String debug = "1";
    String errorMsg;
    private UserInfo_ userInfo;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Intent intent = getIntent();
        firstName = intent.getExtras().getString("firstName");
        lastName = intent.getExtras().getString("lastName");
        emailId = intent.getExtras().getString("emailId");
        mobileNumber = intent.getExtras().getString("mobileNumber");

        edpassword = findViewById(R.id.edPassword_register_user);
        edconfirmPassword = findViewById(R.id.edconfirm_password_register_user);

        btnSubmit_user_registration = findViewById(R.id.btnRegister_user);
        txtBack_second_step = findViewById(R.id.txtBack_step_two_register_user);
        chkterms_condition_register_user = findViewById(R.id.chkterms_condition_register_user);

        txt_setp_three_first = findViewById(R.id.step_three_view_first);
        view_step_three_first = findViewById(R.id.setp_three_line_view_first);

        txt_setp_three_two = findViewById(R.id.step_three_view_two);
        view_step_three_two = findViewById(R.id.setp_three_line_view_second);

        txt_setp_three_three = findViewById(R.id.step_three_view_three);

        txt_setp_three_first.setBackgroundResource(R.drawable.step_check);
        txt_setp_three_first.setTextColor(Color.parseColor("#FFFFFF"));
        txt_setp_three_first.setText("");

        view_step_three_first.setBackgroundColor(Color.parseColor("#05D39B"));

        txt_setp_three_two.setBackgroundResource(R.drawable.step_check);
        txt_setp_three_two.setTextColor(Color.parseColor("#FFFFFF"));
        txt_setp_three_two.setText("");

        view_step_three_two.setBackgroundColor(Color.parseColor("#05D39B"));

        btnSubmit_user_registration.setOnClickListener(this);
        txtBack_second_step.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnRegister_user:
                Password = edpassword.getText().toString().trim();
                confirmPassword = edconfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(Password)) {
                    edpassword.setError("Please enter password");
                } else if (!LoginValidator.isPassword(Password)) {
                    edpassword.setError("Invalid Input");
                } else {
                    if (TextUtils.isEmpty(confirmPassword)) {
                        edconfirmPassword.setError("Please enter confirm password");
                    } else if (!LoginValidator.isPassword(confirmPassword)) {
                        edconfirmPassword.setError("Innvalid Input");
                    } else {
                        if (confirmPassword.equals(Password)) {
                            if (chkterms_condition_register_user.isChecked()) {
                                terms = "Y";
                                userRegistration(firstName, lastName,
                                        emailId, mobileNumber, Password, terms);
                            } else {
                                showResponses(6);

                            }
                        } else {
                            edpassword.setError("Password not matched");
                            edconfirmPassword.setError("Password not matched");
                        }
                    }
                }
                break;

            case R.id.txtBack_step_two_register_user:
                finish();
                this.overridePendingTransition(R.anim.animation_left_to_right,
                        R.anim.animation_right_to_left);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.animation_left_to_right,
                R.anim.animation_right_to_left);
    }
    //Performing Register User for get access
    private void userRegistration(final String firstname, final String lastname, final String email, final String mobilenumber, final String user_passord, final String terms) {

        int status = 12;
        String response = null;
        try {
            response = new PerformRegisterTask(firstname, lastname, email, mobilenumber, user_passord, terms, debug, this).execute().get();
            Log.e("Register", "" + response);

            if (response != null) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

                if (jsonObject.has("details")) {
                    JsonObject object = jsonObject.get("details").getAsJsonObject();

                    if (object.has(General.STATUS)) {
                        status = object.get(General.STATUS).getAsInt();

                        if (status == 5) {
                            if (object.get("error") != null) {
                                errorMsg = object.get("error").getAsString();
                            }
                        }

                    } else {
                        status = 11;
                    }
                } else if (jsonObject.has("details")) {
                    JsonObject object = jsonObject.get("details").getAsJsonObject();
                    if (status == 5) {
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

            if (status == 1) {
                //userInfo = Login_.userInfoParser(response, "details", getApplicationContext());
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(forgotIntent, bundleAnimation);
                finish();

                return;
            }
            showResponses(status);

        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    //Showing login response in snackbar
    private void showResponses(int status) {
        String message = null;
        if (status == 1) {
            message = "Register user successful";
        } else if (status == 2) {
            message = getApplicationContext().getResources().getString(R.string.username_password_not_match);
        } else if (status == 3) {
            if (errorMsg != null) {
                showDialog(errorMsg);
            }
        } else if (status == 6) {
            message = "Please accept terms and condition";
        }
        SubmitSnackResponse.showSnack(status, message, btnSubmit_user_registration, getApplicationContext());
    }

    private void showDialog(String errorMsg) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(RegisterThreeStepActivity.this).inflate(R.layout.error_popup, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterThreeStepActivity.this);
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
}
