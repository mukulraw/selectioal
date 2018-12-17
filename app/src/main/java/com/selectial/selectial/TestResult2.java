package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class TestResult2 extends AppCompatActivity {

    RecyclerView grid;

    GridLayoutManager manager;

    SolutionAdapter adapter;

    NestedScrollView scroll;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result2);

        grid = findViewById(R.id.grid);

        scroll = findViewById(R.id.scroll);

        back = findViewById(R.id.imageButton4);

        adapter = new SolutionAdapter(this);

        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.ViewHolder>
    {

        Context context;

        SolutionAdapter(Context context)
        {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = null;
            if (inflater != null) {
                view = inflater.inflate(R.layout.solution_list_model , viewGroup , false);
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

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView ques ,ans , marks , yranswer;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                ques = itemView.findViewById(R.id.textView50);

                ans = itemView.findViewById(R.id.textView51);

                marks = itemView.findViewById(R.id.textView53);

                yranswer = itemView.findViewById(R.id.textView52);
            }
        }
    }


}
