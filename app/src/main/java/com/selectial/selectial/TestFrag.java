package com.selectial.selectial;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.questionPOJO.questionBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TestFrag extends Fragment {

    Button skip, submit;

    boolean last;

    int position;

    ViewPager pager;

    String tid , qid , que , ima , op1 , op2 , op3 , op4;

    TextView question;

    RadioButton opt1 , opt2 , opt3 , opt4;

    TabLayout tabs;

    ImageView image;

    ProgressBar progress;

    RadioGroup group;

    public void setData(ViewPager pager , TabLayout tabs) {
        this.pager = pager;
        this.tabs = tabs;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_frag_layout, container, false);

        tid = getArguments().getString("tid");
        qid = getArguments().getString("qid");
        que = getArguments().getString("ques");
        ima = getArguments().getString("image");
        op1 = getArguments().getString("opt1");
        op2 = getArguments().getString("opt2");
        op3 = getArguments().getString("opt3");
        op4 = getArguments().getString("opt4");

        last = getArguments().getBoolean("last");

        position = getArguments().getInt("position");

        skip = view.findViewById(R.id.button7);
        progress = view.findViewById(R.id.progressBar10);
        image = view.findViewById(R.id.view15);
        question = view.findViewById(R.id.textView35);
        group = view.findViewById(R.id.group);

        opt1 = view.findViewById(R.id.opt1);
        opt2 = view.findViewById(R.id.opt2);
        opt3 = view.findViewById(R.id.opt3);
        opt4 = view.findViewById(R.id.opt4);



        opt1.setText(op1);
        opt2.setText(op2);
        opt3.setText(op3);
        opt4.setText(op4);


        submit = view.findViewById(R.id.button6);

        question.setText(que);


        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(ima , image , options);



        if (last) {
            skip.setVisibility(View.GONE);
        } else {
            skip.setVisibility(View.VISIBLE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (last) {


                    int iidd = group.getCheckedRadioButtonId();

                    String aa = "";

                    if (iidd > -1)
                    {
                        RadioButton btn = (RadioButton)group.findViewById(iidd);
                        aa = btn.getText().toString();
                    }
                    else
                    {
                        aa = "";
                    }





                    progress.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ServiceInterface cr = retrofit.create(ServiceInterface.class);

                    Log.d("userID" , SharePreferenceUtils.getInstance().getString(Constant.USER_id));
                    Log.d("qid" , qid);
                    Log.d("aaa" , aa);


                    Call<questionBean> call = cr.submitAnswer(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , qid , aa);

                    call.enqueue(new Callback<questionBean>() {
                        @Override
                        public void onResponse(Call<questionBean> call, Response<questionBean> response) {

                            progress.setVisibility(View.GONE);

                            SpannableStringBuilder builder = new SpannableStringBuilder();

                            String blue = tabs.getTabAt(position).getText().toString();

                            SpannableString blueSpannable = new SpannableString(blue);
                            blueSpannable.setSpan(new ForegroundColorSpan(Color.GRAY), 0, blue.length(), 0);
                            builder.append(blueSpannable);

                            tabs.getTabAt(position).setText(builder);


                            submitTest();

                        }

                        @Override
                        public void onFailure(Call<questionBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });




                } else {

                    int iidd = group.getCheckedRadioButtonId();

                    if (iidd > -1)
                    {

                        RadioButton btn = (RadioButton)group.findViewById(iidd);



                        progress.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constant.BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ServiceInterface cr = retrofit.create(ServiceInterface.class);

                        Log.d("userID" , SharePreferenceUtils.getInstance().getString(Constant.USER_id));
                        Log.d("qid" , qid);
                        Log.d("aaa" , btn.getText().toString());

                        Call<questionBean> call = cr.submitAnswer(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , qid , btn.getText().toString());

                        call.enqueue(new Callback<questionBean>() {
                            @Override
                            public void onResponse(Call<questionBean> call, Response<questionBean> response) {

                                progress.setVisibility(View.GONE);

                                SpannableStringBuilder builder = new SpannableStringBuilder();

                                String blue = tabs.getTabAt(position).getText().toString();

                                SpannableString blueSpannable = new SpannableString(blue);
                                blueSpannable.setSpan(new ForegroundColorSpan(Color.GRAY), 0, blue.length(), 0);
                                builder.append(blueSpannable);

                                tabs.getTabAt(position).setText(builder);


                                pager.setCurrentItem(position + 1);

                            }

                            @Override
                            public void onFailure(Call<questionBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please choose an answer", Toast.LENGTH_SHORT).show();
                    }








                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!last) {

                    SpannableStringBuilder builder = new SpannableStringBuilder();

                    String blue = tabs.getTabAt(position).getText().toString();

                    SpannableString blueSpannable = new SpannableString(blue);
                    blueSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, blue.length(), 0);
                    builder.append(blueSpannable);

                    tabs.getTabAt(position).setText(builder);

                    pager.setCurrentItem(position + 1);
                }

            }
        });

        return view;
    }

    void submitTest()
    {


        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<questionBean> call = cr.submitTest(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , tid);


        call.enqueue(new Callback<questionBean>() {
            @Override
            public void onResponse(Call<questionBean> call, Response<questionBean> response) {

                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                progress.setVisibility(View.GONE);



                Intent intent = new Intent(getContext(), TestResult.class);
                //startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<questionBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }

}
