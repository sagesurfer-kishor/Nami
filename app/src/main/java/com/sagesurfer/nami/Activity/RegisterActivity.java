package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnNext_step_first, btnNext_step_two, btnNext_step_three;
    TextView btnCancel_step_first;

    private ArrayList<HashMap<String, String>> instanceList;

    TextView txt_setp_first;
    View view_step_first;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnNext_step_first = findViewById(R.id.btnNext_step_first_register_user);
        btnCancel_step_first = findViewById(R.id.btnCancel_step_first_register_user);

        txt_setp_first = findViewById(R.id.step_first_view_first);
        view_step_first = findViewById(R.id.setp_three_line_view_first);

        txt_setp_first.setBackgroundResource(R.drawable.step_view_green_circle);
        txt_setp_first.setTextColor(Color.parseColor("#FFFFFF"));

        btnNext_step_first.setOnClickListener(this);
        btnCancel_step_first.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNext_step_first_register_user:

                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getApplicationContext(), RegisterSecondStepActivity.class);
                forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
                startActivity(forgotIntent, bundleAnimation);

                break;


            case R.id.btnCancel_step_first_register_user:
                cencelRegister();
                break;


        }

    }

    private void cencelRegister() {
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
