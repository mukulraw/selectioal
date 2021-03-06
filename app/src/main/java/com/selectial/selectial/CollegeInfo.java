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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.GetInsititudePOJO.Datum;
import com.selectial.selectial.GetInsititudePOJO.GetInsititudeBean;
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

public class CollegeInfo extends Fragment {

    RecyclerView grid;

    CollegeAdapter adapter;

    GridLayoutManager manager;

    Spinner spinner;

    List<String> listtt = new ArrayList<>();

    String list;

    List<Datum> lis;

    ProgressBar bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.college_info_layout, container, false);

        listtt = new ArrayList<>();

        grid = view.findViewById(R.id.grid);

        bar = view.findViewById(R.id.progress);

        lis = new ArrayList<>();

        adapter = new CollegeAdapter(getContext(), lis);

        manager = new GridLayoutManager(getContext(), 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        spinner = view.findViewById(R.id.spinner);

        listtt.add("Sort");
        listtt.add("Fees");
        listtt.add("Establishes Year");
        listtt.add("AIR(Medical)");
        listtt.add("AIR(Engg)");
        listtt.add("No.of Students");
        listtt.add("Poplarty");

        ArrayAdapter dataAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listtt);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        bar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<GetInsititudeBean> call = cr.insi(SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call.enqueue(new Callback<GetInsititudeBean>() {
            @Override
            public void onResponse(Call<GetInsititudeBean> call, Response<GetInsititudeBean> response) {


                if (Objects.equals(response.body().getStatus() , "1")){

                    adapter.setgrid(response.body().getData());


                }else {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<GetInsititudeBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });

    }

    class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder> {

        Context context;

        List<Datum> lis = new ArrayList();

        CollegeAdapter(Context context, List<Datum> lis) {

            this.context = context;
            this.lis = lis;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.college_list_model, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            final Datum item = lis.get(i);
            viewHolder.name.setText(item.getName());
            viewHolder.likes.setText(item.getLikes());
            viewHolder.like.setText(item.getLikes());
            viewHolder.ratingBar.setRating(Float.parseFloat(item.getRating()));


            if (item.getIsLiked().equals("1")){

                viewHolder.likes.setVisibility(View.VISIBLE);
            }
            else {
                viewHolder.likes.setVisibility(View.GONE);
            }

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).
                    cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getImage(), viewHolder.imageView, options);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, insititudedetails.class);
                    i.putExtra("id" ,item.getId());
                    i.putExtra("name" ,item.getName());
                    i.putExtra("icon" ,item.getIcon());
                    i.putExtra("image" ,item.getImage());
                    i.putExtra("likes" ,item.getLikes());
                    i.putExtra("rating" ,item.getRating());
                    i.putExtra("fees" ,item.getFees());
                    i.putExtra("establish" ,item.getEstYear());
                    i.putExtra("airengg" ,item.getAirEngg());
                    i.putExtra("airmed" ,item.getAirMed());
                    i.putExtra("center" ,item.getCentres());
                    i.putExtra("students" ,item.getStudents());
                    i.putExtra("faculties" ,item.getFaculties());
                    i.putExtra("details" ,item.getDetails());
                    i.putExtra("has" ,item.getHasFile());
                    i.putExtra("file" ,item.getFile());
                    i.putExtra("isliked" ,item.getIsLiked());
                    context.startActivity(i);
                }
            });

        }

        public void setgrid(List<Datum> list) {

            this.lis = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return lis.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, like , likes;

            RatingBar ratingBar;

            ImageView imageView;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.textView71);

                like = itemView.findViewById(R.id.like);
                likes = itemView.findViewById(R.id.likes);

                ratingBar = itemView.findViewById(R.id.ratingBar);

                imageView = itemView.findViewById(R.id.imageView7);


            }
        }
    }

}
