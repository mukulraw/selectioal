package com.selectial.selectial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.selectial.selectial.EditProfilePOJO.EditProfileBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.Objects;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EditProfile extends AppCompatActivity {


    EditText name , email , age;

    Button submit;

    ToggleSwitch toggleSwitch;

    int g;

    ImageView back;

    String gender;

    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        submit = findViewById(R.id.submit);
        toggleSwitch = findViewById(R.id.textView17);
        bar = findViewById(R.id.progress);
        back = findViewById(R.id.imageButton3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                String a = age.getText().toString();
                g = toggleSwitch.getCheckedTogglePosition();

                if (g == 0) {
                    gender = "Male";
                }

                if (g == 1) {
                    gender = "Female";
                }

                bar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ServiceInterface cr = retrofit.create(ServiceInterface.class);

                Call<EditProfileBean>call = cr.edit(SharePreferenceUtils.getInstance().getString(Constant.USER_id) ,n , gender,a);

                call.enqueue(new Callback<EditProfileBean>() {
                    @Override
                    public void onResponse(Call<EditProfileBean> call, Response<EditProfileBean> response) {

                        if (Objects.equals(response.body().getStatus() , "1")){

                            Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            finish();
                            name.setText("");
                            age.setText("");


                        }else {

                            Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<EditProfileBean> call, Throwable t) {

                        bar.setVisibility(View.GONE);

                    }
                });


            }
        });


    }
}
