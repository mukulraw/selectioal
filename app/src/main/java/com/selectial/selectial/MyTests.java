package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.halilibo.adm.core.DownloadManagerPro;
import com.halilibo.adm.report.listener.DownloadManagerListener;
import com.selectial.selectial.testListPOJO.Datum;
import com.selectial.selectial.testListPOJO.testListBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyTests extends AppCompatActivity {

    TextView toolbar;
    ProgressBar progress;

    String subjectId;
    String title;
    ImageView back;
    TabLayout tabs;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tests);

        subjectId = getIntent().getStringExtra("sub_id");
        title = getIntent().getStringExtra("title");


        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.textView27);
        back = findViewById(R.id.imageButton4);
        progress = findViewById(R.id.progressBar7);

        toolbar.setText(title);

        tabs.addTab(tabs.newTab().setText("Download PDF"));
        tabs.addTab(tabs.newTab().setText("Online Test"));

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("Download PDF");
        tabs.getTabAt(1).setText("Online Test");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                Download frag = new Download();
                Bundle b = new Bundle();
                b.putString("sub_id", subjectId);
                frag.setArguments(b);
                return frag;
            } else {
                Online frag = new Online();
                Bundle b = new Bundle();
                b.putString("sub_id", subjectId);
                frag.setArguments(b);
                return frag;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
