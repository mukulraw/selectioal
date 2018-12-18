package com.selectial.selectial;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TestFrag extends Fragment {

    Button skip, submit;

    boolean last;

    int position;

    ViewPager pager;

    public void setData(ViewPager pager) {
        this.pager = pager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_frag_layout, container, false);

        last = getArguments().getBoolean("last");

        position = getArguments().getInt("position");

        skip = view.findViewById(R.id.button7);

        submit = view.findViewById(R.id.button6);

        if (last) {
            skip.setVisibility(View.GONE);
        } else {
            skip.setVisibility(View.VISIBLE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (last) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.finish_test_popup_layout);
                    dialog.show();

                    Button finish = dialog.findViewById(R.id.button8);

                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getContext(), TestResult.class);
                            startActivity(intent);
                            dialog.dismiss();

                        }
                    });

                } else {
                    pager.setCurrentItem(position + 1);
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!last) {
                    pager.setCurrentItem(position + 1);
                }

            }
        });

        return view;
    }
}
