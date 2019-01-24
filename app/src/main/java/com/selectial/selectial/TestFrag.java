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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    String tid , qid , que , ima , op1 , op2 , op3 , op4 , title , time , qtype , type1 , type2 , type3 , type4;



    TabLayout tabs;

    ImageView image;

    ProgressBar progress;


    TextView ques , text1 , text2 , text3 , text4;
    CheckBox check1 , check2 , check3 , check4;

    ImageView qimage , image1 , image2 , image3 , image4;

    String uurl = "";

    String pos = "";

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
        title = getArguments().getString("title");
        time = getArguments().getString("time");
        qtype = getArguments().getString("qtype");
        type1 = getArguments().getString("type1");
        type2 = getArguments().getString("type2");
        type3 = getArguments().getString("type3");
        type4 = getArguments().getString("type4");

        last = getArguments().getBoolean("last");

        position = getArguments().getInt("position");

        uurl = Constant.BASE_URL + "admin/upload/questions/" + tid + "/";


        skip = view.findViewById(R.id.button7);
        progress = view.findViewById(R.id.progressBar10);
        image = view.findViewById(R.id.view15);


        ques = view.findViewById(R.id.qtext);
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);

        check1 = view.findViewById(R.id.opt1);
        check2 = view.findViewById(R.id.opt2);
        check3 = view.findViewById(R.id.opt3);
        check4 = view.findViewById(R.id.opt4);

        qimage = view.findViewById(R.id.qimage);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);


        if (type1.equals("text"))
        {
            text1.setText(op1);
            text1.setVisibility(View.VISIBLE);
            image1.setVisibility(View.GONE);
        }
        else
        {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + op1 , image1 , options);
            text1.setVisibility(View.GONE);
            image1.setVisibility(View.VISIBLE);
        }


        if (type2.equals("text"))
        {
            text2.setText(op2);
            text2.setVisibility(View.VISIBLE);
            image2.setVisibility(View.GONE);
        }
        else
        {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + op2 , image2 , options);
            text2.setVisibility(View.GONE);
            image2.setVisibility(View.VISIBLE);
        }

        if (type3.equals("text"))
        {
            text3.setText(op3);
            text3.setVisibility(View.VISIBLE);
            image3.setVisibility(View.GONE);
        }
        else
        {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + op3 , image3 , options);
            text3.setVisibility(View.GONE);
            image3.setVisibility(View.VISIBLE);
        }

        if (type4.equals("text"))
        {
            text4.setText(op4);
            text4.setVisibility(View.VISIBLE);
            image4.setVisibility(View.GONE);
        }
        else
        {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + op4 , image4 , options);
            text4.setVisibility(View.GONE);
            image4.setVisibility(View.VISIBLE);
        }


        submit = view.findViewById(R.id.button6);

        if (qtype.equals("text"))
        {
            ques.setText(que);
            ques.setVisibility(View.VISIBLE);
            qimage.setVisibility(View.GONE);
        }
        else
        {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + que , qimage , options);
            ques.setVisibility(View.GONE);
            qimage.setVisibility(View.VISIBLE);
        }



        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(ima , image , options);



        if (last) {
            skip.setVisibility(View.GONE);
            submit.setText("SUBMIT");
        } else {
            skip.setVisibility(View.VISIBLE);
            submit.setText("NEXT");
        }

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    pos = "1";
                    setChech();
                }
                else
                {
                    pos = "";
                    setChech();
                }

            }
        });

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    pos = "2";
                    setChech();
                }
                else
                {
                    pos = "";
                    setChech();
                }

            }
        });

        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    pos = "3";
                    setChech();
                }
                else
                {
                    pos = "";
                    setChech();
                }

            }
        });

        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    pos = "4";
                    setChech();
                }
                else
                {
                    pos = "";
                    setChech();
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (last) {




                    progress.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ServiceInterface cr = retrofit.create(ServiceInterface.class);

                    Log.d("userID" , SharePreferenceUtils.getInstance().getString(Constant.USER_id));
                    Log.d("qid" , qid);
                    Log.d("aaa" , pos);


                    Call<questionBean> call = cr.submitAnswer(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , qid , pos);

                    Toast.makeText(getContext(), pos, Toast.LENGTH_SHORT).show();

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


                    if (pos.length() > 0)
                    {


                        progress.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constant.BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ServiceInterface cr = retrofit.create(ServiceInterface.class);

                        Log.d("userID" , SharePreferenceUtils.getInstance().getString(Constant.USER_id));
                        Log.d("qid" , qid);
                        Log.d("aaa" , pos);

                        Call<questionBean> call = cr.submitAnswer(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , qid , pos);

                        Toast.makeText(getContext(), pos, Toast.LENGTH_SHORT).show();

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

        Call<submitTestBean> call = cr.submitTest(SharePreferenceUtils.getInstance().getString(Constant.USER_id) , tid);


        call.enqueue(new Callback<submitTestBean>() {
            @Override
            public void onResponse(Call<submitTestBean> call, Response<submitTestBean> response) {

                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                progress.setVisibility(View.GONE);



                Intent intent = new Intent(getContext(), TestResult.class);
                intent.putExtra("title" , title);
                intent.putExtra("time" , time);
                intent.putExtra("tid" , tid);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<submitTestBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




    }


    void setChech()
    {

        if (pos.equals("1"))
        {
            check1.setChecked(true);
            check2.setChecked(false);
            check3.setChecked(false);
            check4.setChecked(false);
        }
        else if (pos.equals("2"))
        {
            check1.setChecked(false);
            check2.setChecked(true);
            check3.setChecked(false);
            check4.setChecked(false);
        }
        else if (pos.equals("3"))
        {
            check1.setChecked(false);
            check2.setChecked(false);
            check3.setChecked(true);
            check4.setChecked(false);
        }
        else if (pos.equals("4"))
        {
            check1.setChecked(false);
            check2.setChecked(false);
            check3.setChecked(false);
            check4.setChecked(true);
        }
        else
        {
            check1.setChecked(false);
            check2.setChecked(false);
            check3.setChecked(false);
            check4.setChecked(false);
        }

    }


}
