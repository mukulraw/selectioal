package com.selectial.selectial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

public class AnswerKeyView extends AppCompatActivity {

    ImageButton back;

    WebView web;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_key_view);

        url = getIntent().getStringExtra("url");

        back = findViewById(R.id.imageButton4);

        web = findViewById(R.id.web);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        web.loadUrl(url);

    }
}
