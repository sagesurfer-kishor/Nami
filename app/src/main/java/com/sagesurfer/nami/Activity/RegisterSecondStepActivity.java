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

import com.sagesurfer.nami.R;
import com.sagesurfer.nami.validater.LoginValidator;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.validation.Validator;

public class RegisterSecondStepActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<HashMap<String, String>> instanceList;

    Button btnRegisterthreestep;
    TextView txtCancelsecondstep;
    EditText edEmailId;
    EditText edMobileNumber;

    TextView txt_setp_two_first, txt_setp_two_two;
    View view_step_two_first, view_step_two_two;

    String EmailId;
    String MobileNumber;
    String firstName;
    String lastName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_step_two);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Intent intent = getIntent();
        firstName = intent.getExtras().getString("firstName");
        lastName = intent.getExtras().getString("lastName");

        edEmailId = findViewById(R.id.edEmail_register_user);
        edMobileNumber = findViewById(R.id.edmobileNumber_register_user);

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
                EmailId = edEmailId.getText().toString().trim();
                MobileNumber = edMobileNumber.getText().toString().trim();

                if (TextUtils.isEmpty(EmailId)) {
                    edEmailId.setError("Please enter email Id");
                } else if (!LoginValidator.isEmail(EmailId)) {
                    edEmailId.setError("Invalid Input");
                } else {

                    if (TextUtils.isEmpty(MobileNumber)) {
                        edMobileNumber.setError("Please enter Mobile Number");
                    } else if (!LoginValidator.isphonenumber(MobileNumber)) {
                        edMobileNumber.setError("Invalid Mobile Number");
                    } else {
                        Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                                (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                        Intent i = new Intent(this, RegisterThreeStepActivity.class);
                        i.putExtra("firstName", firstName);
                        i.putExtra("lastName", lastName);
                        i.putExtra("emailId", EmailId);
                        i.putExtra("mobileNumber", MobileNumber);
                        startActivity(i, bundleAnimation);
                    }
                }
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
