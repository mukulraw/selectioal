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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestResult2 extends AppCompatActivity {

    RecyclerView grid;

    GridLayoutManager manager;

    SolutionAdapter adapter;

    NestedScrollView scroll;

    ImageButton back;

    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result2);

        grid = findViewById(R.id.grid);

        scroll = findViewById(R.id.scroll);

        back = findViewById(R.id.imageButton4);

        list = new ArrayList<>();

        adapter = new SolutionAdapter(this, list);

        manager = new GridLayoutManager(this, 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


 /* bar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<String> call = cr.ss(SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (Objects.equals(response.body().getStatus() , "1")){

                    Toast.makeText(getContext(), "P", Toast.LENGTH_SHORT).show();
                }
                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {


                bar.setVisibility(View.GONE);

            }
        });
*/


    }

    class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.ViewHolder> {

        Context context;
        List<String> list = new ArrayList();

        SolutionAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            View view = LayoutInflater.from(context).inflate(R.layout.solution_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            String item = list.get(i);

            viewHolder.ques.setText("");
            viewHolder.ans.setText("");
            viewHolder.marks.setText("");
            viewHolder.yranswer.setText("");


        }

        public void setgrid(List<String>list){

            this.list = list;
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView ques, ans, marks, yranswer;

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
