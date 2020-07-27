package com.sagesurfer.nami.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sagesurfer.nami.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtCancel;

    Button btnForgotpassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        txtCancel = findViewById(R.id.txtCancel_forgot_password);
        btnForgotpassword = findViewById(R.id.btnSubmit_forgot_password);

        txtCancel.setOnClickListener(this);
        btnForgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSubmit_forgot_password:
                cencel();
                break;

            case R.id.txtCancel_forgot_password:
                cencel();
                break;


        }

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


}
