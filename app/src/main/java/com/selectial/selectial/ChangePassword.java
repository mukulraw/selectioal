package com.selectial.selectial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.selectial.selectial.ChangePasswordPOJo.ChangePasswordBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.DataValidation;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ChangePassword extends AppCompatActivity {

    Button submit;

    EditText newpassword , confirmpassword;

    ProgressBar bar;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newpassword = findViewById(R.id.np);

        confirmpassword = findViewById(R.id.cp);

        bar = findViewById(R.id.progress);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = newpassword.getText().toString().trim();
                String c = confirmpassword.getText().toString().trim();

                if (n.length()>3){

                    if (n.equals(c)){

                        bar.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constant.BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ServiceInterface cr = retrofit.create(ServiceInterface.class);
                        Call<ChangePasswordBean>call = cr.cpp(SharePreferenceUtils.getInstance().getString(Constant.USER_id) ,n );
                        call.enqueue(new Callback<ChangePasswordBean>() {
                            @Override
                            public void onResponse(Call<ChangePasswordBean> call, Response<ChangePasswordBean> response) {

                                if (Objects.equals(response.body().getStatus(),"1")){

                                    Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    finish();
                                    newpassword.setText("");
                                    confirmpassword.setText("");

                                }else {
                                    Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                bar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<ChangePasswordBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);

                            }
                        });


                    }else {
                        Toast.makeText(ChangePassword.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                    }

                }else if (DataValidation.isNotValidPassword(n)){

                    Toast.makeText(ChangePassword.this, "Please enter atleat 4 digits password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
