package com.selectial.selectial;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CollegeInfo extends Fragment {

    RecyclerView grid;
    CollegeAdapter adapter;
    GridLayoutManager manager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.college_info_layout , container , false);

        grid = view.findViewById(R.id.grid);
        adapter = new CollegeAdapter(getContext());
        manager = new GridLayoutManager(getContext() , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        return view;
    }

    class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder>
    {

        Context context;

        CollegeAdapter(Context context)
        {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = null;
            if (inflater != null) {
                view = inflater.inflate(R.layout.college_list_model , viewGroup , false);
            }
            return new ViewHolder(Objects.requireNonNull(view));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

}