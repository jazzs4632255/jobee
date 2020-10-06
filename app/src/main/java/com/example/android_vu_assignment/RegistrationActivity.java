package com.example.android_vu_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android_vu_assignment.utils.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private MaterialTextView haveAccountTextView;
    private MaterialButton registrationButton;
    private EditText usernameEditText, emailEditText, ageEditText, passwordEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Registration");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        initUI();
    }

    private void initUI() {
        haveAccountTextView = findViewById(R.id.haveAccountTextView);
        registrationButton = findViewById(R.id.registrationButton);
        usernameEditText = findViewById(R.id.usernameInputLayout);
        emailEditText = findViewById(R.id.ageInputLayout);
        ageEditText = findViewById(R.id.countryInputLayout);
        passwordEditText = findViewById(R.id.passwordInputLayout);
        progressBar = findViewById(R.id.progressLayout);

        haveAccountTextView.setOnClickListener(this);
        registrationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == haveAccountTextView){
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        }
        else if(view == registrationButton){
            if (checkValidationPass()){
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegistrationActivity.this, "Account Registered Successfully.", Toast.LENGTH_SHORT).show();
                        Utils.setUserLogin(RegistrationActivity.this, true);
                        Utils.setEmail(RegistrationActivity.this, emailEditText.getText().toString());
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        finish();
                    }
                }, 1500);
            }
        }
    }

    private boolean checkValidationPass(){
        View coordinatorLayout = findViewById(android.R.id.content);
        if (usernameEditText.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide username.");
            return false;
        }
        if (emailEditText.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide email address.");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide valid email address.");
            return false;
        }
        if (ageEditText.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide your age.");
            return false;
        }
        if (passwordEditText.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide password.");
            return false;
        }
        return true;
    }
}