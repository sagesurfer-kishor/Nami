package com.sagesurfer.nami.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.validater.LoginValidator;

import java.util.concurrent.ExecutionException;


public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();

    EditText edUserId_forgot_password;
    EditText edEmail_forgot_password;

    String UserId;
    String EmailId;
    String Debug = "1";

    Button btnForgotpassword;
    TextView txtCancel;
    private Activity activity;
    String errorMsg;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgotpassword);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        //edUserId_forgot_password = findViewById(R.id.edUserId_forgot_password);
        edEmail_forgot_password = findViewById(R.id.edEmail_forgot_password);

        txtCancel = findViewById(R.id.txtCancel_forgot_password);
        btnForgotpassword = findViewById(R.id.btnSubmit_forgot_password);

        txtCancel.setOnClickListener(this);
        btnForgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSubmit_forgot_password:
                EmailId = edEmail_forgot_password.getText().toString().trim();
                // UserId = edUserId_forgot_password.getText().toString().trim();

               /* if (TextUtils.isEmpty(UserId)) {
                    edUserId_forgot_password.setError("Invalid Input");
                } else {*/
                if (TextUtils.isEmpty(EmailId)) {
                    edEmail_forgot_password.setError("Please enter email Id");
                } else if (!LoginValidator.isEmail(EmailId)) {
                    edEmail_forgot_password.setError("Invalid Input");
                    return;
                } else {
                    forgotPassword(EmailId);
                }
                //}
                break;

            case R.id.txtCancel_forgot_password:
                cencel();
                break;
        }
    }

    private void forgotPassword(final String emailId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SupportAction(emailId);
            }
        }, 2000);
    }

    private void cencel() {
        finish();
        this.overridePendingTransition(R.anim.animation_left_to_right,
                R.anim.animation_right_to_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.animation_left_to_right,
                R.anim.animation_right_to_left);
    }

    //Performing Forgot password- send link for forgot passowrd is user email id is correct
    private void SupportAction(String user_id) {
        int status = 12;

        String response = null;
        try {
            response = new PerformForgotTask(user_id, this).execute().get();
            Log.e("ForgotPassword", "" + response);

            if (response != null) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

                if (jsonObject.has("details")) {
                    JsonObject object = jsonObject.get("details").getAsJsonObject();
                    if (object.has(General.STATUS)) {
                        status = object.get(General.STATUS).getAsInt();
                        if (status == 4) {
                            if (object.get("error") != null) {
                                errorMsg = object.get("error").getAsString();
                            }
                        }
                    } else {
                        status = 11;
                    }
                } else if (jsonObject.has("details")) {
                    JsonObject object = jsonObject.get("details").getAsJsonObject();
                    if (status == 4) {
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
        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            e.printStackTrace();
        }

        if (status == 1) {
            Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                    (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
            Intent forgotIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(forgotIntent, bundleAnimation);
            finish();
        }
        showResponses(status);
    }

    //Showing forgot password response in snackbar
    private void showResponses(int status) {
        String message = null;
        if (status == 1) {
            message = "Email send to your register email-Id";
        } else if (status == 4) {
            message = getApplicationContext().getResources().getString(R.string.wrong_email);
        } else if (status == 5) {
            if (errorMsg != null) {
                showDialog(errorMsg);
            }
        }
        SubmitSnackResponse.showSnack(status, message, btnForgotpassword, getApplicationContext());
    }

    private void showDialog(String errorMsg) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ForgotPasswordActivity.this).inflate(R.layout.error_popup, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
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
