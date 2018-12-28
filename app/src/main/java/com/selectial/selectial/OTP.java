package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OTP extends AppCompatActivity {

    TextView resend, logout;
    Button verify;
    EditText otp;
    String mEmail, mPassword;
    ProgressBar pBar;

    Retrofit retrofit;
    ServiceInterface serviceInterface;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        back = findViewById(R.id.imageButton3);
        otp = findViewById(R.id.editText);

        resend = findViewById(R.id.textView10);
        verify = findViewById(R.id.button3);
        logout = findViewById(R.id.textView6);
        pBar = findViewById(R.id.progressBar2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();


            }
        });



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String o = otp.getText().toString();

                if (o.length() > 0)
                {

                    pBar.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    serviceInterface = retrofit.create(ServiceInterface.class);

                    Call<SignupResp> call = serviceInterface.verifyOTP(convertPlainString(SharePreferenceUtils.getInstance().getString(Constant.USER_id)), convertPlainString(o));

                    call.enqueue(new Callback<SignupResp>() {
                        @Override
                        public void onResponse(Call<SignupResp> call, Response<SignupResp> response) {

                            if (response.body().getStatus().equals("1"))
                            {
                                Toast.makeText(OTP.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
                                SharePreferenceUtils.getInstance().saveString(Constant.CLS_id, response.body().getData().getClassId());

                                Log.d("userid", SharePreferenceUtils.getInstance().getString(Constant.USER_id));


                                Intent intent = new Intent(OTP.this, SetProfileImage.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                            else
                            {
                                Toast.makeText(OTP.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            pBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<SignupResp> call, Throwable t) {
                            pBar.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    Toast.makeText(OTP.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OTP.this , SignupLogin.class);
                SharePreferenceUtils.getInstance().deletePref();
                startActivity(i);
                finishAffinity();

            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                serviceInterface = retrofit.create(ServiceInterface.class);

                Call<SignupResp> call = serviceInterface.resendOTP(convertPlainString(SharePreferenceUtils.getInstance().getString(Constant.USER_id)), convertPlainString(SharePreferenceUtils.getInstance().getString(Constant.USER_phone)));

                call.enqueue(new Callback<SignupResp>() {
                    @Override
                    public void onResponse(Call<SignupResp> call, Response<SignupResp> response) {

                        Toast.makeText(OTP.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        pBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<SignupResp> call, Throwable t) {
                        pBar.setVisibility(View.GONE);
                    }
                });

            }
        });




    }

    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }

}
