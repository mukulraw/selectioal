package com.selectial.selectial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.selectial.selectial.scholarshipQuesPOJO.Question;

import java.util.ArrayList;
import java.util.List;

public class ScholarshipTestFrag extends Fragment {

    TabLayout tabs;

    FragAdapter adapter;

    ViewPager pager;

    String id, time, title;

    TextView ttiittle, ttiimmeer;

    List<Question> list = new ArrayList<>();

    public void setData(List<Question> list)
    {
        this.list = list;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scholar_frag_layout , container , false);

        id = getArguments().getString("id");
        tabs = view.findViewById(R.id.textView34);

        ttiimmeer = view.findViewById(R.id.textView33);
        ttiittle = view.findViewById(R.id.textView27);

        ttiittle.setText(title);

        pager = view.findViewById(R.id.pager);

        ttiimmeer.setVisibility(View.GONE);
        ttiittle.setVisibility(View.GONE);

        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), pager , list , tabs);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        return view;
    }

    class FragAdapter extends FragmentStatePagerAdapter {

        ViewPager pager;
        List<Question> list = new ArrayList<>();
        TabLayout tabs;

        FragAdapter(FragmentManager fm, ViewPager pager, List<Question> list , TabLayout tabs) {
            super(fm);
            this.pager = pager;
            this.list = list;
            this.tabs = tabs;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position + 1);
        }

        @Override
        public Fragment getItem(int i) {
            ScholTestFrag frag = new ScholTestFrag();

            Bundle b = new Bundle();
            b.putInt("position", i);
            if (i == list.size() - 1) {
                b.putBoolean("last", true);
            } else {
                b.putBoolean("last", false);
            }
            b.putString("ques", list.get(i).getQuestion());
            b.putString("qtype", list.get(i).getQtype());
            b.putString("image", list.get(i).getImage());
            b.putString("opt1", list.get(i).getOpt1());
            b.putString("type1", list.get(i).getType1());
            b.putString("opt2", list.get(i).getOpt2());
            b.putString("type2", list.get(i).getType2());
            b.putString("opt3", list.get(i).getOpt3());
            b.putString("type3", list.get(i).getType3());
            b.putString("opt4", list.get(i).getOpt4());
            b.putString("type4", list.get(i).getType4());
            b.putString("qid", list.get(i).getId());
            b.putString("tid", id);
            b.putString("title", title);
            b.putString("time", time);
            frag.setData(pager, tabs);

            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
