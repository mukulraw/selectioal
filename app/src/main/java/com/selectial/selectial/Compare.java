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
import android.widget.ProgressBar;
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

import static com.selectial.selectial.bean.getContext;

public class Compare extends AppCompatActivity {

    RecyclerView grid;

    LinearLayoutManager manager;

    CompareAdapter adapter;

    Toolbar toolbar;

    List<String> list;

    ProgressBar bar;

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

        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        list = new ArrayList<>();

        adapter = new CompareAdapter(this, list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);


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


                if (Objects.equals(response.body().getStatus(), "1")) {

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


    public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.MyViewHolder> {

        Context context;

        List<String> list = new ArrayList();

        public CompareAdapter(Context context, List<String> list) {

            this.context = context;
            this.list = list;

        }


        @NonNull
        @Override
        public CompareAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.compare_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CompareAdapter.MyViewHolder myViewHolder, int i) {


            String item = list.get(i);
            myViewHolder.remove.setText("");
            myViewHolder.title.setText("");
            myViewHolder.feature.setText("");


        }

        public void setgrid(List<String> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title, feature, remove;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                title = findViewById(R.id.title);

                feature = findViewById(R.id.feature);

                remove = findViewById(R.id.remove);
            }
        }
    }
}
