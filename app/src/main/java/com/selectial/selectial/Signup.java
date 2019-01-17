package com.selectial.selectial;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selectial.selectial.classesPOJO.classesBean;
import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.response.SignupResponse;
import com.selectial.selectial.util.DataValidation;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.ArrayList;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Signup extends AppCompatActivity {

    Button signup;

    EditText username, age, email, password, confirm, phone;

    String mUsername, mAge, mEmail, mPassword, mConfirm, mPhone;

    ToggleSwitch toggleSwitchGender;

    //Spinner chooseClass;

    int classPositionToggle, genderPositionToggle;

    String mGender, mClass = "";

    TextView alreadyMember;

    ProgressBar pBar;

    String selClass = "";

    Retrofit retrofit;

    ServiceInterface serviceInterface;

    ImageButton back;

    List<String> className;
    List<String> classId;

String isSocial;

String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        isSocial = getIntent().getStringExtra("social");

        className = new ArrayList<>();
        classId = new ArrayList<>();

        setupwidget();
        // getInput();

        pBar.setVisibility(View.GONE);


        if (isSocial.equals("1"))
        {

            String nname = getIntent().getStringExtra("name");
            String eemail = getIntent().getStringExtra("email");
            String iidd = getIntent().getStringExtra("id");

            username.setText(nname);
            email.setText(eemail);
            pid = iidd;


        }



        //Retrofit
        // pBar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);

        signup = findViewById(R.id.button3);
        back = findViewById(R.id.imageButton3);

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
                    Toast.makeText(Signup.this, "Fill Valid Email", Toast.LENGTH_SHORT).show();
                } else if (DataValidation.isValidPhoneNumber(mPhone)) {
                    Toast.makeText(Signup.this, "Fill valid phone number", Toast.LENGTH_SHORT).show();
                } else if (DataValidation.isNotValidPassword(mPassword)) {
                    Toast.makeText(Signup.this, "Please enter at least 4 digits password", Toast.LENGTH_SHORT).show();
                } else if (!mConfirm.equals(mPassword)) {
                    Toast.makeText(Signup.this, "Password did not match", Toast.LENGTH_SHORT).show();
                } else {
                    signupReq();
                    pBar.setVisibility(View.VISIBLE);
                }

                /*Intent intent = new Intent(Signup.this, SetProfileImage.class);
                startActivity(intent);*/


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Signup.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dob_popup);
                dialog.setCancelable(true);
                dialog.show();

                Button submit = dialog.findViewById(R.id.button11);
                final DatePicker dp = dialog.findViewById(R.id.view14);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String dd = String.valueOf(dp.getDayOfMonth()) + "-" + String.valueOf(dp.getMonth() + 1) + "-" + dp.getYear();

                        Log.d("dddd" , dd);

                        age.setText(dd);

                        dialog.dismiss();

                    }
                });

            }
        });

        /*chooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    selClass = classId.get(position - 1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        /*pBar.setVisibility(View.VISIBLE);

        Call<classesBean> call = serviceInterface.getClasses();

        call.enqueue(new Callback<classesBean>() {
            @Override
            public void onResponse(Call<classesBean> call, Response<classesBean> response) {


                className.clear();
                classId.clear();

                className.add("Select Class");


                for (int i = 0; i < response.body().getData().size(); i++) {
                    className.add(response.body().getData().get(i).getName());
                    classId.add(response.body().getData().get(i).getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Signup.this,
                        R.layout.spinner_item,className);

                chooseClass.setAdapter(adapter);

                pBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<classesBean> call, Throwable t) {
                pBar.setVisibility(View.GONE);
            }
        });*/

    }

    private void signupReq() {


        if (isSocial.equals("1"))
        {
            Call<SignupResp> call = serviceInterface.socialSignup(convertPlainString(mClass), convertPlainString(mUsername),
                    convertPlainString(mGender), convertPlainString(mAge), convertPlainString(mEmail), convertPlainString(mPassword), convertPlainString(pid) , convertPlainString(mPhone));

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

                            Log.d("userid", SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));

                            Intent intent = new Intent(Signup.this, OTP.class);
                            //Intent intent = new Intent(Signup.this, SetProfileImage.class);
                            startActivity(intent);
                            finishAffinity();
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
        else
        {
            Call<SignupResp> call = serviceInterface.signup(convertPlainString(mClass), convertPlainString(mUsername),
                    convertPlainString(mGender), convertPlainString(mAge), convertPlainString(mEmail), convertPlainString(mPassword), convertPlainString(mPhone));

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

                            Log.d("userid", SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));

                            Intent intent = new Intent(Signup.this, OTP.class);
                            //Intent intent = new Intent(Signup.this, SetProfileImage.class);
                            startActivity(intent);
                            finishAffinity();
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



    }

    private void getInput() {

        mUsername = username.getText().toString().trim();
        mAge = age.getText().toString().trim();
        mEmail = email.getText().toString().trim();
        mPassword = password.getText().toString().trim();
        mConfirm = confirm.getText().toString().trim();
        mPhone = phone.getText().toString().trim();
        mClass = selClass;
        genderPositionToggle = toggleSwitchGender.getCheckedTogglePosition();


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
        confirm = findViewById(R.id.editText5);
        //chooseClass = findViewById(R.id.spinner);
        //toggleSwitchClass = findViewById(R.id.textView15);
        toggleSwitchGender = findViewById(R.id.textView17);
        alreadyMember = findViewById(R.id.textView10);
        phone = findViewById(R.id.phone);
        pBar = findViewById(R.id.progressBar3);

    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }
}
