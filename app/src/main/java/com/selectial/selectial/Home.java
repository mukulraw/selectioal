package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Home extends Fragment {

    RecyclerView grid;
    TestAdapter adapter;
    GridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout , container , false);

        grid = view.findViewById(R.id.grid);
        adapter = new TestAdapter(getContext());
        manager = new GridLayoutManager(getContext() , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        return view;
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>
    {

        Context context;

        TestAdapter(Context context)
        {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = null;
            if (inflater != null) {
                view = inflater.inflate(R.layout.home_test_list_model , viewGroup , false);
            }
            return new ViewHolder(Objects.requireNonNull(view));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            viewHolder.takeTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context , StartTest.class);
                    context.startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            Button takeTest;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                takeTest = itemView.findViewById(R.id.button10);
            }
        }
    }

}
