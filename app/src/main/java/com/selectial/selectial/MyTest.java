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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyTest extends Fragment {

    RecyclerView grid;

    TestAdapter adapter;

    GridLayoutManager manager;

   // List<>list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_test_layout, container, false);

        grid = view.findViewById(R.id.grid);

       // list = new ArrayList<>();

        adapter = new TestAdapter(getContext());

        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        return view;
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

        Context context;
        //List<>list = new ArrayList();

        TestAdapter(Context context) {
            this.context = context;
            // this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

          View view = LayoutInflater.from(context).inflate(R.layout.test_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {






        }

       /* public void setgrid(List<>list){

            this.list = list;
            notifyDataSetChanged();
        }
*/
        @Override
        public int getItemCount() {
            return 10;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView comm, subject, status ,vieww;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                comm = itemView.findViewById(R.id.textView67);

                subject = itemView.findViewById(R.id.textView68);

                status = itemView.findViewById(R.id.textView69);

                vieww = itemView.findViewById(R.id.textView70);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, TestResult2.class);
                        context.startActivity(intent);

                    }
                });
            }
        }
    }

}
