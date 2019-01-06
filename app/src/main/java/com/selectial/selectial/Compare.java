package com.selectial.selectial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.AddComparePOJO.AddCompareBean;
import com.selectial.selectial.comparePOJO.Datum;
import com.selectial.selectial.comparePOJO.compareBean;
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

    List<Datum> list;

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        toolbar = findViewById(R.id.toolbar);
        bar = findViewById(R.id.progress);
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

        adapter = new CompareAdapter(Compare.this, list);

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


    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }


    void loadData() {

        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<compareBean> call = cr.getCompare(SharePreferenceUtils.getInstance().getString(Constant.USER_id));

        call.enqueue(new Callback<compareBean>() {
            @Override
            public void onResponse(Call<compareBean> call, Response<compareBean> response) {

                if (response.body().getData().size() > 0) {

                    adapter.setgrid(response.body().getData());

                } else {
                    adapter.setgrid(response.body().getData());
                    Toast.makeText(Compare.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }

                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<compareBean> call, Throwable t) {
                bar.setVisibility(View.GONE);
            }
        });

    }

    public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.MyViewHolder> {

        Context context;

        List<Datum> list;

        CompareAdapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;

        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.compare_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {


            final Datum item = list.get(i);

            Log.d("position" , item.getId());

            /*if (i == 0) {

                try {

                    Log.d("asdasd" , "asdasd");

                    holder.title.setText("Title");
                    holder.fees.setText("Fees");
                    holder.year.setText("Establishment Year");
                    holder.centre.setText("No. of Centres");
                    holder.med.setText("AIR (med)");
                    holder.engg.setText("AIR (eng)");
                    holder.students.setText("No. of Students");
                    holder.faculties.setText("No. of Faculties");

                    holder.rat.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {*/
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                final ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getImage(), holder.image, options);

                holder.rat.setVisibility(View.VISIBLE);
                holder.rat.setRating(Float.parseFloat(item.getRating()));

                holder.title.setText(item.getName());
                holder.fees.setText("Fees:       " + item.getFees());
                holder.year.setText("Est. Yr.:   " + item.getEstYear());
                holder.centre.setText("Centres:    " + item.getCentres());
                holder.med.setText("AIR (Med.): " + item.getAirMed());
                holder.engg.setText("AIR (Eng.): " + item.getAirEngg());
                holder.students.setText("Students:   " + item.getStudents());
                holder.faculties.setText("Faculties:  " + item.getFaculties());
            //}

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bar.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ServiceInterface cr = retrofit.create(ServiceInterface.class);

                    Call<AddCompareBean> call = cr.removeFromCompare(item.getId());

                    call.enqueue(new Callback<AddCompareBean>() {
                        @Override
                        public void onResponse(Call<AddCompareBean> call, Response<AddCompareBean> response) {



                            bar.setVisibility(View.GONE);

                            loadData();

                        }

                        @Override
                        public void onFailure(Call<AddCompareBean> call, Throwable t) {
                            bar.setVisibility(View.GONE);
                        }
                    });


                }
            });

        }

        void setgrid(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title, fees, year, centre, engg, med, students, faculties , remove;
            ImageView image;
            RatingBar rat;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.textView23);
                fees = itemView.findViewById(R.id.textView67);
                year = itemView.findViewById(R.id.textView70);
                centre = itemView.findViewById(R.id.textView72);
                engg = itemView.findViewById(R.id.textView86);
                med = itemView.findViewById(R.id.textView87);
                students = itemView.findViewById(R.id.textView88);
                faculties = itemView.findViewById(R.id.textView89);
                image = itemView.findViewById(R.id.imageView9);
                rat = itemView.findViewById(R.id.ratingBar2);
                remove = itemView.findViewById(R.id.textView92);
            }
        }
    }
}
