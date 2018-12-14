package com.selectial.selectial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Compare extends AppCompatActivity {

    RecyclerView grid;

    LinearLayoutManager manager;

    CompareAdapter adapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle("Compare List");

        grid = findViewById(R.id.grid);

        manager = new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false);

        adapter = new CompareAdapter(this);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

    }


    public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.MyViewHolder>{

        Context context;

        public CompareAdapter(Context context){

            this.context = context;

        }


        @NonNull
        @Override
        public CompareAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.compare_list_model , viewGroup , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CompareAdapter.MyViewHolder myViewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 16;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView title , feature , remove;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                title = findViewById(R.id.title);
                feature = findViewById(R.id.feature);
                remove = findViewById(R.id.remove);
            }
        }
    }
}
