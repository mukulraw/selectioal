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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.response.SigninResponse;
import com.selectial.selectial.util.DataValidation;
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

public class Login extends AppCompatActivity {

    TextView forgot, signupNow;
    Button login;
    EditText email, password;
    String mEmail, mPassword;
    ProgressBar pBar;

    Retrofit retrofit;
    ServiceInterface serviceInterface;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpWidget();

        pBar.setVisibility(View.GONE);

        back = findViewById(R.id.imageButton3);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        signupNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(Login.this, Signup.class);
                startActivity(signupIntent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInput();

                if (mEmail.length() == 0) {
                    Toast.makeText(Login.this, "Not valid email", Toast.LENGTH_SHORT).show();
                } else if (DataValidation.isNotValidPassword(mPassword)) {
                    Toast.makeText(Login.this, "Not valid password", Toast.LENGTH_SHORT).show();
                } else {
                    signinReq();
                    pBar.setVisibility(View.VISIBLE);
                }

               /* signinReq();
                pBar.setVisibility(View.VISIBLE);*/

               /* Intent intent = new Intent(Login.this , StartTest.class);
                startActivity(intent);*/

            }
        });

    }

    private void signinReq() {

        Call<SigninResp> call = serviceInterface.signin(mEmail, mPassword);
        call.enqueue(new Callback<SigninResp>() {
            @Override
            public void onResponse(Call<SigninResp> call, Response<SigninResp> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {
                        pBar.setVisibility(View.GONE);



                        Toast.makeText(Login.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

                        if (response.body().getData().getIsVerified().equals("0"))
                        {
                            Toast.makeText(Login.this, "Your phone number is not verified, please check OTP", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, OTP.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else
                        {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }


                    } else {
                        pBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<SigninResp> call, Throwable t) {
                pBar.setVisibility(View.GONE);

                Log.e("error", "" + t);
                // Toast.makeText(Login.this, "invalid login"+t, Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void getInput() {

        mEmail = email.getText().toString().trim();
        mPassword = password.getText().toString().trim();

        // Toast.makeText(this, ""+mEmail+" "+mPassword, Toast.LENGTH_SHORT).show();
    }

    private void setUpWidget() {
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        forgot = findViewById(R.id.textView10);
        login = findViewById(R.id.button3);
        signupNow = findViewById(R.id.textView6);
        pBar = findViewById(R.id.progressBar2);

    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }
}
