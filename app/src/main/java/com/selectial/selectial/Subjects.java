package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.selectial.selectial.getHomePOJO.Sucject;
import com.selectial.selectial.getHomePOJO.getHomeBean;
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

public class Subjects extends Fragment {

    RecyclerView grid;

    TestAdapter adapter;

    List<Sucject> list;

    GridLayoutManager manager;

    ProgressBar bar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_orders , container , false);


        grid = view.findViewById(R.id.grid);
        bar = view.findViewById(R.id.progressBar11);

        list = new ArrayList<>();

        adapter = new TestAdapter(getContext(), list);

        manager = new GridLayoutManager(getContext(), 2);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);


        bar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<getHomeBean> call = cr.getHome(SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id) , SharePreferenceUtils.getInstance().getString(Constant.USER_id) , SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));
        call.enqueue(new Callback<getHomeBean>() {
            @Override
            public void onResponse(Call<getHomeBean> call, Response<getHomeBean> response) {

                if (Objects.equals(response.body().getStatus() , "1")){


                    Log.d("asdasd" , String.valueOf(response.body().getData().getSucjects().size()));

                    adapter.setgrid(response.body().getData().getSucjects());


                }else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                //bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getHomeBean> call, Throwable t) {

                Log.d("asdasda" , t.toString());

                // bar.setVisibility(View.GONE);
            }
        });

        return view;
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>
    {

        Context context;
        List<Sucject>list;

        TestAdapter(Context context , List<Sucject> list)
        {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            View view = LayoutInflater.from(context).inflate(R.layout.home_test_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            final Sucject item = list.get(i);

            viewHolder.math.setText(item.getTitle());


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context , MyTests.class);
                    intent.putExtra("sub_id" , item.getId());
                    intent.putExtra("title" , item.getTitle());
                    context.startActivity(intent);

                }
            });

        }

        public void setgrid(List<Sucject>list){

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView math;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                math = itemView.findViewById(R.id.textView68);

            }
        }
    }

}
