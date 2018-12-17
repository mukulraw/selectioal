package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.GetStreamPOJO.Datum;
import com.selectial.selectial.GetStreamPOJO.GetStreamBean;
import com.selectial.selectial.UpdateStreamPOJO.UpdateStreamBean;
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

public class Interest extends AppCompatActivity {

    TextView proceed;

    RecyclerView grid;

    GridLayoutManager manager;

    GridAddapter adapter;

    List<Datum> list;

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        proceed = findViewById(R.id.textView21);

        bar = findViewById(R.id.progressBar5);

        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext(), 2);

        list = new ArrayList<>();

        adapter = new GridAddapter(this, list);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<GetStreamBean> call = cr.steam(SharePreferenceUtils.getInstance().getString(Constant.CLS_id));

        Log.d("clsid", SharePreferenceUtils.getInstance().getString(Constant.CLS_id));

        call.enqueue(new Callback<GetStreamBean>() {
            @Override
            public void onResponse(Call<GetStreamBean> call, Response<GetStreamBean> response) {

                if (Objects.equals(response.body().getStatus(), "1")) {

                    adapter.setgrid(response.body().getData());

                    //Toast.makeText(Interest.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(Interest.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetStreamBean> call, Throwable t) {

                bar.setVisibility(View.GONE);
            }
        });

    }

    public class GridAddapter extends RecyclerView.Adapter<GridAddapter.MyViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();

        public GridAddapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public GridAddapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.grid_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GridAddapter.MyViewHolder myViewHolder, int i) {

            final Datum item = list.get(i);

            myViewHolder.text.setText(item.getName());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getImage(), myViewHolder.imageView, options);

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bar.setVisibility(View.GONE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ServiceInterface cr = retrofit.create(ServiceInterface.class);

                    Call<UpdateStreamBean> call = cr.udatestream(item.getId(), SharePreferenceUtils.getInstance().getString(Constant.USER_id));

                    call.enqueue(new Callback<UpdateStreamBean>() {
                        @Override
                        public void onResponse(Call<UpdateStreamBean> call, Response<UpdateStreamBean> response) {

                            if (Objects.equals(response.body().getStatus(), "1")) {

                                Toast.makeText(Interest.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                                if (SharePreferenceUtils.getInstance().getString(Constant.CLS_id).equals("3"))
                                {

                                    Intent i = new Intent(context, StartTest.class);
                                    context.startActivity(i);
                                    finish();
                                }
                                else
                                {

                                    Intent i = new Intent(context, MainActivity.class);
                                    context.startActivity(i);
                                    finish();
                                }


                            } else {
                                Toast.makeText(Interest.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            bar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<UpdateStreamBean> call, Throwable t) {

                            bar.setVisibility(View.GONE);
                        }
                    });
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

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView text;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                text = itemView.findViewById(R.id.text);
                imageView = itemView.findViewById(R.id.image);

            }
        }
    }
}
