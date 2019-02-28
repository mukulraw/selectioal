package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScholarshipStartTest extends AppCompatActivity {


    Button start;

    TextView text , testCode;

    String title , time , id , inst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_start_test);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");
        inst = getIntent().getStringExtra("inst");

        start = findViewById(R.id.button5);

        text = findViewById(R.id.textView30);
        testCode = findViewById(R.id.textView31);

        testCode.setText(title);
        text.setText(Html.fromHtml(inst));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScholarshipStartTest.this , ScholarshipTest.class);
                intent.putExtra("title" , title);
                intent.putExtra("time" , time);
                intent.putExtra("id" , id);
                startActivity(intent);
                finish();
            }
        });


    }
}
