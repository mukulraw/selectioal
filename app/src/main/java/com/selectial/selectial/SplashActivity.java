package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this , SignupLogin.class);
                startActivity(intent);
                finishAffinity();

            }
        } , 800);



    }
}