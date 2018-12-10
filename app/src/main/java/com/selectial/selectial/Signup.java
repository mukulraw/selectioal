package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.response.SignupResponse;
import com.selectial.selectial.util.DataValidation;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity {

    Button signup;
    EditText username, age, email, password;
    String mUsername, mAge, mEmail, mPassword;
    ToggleSwitch toggleSwitchClass, toggleSwitchGender;
    int classPositionToggle, genderPositionToggle;
    String mGender, mClass;
    TextView alreadyMember;
    ProgressBar pBar;

    Retrofit retrofit;
    ServiceInterface serviceInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setupwidget();
        // getInput();

        pBar.setVisibility(View.GONE);


        //Retrofit
        // pBar.setVisibility(View.GONE);

        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);

        signup = findViewById(R.id.button3);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();

               /* signupReq();
                pBar.setVisibility(View.VISIBLE);*/

                if (mUsername.isEmpty()) {
                    Toast.makeText(Signup.this, " Fill Username First", Toast.LENGTH_SHORT).show();
                } else if (mAge.isEmpty()) {
                    Toast.makeText(Signup.this, "Fill age First", Toast.LENGTH_SHORT).show();
                } else if (DataValidation.isNotValidEmail(mEmail)) {
                    Toast.makeText(Signup.this, " Fill Valid Email", Toast.LENGTH_SHORT).show();
                } else if (DataValidation.isNotValidPassword(mPassword)) {
                    Toast.makeText(Signup.this, "Fill Password", Toast.LENGTH_SHORT).show();
                } else {
                    signupReq();
                    pBar.setVisibility(View.VISIBLE);
                }




                /*Intent intent = new Intent(Signup.this, SetProfileImage.class);
                startActivity(intent);*/

            }
        });
        alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Signup.this, Login.class);
                startActivity(loginIntent);
                finish();
            }
        });

    }

    private void signupReq() {
        Call<SignupResp> call = serviceInterface.signup(convertPlainString(mClass), convertPlainString(mUsername),
                convertPlainString(mGender), convertPlainString(mAge), convertPlainString(mEmail), convertPlainString(mPassword));

        call.enqueue(new Callback<SignupResp>() {
            @Override
            public void onResponse(Call<SignupResp> call, Response<SignupResp> response) {
                if (response.body() != null && response.isSuccessful()) {
                    pBar.setVisibility(View.GONE);

                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(Signup.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


                        Intent intent = new Intent(Signup.this, SetProfileImage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Signup.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<SignupResp> call, Throwable t) {
                pBar.setVisibility(View.GONE);
                Log.e("error", "" + t);

            }
        });
    }

    private void getInput() {

        mUsername = username.getText().toString().trim();
        mAge = age.getText().toString().trim();
        mEmail = email.getText().toString().trim();
        mPassword = password.getText().toString().trim();
        classPositionToggle = toggleSwitchClass.getCheckedTogglePosition();
        genderPositionToggle = toggleSwitchGender.getCheckedTogglePosition();

        if (classPositionToggle == 0) {
            mClass = String.valueOf("1");
        }
        if (classPositionToggle == 1) {
            mClass = String.valueOf("2");
        }
        if (classPositionToggle == 2) {
            mClass = "3";
        }


        if (genderPositionToggle == 0) {
            mGender = "Male";
        }

        if (genderPositionToggle == 1) {
            mGender = "Female";
        }
       /* String info = mUsername + "" + mAge + "" + mEmail + "" + mPassword + "" + mClass + "" + mGender;
        Toast.makeText(this, "" + info, Toast.LENGTH_SHORT).show();
        Log.i("info", info);*/
    }

    private void setupwidget() {
        username = findViewById(R.id.editText3);
        age = findViewById(R.id.editText4);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        toggleSwitchClass = findViewById(R.id.textView15);
        toggleSwitchGender = findViewById(R.id.textView17);
        alreadyMember = findViewById(R.id.textView10);
        pBar = findViewById(R.id.progressBar3);

    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }
}
