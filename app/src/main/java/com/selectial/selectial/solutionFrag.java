package com.selectial.selectial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.selectial.selectial.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class solutionFrag extends Fragment {

    TextView marks, explanation, index;
    MyView ques, ans, yranswer;
    ImageView image, qimage, aimage, yimage;
String tid;
    Datum item;
    int i;

    String title, time , corr , wro , not;

    TextView tit, tot, tim, cor, mar;

    AnyChartView pie;


    public void setData(Datum item , String tid , int i)
    {
        this.item = item;
        this.tid = tid;
        this.i = i;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.solution_list_model , container , false);

        title = getArguments().getString("title");
        time = getArguments().getString("time");
        tid = getArguments().getString("tid");

        corr = getArguments().getString("corr");
        wro = getArguments().getString("wro");
        not = getArguments().getString("not");

        tit = itemView.findViewById(R.id.textView39);
        tot = itemView.findViewById(R.id.textView41);
        tim = itemView.findViewById(R.id.textView42);
        cor = itemView.findViewById(R.id.textView47);
        mar = itemView.findViewById(R.id.textView45);
        pie = itemView.findViewById(R.id.view6);

        tit.setText(title);
        tim.setText("Time :" + getDurationString(Integer.parseInt(time) / 1000));


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


        List<DataEntry> dataEntries = new ArrayList<>();

        Pie pie1 = AnyChart.pie();

        Float c = Float.parseFloat(corr);
        Float t = Float.parseFloat(wro);
        Float n = Float.parseFloat(not);

        dataEntries.add(new ValueDataEntry("Correct", c));
        dataEntries.add(new ValueDataEntry("Incorrect", t));
        dataEntries.add(new ValueDataEntry("Not Attempted", n));


        pie1.data(dataEntries);

        pie.setChart(pie1);



        ques.setConfig(
                "MathJax.Hub.Config({\n" +
                        "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]}\n" +
                        "});");

        ans.setConfig(
                "MathJax.Hub.Config({\n" +
                        "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]}\n" +
                        "});");

        yranswer.setConfig(
                "MathJax.Hub.Config({\n" +
                        "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]}\n" +
                        "});");


        final String uurl = Constant.BASE_URL + "admin/upload/questions/" + tid + "/";

        marks.setText("Marks: " + item.getMarks());


        index.setText("Question " + String.valueOf(i + 1));

        if (item.getQtype().equals("text") || item.getQtype().equals("latex")) {
            ques.setText(item.getQuestion());
            ques.setVisibility(View.VISIBLE);
            qimage.setVisibility(View.GONE);
        } else {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + item.getQuestion(), qimage, options);
            ques.setVisibility(View.GONE);
            qimage.setVisibility(View.VISIBLE);
        }

        //ques.setText("Ques: " + item.getQuestion());

        if (item.getAtype().equals("text") || item.getAtype().equals("latex")) {
            ans.setText(item.getAnswer());
            ans.setVisibility(View.VISIBLE);
            aimage.setVisibility(View.GONE);
        } else {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + item.getAnswer(), aimage, options);
            ans.setVisibility(View.GONE);
            aimage.setVisibility(View.VISIBLE);
        }


        //ans.setText("Answer.:  " + item.getAnswer());

        if (item.getYtype().equals("text") || item.getYtype().equals("latex")) {
            yranswer.setText(item.getYourans());
            yranswer.setVisibility(View.VISIBLE);
            yimage.setVisibility(View.GONE);
        } else {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(uurl + item.getYourans(), yimage, options);
            yranswer.setVisibility(View.GONE);
            yimage.setVisibility(View.VISIBLE);
        }

        //yranswer.setText("Your Ans: " + item.getYourans());


        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();

        loader.loadImage(item.getImage(), options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                image.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                image.setImageBitmap(loadedImage);
                image.setVisibility(View.VISIBLE);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        explanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), Explanation.class);
                intent.putExtra("que", item.getQuestion());
                intent.putExtra("exp", item.getExplanation());
                intent.putExtra("etype", item.getEtype());
                intent.putExtra("ans", item.getAnswer());
                intent.putExtra("url", uurl);
                getActivity().startActivity(intent);

            }
        });



        return itemView;
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
