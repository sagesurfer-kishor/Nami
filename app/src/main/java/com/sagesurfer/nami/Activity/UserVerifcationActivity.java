package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sagesurfer.nami.R;

public class UserVerifcationActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLoginUser;
    TextView txtCancelUserLogin;


    EditText edVerifyCode;
    ImageView imgQrcode;

    String VerifyCode;
    String UserId;
    String Password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_verification);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

      /*  // get value from login page
        Intent intent = getIntent();
        UserId = intent.getExtras().getString("userId");
        Password = intent.getExtras().getString("password");
*/

        edVerifyCode = findViewById(R.id.eduser_verify_code);

        btnLoginUser = findViewById(R.id.btnLogin_verify_user);
        txtCancelUserLogin = findViewById(R.id.txtCancel_user_verify);
        imgQrcode = findViewById(R.id.imgVerify_qrcode);

      /*  animationView = findViewById(R.id.login_user_anim_view);
        animationView.setAnimation("genericLoader.json");
        animationView.loop(true);*/

        btnLoginUser.setOnClickListener(this);
        txtCancelUserLogin.setOnClickListener(this);

    }

    // different click event
    @Override
    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.btnLogin_verify_user:
                VerifyCode = edVerifyCode.getText().toString().trim();
                if (TextUtils.isEmpty(VerifyCode)) {
                    edVerifyCode.setError("Invalid Input");
                } else {

                    Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                            (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                    Intent forgotIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(forgotIntent, bundleAnimation);
                    finish();

                }
                break;

            case R.id.txtCancel_user_verify:
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


}
