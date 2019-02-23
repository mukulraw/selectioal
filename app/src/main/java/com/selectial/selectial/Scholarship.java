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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Scholarship extends Fragment {

    RecyclerView testgrid , scholarshipgrid;

    GridLayoutManager manager  , manager1;

    TestgridAdapter adapter;

    SchgridAdapter adapter1;

    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scholarship_layout , container , false);


        testgrid = view.findViewById(R.id.recyclerView);
        scholarshipgrid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 1);
        manager1 = new GridLayoutManager(getContext() , 1);

        adapter = new TestgridAdapter(getContext());
        adapter1 = new SchgridAdapter(getContext());

        testgrid.setAdapter(adapter);
        scholarshipgrid.setAdapter(adapter1);

        testgrid.setLayoutManager(manager);
        scholarshipgrid.setLayoutManager(manager1);

        progressBar = view.findViewById(R.id.progressBar14);






        return view;
    }

    public class TestgridAdapter extends RecyclerView.Adapter<TestgridAdapter.My>{


        Context context;

        public TestgridAdapter(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public TestgridAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.scholar_list_model , viewGroup , false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TestgridAdapter.My my, int i) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class My extends RecyclerView.ViewHolder{


            TextView testname , status;

            Button download;

            public My(@NonNull View itemView) {
                super(itemView);


                testname = itemView.findViewById(R.id.textView68);
                status = itemView.findViewById(R.id.textView69);
                download = itemView.findViewById(R.id.button10);
            }
        }
    }






    public class SchgridAdapter extends RecyclerView.Adapter<SchgridAdapter.My>{

        Context context;


        public SchgridAdapter(Context context){

            this.context = context;
        }


        @NonNull
        @Override
        public SchgridAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.scholar_list_model , viewGroup , false);

            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SchgridAdapter.My my, int i) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class My extends RecyclerView.ViewHolder{

            TextView testname , status;

            Button download;


            public My(@NonNull View itemView) {
                super(itemView);

                testname = itemView.findViewById(R.id.textView68);
                status = itemView.findViewById(R.id.textView69);
                download = itemView.findViewById(R.id.button10);
            }
        }
    }









}
