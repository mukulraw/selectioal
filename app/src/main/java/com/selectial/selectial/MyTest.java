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
import android.widget.Toast;

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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyTest extends Fragment {

    RecyclerView grid;

    TestAdapter adapter;

    GridLayoutManager manager;

    List<String> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_test_layout, container, false);

        grid = view.findViewById(R.id.grid);

        list = new ArrayList<>();

        adapter = new TestAdapter(getContext(), list);

        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);


    /*    bar.setVisibility(View.GONE);

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

                if (Objects.equals(response.body().getStatus(), "1")) {

                    adapter.setgrid(response.body().getData());

                } else {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                bar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                bar.setVisibility(View.VISIBLE);

            }
        });

*/
        return view;
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

        Context context;
        List<String> list = new ArrayList();

        TestAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.test_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            String item = list.get(i);
            viewHolder.comm.setText("");
            viewHolder.subject.setText("");
            viewHolder.status.setText("");
            viewHolder.vieww.setText("");

        }

        public void setgrid(List<String> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView comm, subject, status, vieww;

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
