package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SplashActivity extends AppCompatActivity {


    Retrofit retrofit;
    ServiceInterface serviceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);


        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {

                if (SharePreferenceUtils.getInstance().getString(Constant.USER_id).equalsIgnoreCase("")) {

                    Intent intent = new Intent(SplashActivity.this, SignupLogin.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    updateLoginData();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }


            }
        }, 800);


    }

    private void updateLoginData() {

        Call<SigninResp> call = serviceInterface.signin(convertPlainString(SharePreferenceUtils.getInstance().getString(Constant.USER_email)), convertPlainString(SharePreferenceUtils.getInstance().getString(Constant.USER_password)));
        call.enqueue(new Callback<SigninResp>() {
            @Override
            public void onResponse(Call<SigninResp> call, Response<SigninResp> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {
                        // pBar.setVisibility(View.GONE);
                        Toast.makeText(SplashActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_id, response.body().getData().getUserId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_name, response.body().getData().getName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_email, response.body().getData().getEmail());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_gender, response.body().getData().getGender());
                        SharePreferenceUtils.getInstance().saveString(Constant.User_age, response.body().getData().getAge());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_class, response.body().getData().getClassName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_date, response.body().getData().getCreatedDate());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_image, response.body().getData().getImage());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_isPaid, response.body().getData().getIsPaid());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_status, response.body().getData().getStatus());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_id, response.body().getData().getSubClassId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_name, response.body().getData().getSubClassName());
                        SharePreferenceUtils.getInstance().getString(Constant.USER_password, response.body().getData().getPassword());

                       // Toast.makeText(SplashActivity.this, ""+SharePreferenceUtils.getInstance().getString(Constant.USER_name), Toast.LENGTH_SHORT).show();


                    } else {
                        // pBar.setVisibility(View.GONE);
                        //  Toast.makeText(Login.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<SigninResp> call, Throwable t) {
                // pBar.setVisibility(View.GONE);

                Log.e("error", "" + t);
                // Toast.makeText(Login.this, "invalid login"+t, Toast.LENGTH_SHORT).show();


            }
        });
    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }
}