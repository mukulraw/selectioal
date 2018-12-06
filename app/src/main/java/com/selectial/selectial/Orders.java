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
        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


    }

    class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>
    {

        Context context;

        OrdersAdapter(Context context)
        {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = null;
            if (inflater != null) {
                view = inflater.inflate(R.layout.order_list_model , viewGroup , false);
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
