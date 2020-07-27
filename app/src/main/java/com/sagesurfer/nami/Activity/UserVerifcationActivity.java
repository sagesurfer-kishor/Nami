package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sagesurfer.nami.MainActivity;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;

import java.util.ArrayList;
import java.util.HashMap;

public class UserVerifcationActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLoginUser;
    TextView txtCancelUserLogin;

    private ArrayList<HashMap<String, String>> instanceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);


        btnLoginUser = findViewById(R.id.btnLogin_verify_user);
        txtCancelUserLogin = findViewById(R.id.txtCancel_user_verify);

        btnLoginUser.setOnClickListener(this);
        txtCancelUserLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin_verify_user:

                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getApplicationContext(), MainActivity.class);
                forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
                startActivity(forgotIntent, bundleAnimation);

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
