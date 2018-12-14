package com.selectial.selectial;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.selectial.selectial.util.SharePreferenceUtils;

public class Settings extends AppCompatActivity {

    TextView logout;

    TextView change;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        change = findViewById(R.id.textView76);
        back = findViewById(R.id.imageButton4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i  =new Intent(Settings.this , ChangePassword.class);
                startActivity(i);
            }
        });

        logout = findViewById(R.id.textView75);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Settings.this , SignupLogin.class);
                SharePreferenceUtils.getInstance().deletePref();
                startActivity(i);
                finishAffinity();

            }
        });
    }
}
