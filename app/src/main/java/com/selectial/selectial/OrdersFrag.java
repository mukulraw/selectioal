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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.selectial.selectial.ordersPOJO.Datum;
import com.selectial.selectial.ordersPOJO.ordersBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OrdersFrag extends Fragment {

    RecyclerView grid;

    OrdersAdapter adapter;

    GridLayoutManager manager;

    ProgressBar bar;

    List<Datum> list;

//    ImageButton back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_orders , container , false);


        grid = view.findViewById(R.id.grid);
        bar = view.findViewById(R.id.progressBar11);

        list = new ArrayList<>();

        adapter = new OrdersAdapter(getContext(), list);

        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);


        bar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<ordersBean> call = cr.getOrders(SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call.enqueue(new Callback<ordersBean>() {
            @Override
            public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {


                if (Objects.equals(response.body().getStatus(), "1")) {

                    adapter.setgrid(response.body().getData());

                }
                else {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ordersBean> call, Throwable t) {

                bar.setVisibility(View.GONE);
            }
        });

        return view;
    }

    class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

        Context context;

        List<Datum> list = new ArrayList();

        OrdersAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.order_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


            Datum item = list.get(i);
            viewHolder.orderid.setText(item.getTitle());

            viewHolder.date.setText(item.getCreatedDate());
            viewHolder.inr.setText("INR " + item.getPrice());


        }

        public void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView orderid, date, inr;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                orderid = itemView.findViewById(R.id.textView76);



                date = itemView.findViewById(R.id.textView79);

                inr = itemView.findViewById(R.id.textView77);
            }
        }
    }

}
