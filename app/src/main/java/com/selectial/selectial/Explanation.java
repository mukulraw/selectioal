package com.selectial.selectial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Explanation extends AppCompatActivity {

    ImageView back;

    String que , ans , exp , etype , uurl;

    TextView etext;
    ZoomageView eimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        que = getIntent().getStringExtra("que");
        ans = getIntent().getStringExtra("ans");
        exp = getIntent().getStringExtra("exp");
        etype = getIntent().getStringExtra("etype");
        uurl = getIntent().getStringExtra("url");

        back = findViewById(R.id.imageButton4);
        etext = findViewById(R.id.etext);
        eimage = findViewById(R.id.eimage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (etype.equals("text"))
        {
            etext.setText(exp);
            etext.setVisibility(View.VISIBLE);
            eimage.setVisibility(View.GONE);
        }
        else
        {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + exp , eimage , options);
            etext.setVisibility(View.GONE);
            eimage.setVisibility(View.VISIBLE);

        }


    }
}
