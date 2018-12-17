package com.selectial.selectial;

import android.graphics.Color;
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
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class Test extends AppCompatActivity {


    TabLayout tabs;

    FragAdapter adapter;

    ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tabs = findViewById(R.id.textView34);

        pager = findViewById(R.id.pager);

        adapter = new FragAdapter(getSupportFragmentManager() , pager);

        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

        pager.setOffscreenPageLimit(11);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        String blue = tabs.getTabAt(3).getText().toString();

        SpannableString blueSpannable = new SpannableString(blue);
        blueSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, blue.length(), 0);
        builder.append(blueSpannable);

        tabs.getTabAt(3).setText(builder);


    }

    class FragAdapter extends FragmentStatePagerAdapter {

        ViewPager pager;

        FragAdapter(FragmentManager fm , ViewPager pager) {
            super(fm);
            this.pager = pager;
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
            if (i == 11) {
                b.putBoolean("last", true);
            } else {
                b.putBoolean("last", false);
            }
            frag.setData(pager);

            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return 12;
        }
    }

}
