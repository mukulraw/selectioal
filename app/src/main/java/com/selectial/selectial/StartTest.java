package com.selectial.selectial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.selectial.selectial.GetProfilePOJO.GetProfileBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StartTest extends AppCompatActivity {

    Button start;

    TextView text , testCode;

    String title , time , id , inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");
        inst = getIntent().getStringExtra("inst");

        start = findViewById(R.id.button5);

        text = findViewById(R.id.textView30);
        testCode = findViewById(R.id.textView31);

        testCode.setText(title);
        text.setText(Html.fromHtml(inst));

       /* bar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<String> call = cr.s(SharePreferenceUtils.getInstance().getString(Constant.USER_id));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (Objects.equals(response.body().getStatus(), "1")) {


                } else {

                    Toast.makeText(StartTest.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });*/

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTest.this , Test.class);
                intent.putExtra("title" , title);
                intent.putExtra("time" , time);
                intent.putExtra("id" , id);
                startActivity(intent);
                finish();
            }
        });

    }
}
