package com.selectial.selectial;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.selectial.selectial.questionPOJO.Datum;
import com.selectial.selectial.questionPOJO.questionBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Test extends AppCompatActivity {


    TabLayout tabs;

    FragAdapter adapter;

    ViewPager pager;

    String id, time, title;

    TextView ttiittle , ttiimmeer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");

        tabs = findViewById(R.id.textView34);

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

        Call<questionBean> call = cr.getQuestions(id);

        call.enqueue(new Callback<questionBean>() {
            @Override
            public void onResponse(Call<questionBean> call, Response<questionBean> response) {

                tit.setText(title);
                que.setText(String.valueOf(response.body().getData().size()));
                tim.setText(String.valueOf(Float.parseFloat(time) / 60) + " min.");

                adapter = new FragAdapter(getSupportFragmentManager(), pager , response.body().getData());

                pager.setAdapter(adapter);

                tabs.setupWithViewPager(pager);

                pager.setOffscreenPageLimit(response.body().getData().size() - 1);


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<questionBean> call, Throwable t) {
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


        //pager.setOffscreenPageLimit(11);

        /*SpannableStringBuilder builder = new SpannableStringBuilder();

        String blue = tabs.getTabAt(3).getText().toString();

        SpannableString blueSpannable = new SpannableString(blue);
        blueSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, blue.length(), 0);
        builder.append(blueSpannable);

        tabs.getTabAt(3).setText(builder);*/


    }

    class FragAdapter extends FragmentStatePagerAdapter {

        ViewPager pager;
        List<Datum> list = new ArrayList<>();

        FragAdapter(FragmentManager fm, ViewPager pager , List<Datum> list) {
            super(fm);
            this.pager = pager;
            this.list = list;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position + 1);
        }

        @Override
        public Fragment getItem(int i) {
            TestFrag frag = new TestFrag();

            Bundle b = new Bundle();
            b.putInt("position", i);
            if (i == list.size() - 1) {
                b.putBoolean("last", true);
            } else {
                b.putBoolean("last", false);
            }
            b.putString("ques" , list.get(i).getQuestion());
            b.putString("image" , list.get(i).getImage());
            b.putString("opt1" , list.get(i).getOpt1());
            b.putString("opt2" , list.get(i).getOpt2());
            b.putString("opt3" , list.get(i).getOpt3());
            b.putString("opt4" , list.get(i).getOpt4());
            b.putString("qid" , list.get(i).getId());
            b.putString("tid" , id);
            frag.setData(pager , tabs);

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
                Toast.makeText(Test.this, "Test completed", Toast.LENGTH_SHORT).show();

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

    void submitTest()
    {


        //progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<questionBean> call = cr.submitTest(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , id);


        call.enqueue(new Callback<questionBean>() {
            @Override
            public void onResponse(Call<questionBean> call, Response<questionBean> response) {

                Toast.makeText(Test.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                //progress.setVisibility(View.GONE);


                Intent intent = new Intent(Test.this, TestResult.class);
                //startActivity(intent);

            }

            @Override
            public void onFailure(Call<questionBean> call, Throwable t) {
                //progress.setVisibility(View.GONE);
            }
        });




    }

}
