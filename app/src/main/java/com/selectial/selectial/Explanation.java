package com.selectial.selectial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Explanation extends AppCompatActivity {

    ImageView back;

    String que , ans , exp;

    TextView question , answer , explanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        que = getIntent().getStringExtra("que");
        ans = getIntent().getStringExtra("ans");
        exp = getIntent().getStringExtra("exp");

        back = findViewById(R.id.imageButton4);
        question = findViewById(R.id.textView99);
        answer = findViewById(R.id.textView100);
        explanation = findViewById(R.id.textView102);


        question.setText("Ques: " + que);
        answer.setText("Ans: " + ans);
        explanation.setText(exp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
