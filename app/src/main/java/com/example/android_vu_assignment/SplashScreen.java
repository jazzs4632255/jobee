package com.example.android_vu_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.android_vu_assignment.utils.Utils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        AppCompatTextView appCompatTextView = findViewById(R.id.appCompatTextView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isUserLogin(SplashScreen.this)){
                    Intent i = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(i);
                }
                finish();
            }
        },2000);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        appCompatTextView.startAnimation(myanim);
    }
}