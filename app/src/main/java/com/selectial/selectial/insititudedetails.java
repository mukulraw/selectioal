package com.selectial.selectial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class insititudedetails extends AppCompatActivity {

    TextView name , namee , des ,feature , addcompare;

    Toolbar toolbar;

    TextView like , likes;

    RatingBar ratingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insititudedetails);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //name = findViewById(R.id.name);

        namee = findViewById(R.id.namee);

        des = findViewById(R.id.des);

        feature = findViewById(R.id.feature);

        like = findViewById(R.id.like);

        likes = findViewById(R.id.likes);

        addcompare = findViewById(R.id.addcompare);

        addcompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        ratingBar = findViewById(R.id.ratingBar);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                likes.setVisibility(View.VISIBLE);
                like.setVisibility(View.GONE);
            }
        });


        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                like.setVisibility(View.VISIBLE);
                likes.setVisibility(View.GONE);
            }
        });

    }
}
