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

import com.sagesurfer.nami.MainActivity;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;

import java.util.ArrayList;
import java.util.HashMap;


public class RegisterThreeStepActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<HashMap<String, String>> instanceList;

    TextView txtBack_second_step;
    TextView txt_setp_three_first, txt_setp_three_two, txt_setp_three_three;
    View view_step_three_first, view_step_three_two;


    Button btnSubmit_user_registration;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        btnSubmit_user_registration = findViewById(R.id.btnRegister_user);
        txtBack_second_step = findViewById(R.id.txtBack_step_two_register_user);

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
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getApplicationContext(), MainActivity.class);
                forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
                startActivity(forgotIntent, bundleAnimation);

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

}
