package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.selectial.selectial.solutionPOJO.Datum;
import com.selectial.selectial.solutionPOJO.solutionBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TestResult extends AppCompatActivity {

    ViewPager grid;


    //SolutionAdapter adapter;

    //NestedScrollView scroll;

    ImageButton back;

    String title, time, tid;

    TextView tit, tot, tim, cor, mar;

    List<Datum> list;

    AnyChartView pie;

    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        list = new ArrayList<>();

        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");
        tid = getIntent().getStringExtra("tid");

        grid = findViewById(R.id.grid);
        tabs = findViewById(R.id.tabLayout);
        tit = findViewById(R.id.textView39);
        tot = findViewById(R.id.textView41);
        tim = findViewById(R.id.textView42);
        cor = findViewById(R.id.textView47);
        mar = findViewById(R.id.textView45);
        pie = findViewById(R.id.view6);

        tit.setText(title);
        tim.setText("Time :" + getDurationString(Integer.parseInt(time) / 1000));


        back = findViewById(R.id.imageButton4);

        list = new ArrayList<>();

        //adapter = new SolutionAdapter(this, list);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        loadData();


         /* bar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<String> call = cr.ss(SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (Objects.equals(response.body().getStatus() , "1")){

                    Toast.makeText(getContext(), "P", Toast.LENGTH_SHORT).show();
                }
                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {


                bar.setVisibility(View.GONE);

            }
        });
*/

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    void loadData() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);


        Call<solutionBean> call = cr.viewResult(SharePreferenceUtils.getInstance().getString(Constant.USER_id), tid);

        call.enqueue(new Callback<solutionBean>() {
            @Override
            public void onResponse(Call<solutionBean> call, Response<solutionBean> response) {

                tot.setText("Total Ques.: " + response.body().getTotal());
                cor.setText("Correct Answers: " + response.body().getCorrect());
                mar.setText("Total Marks:   " + response.body().getMarks());


                List<DataEntry> dataEntries = new ArrayList<>();

                Pie pie1 = AnyChart.pie();

                Float c = Float.parseFloat(response.body().getCorrect());
                Float t = Float.parseFloat(String.valueOf(response.body().getTotal()));

                dataEntries.add(new ValueDataEntry("Correct", c));
                dataEntries.add(new ValueDataEntry("Incorrect", t - c));


                pie1.data(dataEntries);

                pie.setChart(pie1);

                FragAdapter adapter = new FragAdapter(getSupportFragmentManager() , response.body().getData() , response.body().getCorrect() , response.body().getWrong() , response.body().getNot());
                grid.setAdapter(adapter);
                tabs.setupWithViewPager(grid);

            }

            @Override
            public void onFailure(Call<solutionBean> call, Throwable t) {

            }
        });

    }


    /*@Override
    public void onBackPressed() {

        Intent intent = new Intent(TestResult.this , MainActivity.class);

        startActivity(intent);

        finishAffinity();
    }*/


    class FragAdapter extends FragmentStatePagerAdapter
    {
        List<Datum> list = new ArrayList<>();
        String corr , wro , not;

        public FragAdapter(FragmentManager fm , List<Datum> list , String corr , String wro , String not) {
            super(fm);
            this.list = list;
            this.corr = corr;
            this.wro = wro;
            this.not = not;
        }

        @Override
        public Fragment getItem(int i) {

            solutionFrag frag = new solutionFrag();
            Bundle b = new Bundle();

            b.putString("title" , title);
            b.putString("time" , time);
            b.putString("tid" , tid);
            b.putString("corr" , corr);
            b.putString("wro" , wro);
            b.putString("not" , not);

            frag.setArguments(b);

            frag.setData(list.get(i) , tid , i);
            return frag;

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position + 1);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


    class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.ViewHolder> {

        Context context;

        List<Datum> list = new ArrayList<>();

        SolutionAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.solution_list_model, viewGroup, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

            viewHolder.setIsRecyclable(false);

            final Datum item = list.get(i);

            final String uurl = Constant.BASE_URL + "admin/upload/questions/" + tid + "/";

            viewHolder.marks.setText("Marks: " + item.getMarks());


            viewHolder.index.setText("Question " + String.valueOf(i + 1));

            if (item.getQtype().equals("text") || item.getQtype().equals("latex")) {
                viewHolder.ques.setText(item.getQuestion());
                viewHolder.ques.setVisibility(View.VISIBLE);
                viewHolder.qimage.setVisibility(View.GONE);
            } else {
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(uurl + item.getQuestion(), viewHolder.qimage, options);
                viewHolder.ques.setVisibility(View.GONE);
                viewHolder.qimage.setVisibility(View.VISIBLE);
            }

            //viewHolder.ques.setText("Ques: " + item.getQuestion());

            if (item.getAtype().equals("text") || item.getAtype().equals("latex")) {
                viewHolder.ans.setText(item.getAnswer());
                viewHolder.ans.setVisibility(View.VISIBLE);
                viewHolder.aimage.setVisibility(View.GONE);
            } else {
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(uurl + item.getAnswer(), viewHolder.aimage, options);
                viewHolder.ans.setVisibility(View.GONE);
                viewHolder.aimage.setVisibility(View.VISIBLE);
            }


            //viewHolder.ans.setText("Answer.:  " + item.getAnswer());

            if (item.getYtype().equals("text") || item.getYtype().equals("latex")) {
                viewHolder.yranswer.setText(item.getYourans());
                viewHolder.yranswer.setVisibility(View.VISIBLE);
                viewHolder.yimage.setVisibility(View.GONE);
            } else {
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(uurl + item.getYourans(), viewHolder.yimage, options);
                viewHolder.yranswer.setVisibility(View.GONE);
                viewHolder.yimage.setVisibility(View.VISIBLE);
            }

            //viewHolder.yranswer.setText("Your Ans: " + item.getYourans());


            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();

            loader.loadImage(item.getImage(), options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    viewHolder.image.setVisibility(View.GONE);

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    viewHolder.image.setImageBitmap(loadedImage);
                    viewHolder.image.setVisibility(View.VISIBLE);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });

            viewHolder.explanation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, Explanation.class);
                    intent.putExtra("que", item.getQuestion());
                    intent.putExtra("exp", item.getExplanation());
                    intent.putExtra("etype", item.getEtype());
                    intent.putExtra("ans", item.getAnswer());
                    intent.putExtra("url", uurl);
                    context.startActivity(intent);

                }
            });

        }

        public void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView marks, explanation, index;
            MyView ques, ans, yranswer;
            ImageView image, qimage, aimage, yimage;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                ques = itemView.findViewById(R.id.qtext);
                qimage = itemView.findViewById(R.id.qimage);

                ans = itemView.findViewById(R.id.text1);
                aimage = itemView.findViewById(R.id.image1);

                marks = itemView.findViewById(R.id.textView53);

                yranswer = itemView.findViewById(R.id.text2);
                yimage = itemView.findViewById(R.id.image2);

                image = itemView.findViewById(R.id.view16);
                index = itemView.findViewById(R.id.textView104);

                explanation = itemView.findViewById(R.id.textView98);

                ques.setConfig(
                        "MathJax.Hub.Config({\n" +
                                "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                "         SVG: { linebreaks: { automatic: true } }\n" +
                                "});");

                ans.setConfig(
                        "MathJax.Hub.Config({\n" +
                                "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                "         SVG: { linebreaks: { automatic: true } }\n" +
                                "});");

                yranswer.setConfig(
                        "MathJax.Hub.Config({\n" +
                                "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                "         SVG: { linebreaks: { automatic: true } }\n" +
                                "});");

            }
        }
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

}
