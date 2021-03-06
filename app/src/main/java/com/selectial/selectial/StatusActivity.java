package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.selectial.selectial.questionPOJO.questionBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StatusActivity extends AppCompatActivity {

    String transStatus;
    ImageView image;
    TextView text;
    Button ok;
    ProgressBar progress;
    String pid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        pid = getIntent().getStringExtra("pid");

        transStatus = getIntent().getStringExtra("transStatus");
        image = findViewById(R.id.imageView7);
        text = findViewById(R.id.textView19);
        ok = findViewById(R.id.button13);
        progress = findViewById(R.id.progressBar4);


        switch (transStatus) {
            case "Transaction Successful!":


                progress.setVisibility(View.VISIBLE);


                        progress.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ServiceInterface cr = retrofit.create(ServiceInterface.class);

                Call<questionBean> call = cr.buyPackage(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , pid);

                call.enqueue(new Callback<questionBean>() {
                    @Override
                    public void onResponse(Call<questionBean> call, Response<questionBean> response) {

                        image.setImageResource(R.drawable.success);
                        text.setText("Package Added Successfully. Tests has been added to the particular subject.");

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<questionBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });



                //Toast.makeText(StatusActivity.this , response.body() , Toast.LENGTH_SHORT).show();

                break;
            case "Transaction Declined!":
                text.setText("Payment has been declined by your bank");
                image.setImageResource(R.drawable.failure);
                break;
            default:
                text.setText("Transaction has been cancelled");
                image.setImageResource(R.drawable.failure);
                break;
        }




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(StatusActivity.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/
                finish();
            }
        });



    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatusActivity.this , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
