package com.sagesurfer.nami.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    CoordinatorLayout coordinatorLayout;

    Button btnNext_step_first, btnNext_step_two, btnNext_step_three;
    TextView btnCancel_step_first;

    private ArrayList<HashMap<String, String>> instanceList;

    TextView txt_setp_first;
    View view_step_first;

    EditText edFirstName;
    EditText edLastName;

    String firstName;
    String lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        //enter input first name
        edFirstName = findViewById(R.id.edFirstName_register_user);
        //enter input last name
        edLastName = findViewById(R.id.edLastName_register_user);

        //button declaration
        btnNext_step_first = findViewById(R.id.btnNext_step_first_register_user);
        btnCancel_step_first = findViewById(R.id.btnCancel_step_first_register_user);

        txt_setp_first = findViewById(R.id.step_first_view_first);
        view_step_first = findViewById(R.id.setp_three_line_view_first);

        txt_setp_first.setBackgroundResource(R.drawable.step_view_green_circle);
        txt_setp_first.setTextColor(Color.parseColor("#FFFFFF"));

        //next button click
        btnNext_step_first.setOnClickListener(this);
        //cancel button click
        btnCancel_step_first.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNext_step_first_register_user:
                firstName = edFirstName.getText().toString().trim();
                lastName = edLastName.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    edFirstName.setError("Please enter first name");
                } else {
                    if (TextUtils.isEmpty(lastName)) {
                        edLastName.setError("please enter last name");
                    } else {

                        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();

                        Intent i = new Intent(this, RegisterSecondStepActivity.class);
                        i.putExtra("firstName", firstName);
                        i.putExtra("lastName", lastName);
                        startActivity(i, bundleAnimation);

                    }
                }
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
