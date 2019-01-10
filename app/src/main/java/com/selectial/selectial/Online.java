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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.halilibo.adm.core.DownloadManagerPro;
import com.halilibo.adm.report.listener.DownloadManagerListener;
import com.selectial.selectial.onlineTestPOJO.Datum;
import com.selectial.selectial.onlineTestPOJO.onlineTestBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Online  extends Fragment {

    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    TestAdapter adapter;
    List<Datum> list;
    String subjectId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout , container , false);

        subjectId = getArguments().getString("sub_id");

        list = new ArrayList<>();

        progress = view.findViewById(R.id.progressBar8);
        grid = view.findViewById(R.id.grid);
        manager = new GridLayoutManager(getContext() , 1);
        adapter = new TestAdapter(getContext() , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

    }

    void loadData()
    {
        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<onlineTestBean> call = cr.getOnlineTestBySubjects(subjectId , SharePreferenceUtils.getInstance().getString(Constant.USER_id));

        call.enqueue(new Callback<onlineTestBean>() {
            @Override
            public void onResponse(Call<onlineTestBean> call, Response<onlineTestBean> response) {


                if (response.body().getStatus().equals("1"))
                {
                    adapter.setgrid(response.body().getData());
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<onlineTestBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

        Context context;
        List<Datum> list = new ArrayList();

        TestAdapter(Context context, List<Datum> list) {
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
        public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

            final Datum item = list.get(i);

            holder.name.setText(item.getTitle());
            holder.date.setText(item.getCreatedDate());

            if (item.getStatus().equals("0"))
            {
                holder.action.setText("TAKE TEST");
            }
            else
            {
                holder.action.setText("VIEW RESULT");
            }




            holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.action.getText().toString().equals("TAKE TEST"))
                    {
                        Intent intent = new Intent(context , Test.class);
                        intent.putExtra("title" , item.getTitle());
                        intent.putExtra("time" , item.getTime());
                        intent.putExtra("id" , item.getId());
                        context.startActivity(intent);
                    }


                }
            });



        }

        public void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name , date;

            Button action;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                name = itemView.findViewById(R.id.textView68);
                date = itemView.findViewById(R.id.textView69);
                action = itemView.findViewById(R.id.button10);


            }
        }
    }



}
