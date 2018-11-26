package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Interest extends AppCompatActivity {

    TextView proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        proceed = findViewById(R.id.textView21);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Interest.this , StartTest.class);
                startActivity(intent);

            }
        });

    }
}
