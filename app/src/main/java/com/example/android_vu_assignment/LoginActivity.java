package com.example.android_vu_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android_vu_assignment.utils.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialTextView dontHaveAccountTextView;
    private MaterialButton loginButton;
    private EditText passwordEditText, emailEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        initUI();
    }

    private void initUI() {
        dontHaveAccountTextView = findViewById(R.id.dontHaveAccountTextView);
        loginButton = findViewById(R.id.loginButton);
        passwordEditText = findViewById(R.id.passwordInputLayout);
        emailEditText = findViewById(R.id.usernameInputLayout);
        progressBar = findViewById(R.id.progressLayout);
        dontHaveAccountTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == dontHaveAccountTextView){
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            finish();
        }
        else if (view == loginButton){
            if(validationCheckPass()){

                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                        Utils.setUserLogin(LoginActivity.this, true);
                        Utils.setEmail(LoginActivity.this, emailEditText.getText().toString());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }, 1500);
            }
        }
    }

    private boolean validationCheckPass() {
        View coordinatorLayout = findViewById(android.R.id.content);
        if (emailEditText.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide email address.");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide valid email address.");
            return false;
        }
        if (passwordEditText.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide password for login account.");
            return false;
        }
        return true;
    }
}