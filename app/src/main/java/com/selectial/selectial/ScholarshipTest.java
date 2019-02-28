package com.selectial.selectial;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.selectial.selectial.scholarshipQuesPOJO.Datum;
import com.selectial.selectial.scholarshipQuesPOJO.scholarshipQuesBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ScholarshipTest extends AppCompatActivity {

    TabLayout tabs;

    FragAdapter adapter;

    ViewPager pager;

    String id, time, title;

    TextView ttiittle, ttiimmeer;

    Button submitTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_test);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");

        tabs = findViewById(R.id.textView34);

        submitTest = findViewById(R.id.button16);
        ttiimmeer = findViewById(R.id.textView33);
        ttiittle = findViewById(R.id.textView27);

        ttiittle.setText(title);

        pager = findViewById(R.id.pager);

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.test_dialog);
        dialog.setCancelable(true);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        dialog.show();

        final ProgressBar progress = dialog.findViewById(R.id.progressBar9);
        final TextView tit = dialog.findViewById(R.id.textView93);
        final TextView tim = dialog.findViewById(R.id.textView97);
        final TextView que = dialog.findViewById(R.id.textView95);
        Button start = dialog.findViewById(R.id.button12);

        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<scholarshipQuesBean> call = cr.getScholarshipQuestions(id);

        call.enqueue(new Callback<scholarshipQuesBean>() {
            @Override
            public void onResponse(Call<scholarshipQuesBean> call, Response<scholarshipQuesBean> response) {

                tit.setText(title);
                que.setText(String.valueOf(response.body().getData().size()));
                tim.setText(String.valueOf(Float.parseFloat(time) / 60000) + " min.");

                adapter = new FragAdapter(getSupportFragmentManager(), pager, response.body().getData());

                pager.setAdapter(adapter);

                tabs.setupWithViewPager(pager);

                pager.setOffscreenPageLimit(response.body().getData().size() - 1);


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<scholarshipQuesBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startTimer();
                dialog.dismiss();

            }
        });

        submitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitTest();

            }
        });

    }

    class FragAdapter extends FragmentStatePagerAdapter {

        ViewPager pager;
        List<Datum> list = new ArrayList<>();

        FragAdapter(FragmentManager fm, ViewPager pager, List<Datum> list) {
            super(fm);
            this.pager = pager;
            this.list = list;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getTitle();
        }

        @Override
        public Fragment getItem(int i) {
            ScholarshipTestFrag frag = new ScholarshipTestFrag();

            Bundle b = new Bundle();

            b.putString("id" , id);

            frag.setData(list.get(i).getQuestion());

            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    void startTimer() {
        CountDownTimer timer = new CountDownTimer((long) Float.parseFloat(time), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ttiimmeer.setText(getDurationString(Integer.parseInt(String.valueOf(millisUntilFinished / 1000))));

            }

            @Override
            public void onFinish() {


                submitTest();
                //finish();
                Toast.makeText(ScholarshipTest.this, "Test completed", Toast.LENGTH_SHORT).show();

            }
        };
        timer.start();
    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    void submitTest() {


        //progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<submitTestBean> call = cr.submitScholarship(SharePreferenceUtils.getInstance().getString(Constant.USER_id), id);


        call.enqueue(new Callback<submitTestBean>() {
            @Override
            public void onResponse(Call<submitTestBean> call, Response<submitTestBean> response) {

                Toast.makeText(ScholarshipTest.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                //progress.setVisibility(View.GONE);

                byte[] data = new byte[0];
                try {
                    data = id.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String base64 = Base64.encodeToString(data, Base64.DEFAULT);

                Intent intent = new Intent(ScholarshipTest.this , AnswerKeyView.class);
                intent.putExtra("url" , Constant.BASE_URL + "/your-answerkey.php?schlorId=" + base64 + "&userId=" + SharePreferenceUtils.getInstance().getString(Constant.USER_id));
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<submitTestBean> call, Throwable t) {
                //progress.setVisibility(View.GONE);
            }
        });


    }

}
