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

public class RegisterSecondStepActivity extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<HashMap<String, String>> instanceList;

    Button btnRegisterthreestep;
    TextView txtCancelsecondstep;

    TextView txt_setp_two_first, txt_setp_two_two;
    View view_step_two_first, view_step_two_two;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_step_two);

        btnRegisterthreestep = findViewById(R.id.btnNext_step_two_register_user);
        txtCancelsecondstep = findViewById(R.id.btnBack_step_first_register_user);

        txt_setp_two_first = findViewById(R.id.step_two_view_first);
        view_step_two_first = findViewById(R.id.setp_two_line_view_first);

        txt_setp_two_two = findViewById(R.id.step_two_view_two);
        view_step_two_two = findViewById(R.id.setp_two_line_view_second);

        txt_setp_two_first.setBackgroundResource(R.drawable.step_check);
        txt_setp_two_first.setTextColor(Color.parseColor("#FFFFFF"));
        txt_setp_two_first.setText("");

        view_step_two_first.setBackgroundColor(Color.parseColor("#05D39B"));

        txt_setp_two_two.setBackgroundResource(R.drawable.step_view_green_circle);
        txt_setp_two_two.setTextColor(Color.parseColor("#FFFFFF"));

        btnRegisterthreestep.setOnClickListener(this);
        txtCancelsecondstep.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNext_step_two_register_user:
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getApplicationContext(), RegisterThreeStepActivity.class);
                forgotIntent.putExtra(General.INSTANCE_KEY, instanceList);
                startActivity(forgotIntent, bundleAnimation);
                break;
            case R.id.btnBack_step_first_register_user:
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
