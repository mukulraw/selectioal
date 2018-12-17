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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollegeInfo extends Fragment {

    RecyclerView grid;

    CollegeAdapter adapter;

    GridLayoutManager manager;

    Spinner spinner;

    List<String> listtt = new ArrayList<>();

    String list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.college_info_layout, container, false);

        listtt = new ArrayList<>();

        grid = view.findViewById(R.id.grid);

        adapter = new CollegeAdapter(getContext());

        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        spinner = view.findViewById(R.id.spinner);

        listtt.add("Sort");
        listtt.add("Fees");
        listtt.add("Establishes Year");
        listtt.add("AIR(Medical)");
        listtt.add("AIR(Engg)");
        listtt.add("No.of Students");
        listtt.add("Poplarty");

        ArrayAdapter dataAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listtt);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder> {

        Context context;

        //List<>list = new ArrayList();

        CollegeAdapter(Context context) {
            this.context = context;
            //this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.college_list_model, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, insititudedetails.class);
                    context.startActivity(i);
                }
            });

        }

        /*public void setgrid(List<>list){

            this.list = list;
            notifyDataSetChanged();
        }
*/
        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, likes;

            RatingBar ratingBar;

            ImageView imageView;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.textView71);

                likes = itemView.findViewById(R.id.textView72);

                ratingBar = itemView.findViewById(R.id.ratingBar);

                imageView = itemView.findViewById(R.id.imageView7);
            }
        }
    }

}
