package com.selectial.selectial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selectial.selectial.forgotpojo.ForgotBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ForgotPassword extends AppCompatActivity {

    EditText email;

    ProgressBar bar;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.editText);

        bar = findViewById(R.id.progress);

        btn = findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString();

                if (e.length() > 0) {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ServiceInterface cr = retrofit.create(ServiceInterface.class);

                    Call<ForgotBean> call = cr.forgot(e);

                    call.enqueue(new Callback<ForgotBean>() {
                        @Override
                        public void onResponse(Call<ForgotBean> call, Response<ForgotBean> response) {

                            if (Objects.equals(response.body().getStatus(), "1")) {

                                Toast.makeText(ForgotPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                finish();

                                email.setText("");
                            } else {

                                Toast.makeText(ForgotPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            bar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<ForgotBean> call, Throwable t) {

                            bar.setVisibility(View.GONE);

                        }
                    });


                } else {

                    Toast.makeText(ForgotPassword.this, "Please enter a Email", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
}
