package com.selectial.selectial;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupLogin extends AppCompatActivity {

    Button login , signup;
    TextView subText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        login = findViewById(R.id.button2);
        signup = findViewById(R.id.button);
        subText = findViewById(R.id.textView91);


        SpannableString ss = new SpannableString("By entering you will agree to our Privacy Policy and Terms and Conditions");
        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                Log.d("clicked" , "privacy");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://selectialindia.com/privacy-policy.html")));

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan terms = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                Log.d("clicked" , "terms");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://selectialindia.com/terms-condition.html")));

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(privacy, 34, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(terms, 52, 73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        subText.setText(ss);
        subText.setMovementMethod(LinkMovementMethod.getInstance());
        subText.setHighlightColor(Color.TRANSPARENT);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignupLogin.this , Login.class);
                startActivity(intent);

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupLogin.this , Signup.class);
                startActivity(intent);
            }
        });


    }
}
