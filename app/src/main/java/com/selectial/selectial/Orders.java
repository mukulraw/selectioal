package com.selectial.selectial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Orders extends AppCompatActivity {

    RecyclerView grid;

    OrdersAdapter adapter;

    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        grid = findViewById(R.id.grid);

        adapter = new OrdersAdapter(this);

        manager = new GridLayoutManager(this, 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);



    }

    class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

        Context context;

        //List<>list = new ArrayList();

        OrdersAdapter(Context context) {
            this.context = context;
            //this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = null;
            if (inflater != null) {
                view = inflater.inflate(R.layout.order_list_model, viewGroup, false);
            }
            return new ViewHolder(Objects.requireNonNull(view));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        }

       /* public void setgrid(List<>list){

            this.list = list;

            notifyDataSetChanged();
        }*/

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView orderid, packagename, date, inr;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                orderid = itemView.findViewById(R.id.textView76);

                packagename = itemView.findViewById(R.id.textView78);

                date = itemView.findViewById(R.id.textView79);

                inr = itemView.findViewById(R.id.textView77);
            }
        }
    }


}
