package com.selectial.selectial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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


    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);




        if (hasPermissions(this, PERMISSIONS)) {

startApp();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        }


    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {

            Log.d("permmm" , "1");

            if (hasPermissions(this , PERMISSIONS)) {

                Log.d("permmm" , "2");

               startApp();

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                    Log.d("permmm" , "3");

                    Toast.makeText(getApplicationContext(), "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                    finish();

                } else {

                    Log.d("permmm" , "4");
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }
        }

    }


    private void updateLoginData() {

        Log.d("data" , "123123");
        Log.d("data" , SharePreferenceUtils.getInstance().getString(Constant.USER_email));
        Log.d("data" , SharePreferenceUtils.getInstance().getString(Constant.USER_password));

        Call<SigninResp> call = serviceInterface.signin(SharePreferenceUtils.getInstance().getString(Constant.USER_email), SharePreferenceUtils.getInstance().getString(Constant.USER_password));
        call.enqueue(new Callback<SigninResp>() {
            @Override
            public void onResponse(Call<SigninResp> call, Response<SigninResp> response) {



                if (response.body() != null && response.isSuccessful()) {

                    //Log.d("data" , response.body().getData().getUserId());

                    if (response.body().getStatus().equals("1")) {
                        // pBar.setVisibility(View.GONE);


                        Toast.makeText(SplashActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_id, response.body().getData().getUserId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_name, response.body().getData().getName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_email, response.body().getData().getEmail());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_phone, response.body().getData().getPhone());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_gender, response.body().getData().getGender());
                        SharePreferenceUtils.getInstance().saveString(Constant.User_age, response.body().getData().getAge());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_class, response.body().getData().getClassName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_class_id, response.body().getData().getClassId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_date, response.body().getData().getCreatedDate());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_image, response.body().getData().getImage());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_isPaid, response.body().getData().getIsPaid());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_status, response.body().getData().getStatus());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_id, response.body().getData().getSubClassId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_name, response.body().getData().getSubClassName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_password, response.body().getData().getPassword());

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();

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

    void startApp()
    {
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

                }


            }
        }, 1500);
    }

}