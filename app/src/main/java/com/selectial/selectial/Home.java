package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.getHomePOJO.Sucject;
import com.selectial.selectial.getHomePOJO.getHomeBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Home extends Fragment {

    RecyclerView grid;

    TestAdapter adapter;

    GridLayoutManager manager;

    List<Sucject> list;

    CircleImageView circleImageView;

    TextView name , email;

    CardView packageView , scholarshipView;

    TextView packageTitle , packageFeature;
    Button packgePurchase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_layout , container , false);

        circleImageView = view.findViewById(R.id.view11);
        name = view.findViewById(R.id.textView80);
        email = view.findViewById(R.id.textView81);
        grid = view.findViewById(R.id.grid);
        packageView = view.findViewById(R.id.view12);
        scholarshipView = view.findViewById(R.id.view13);

        packageTitle = view.findViewById(R.id.textView82);
        packageFeature = view.findViewById(R.id.textView83);
        packgePurchase = view.findViewById(R.id.button9);

        list = new ArrayList<>();

        adapter = new TestAdapter(getContext() , list);

        manager = new GridLayoutManager(getContext() , 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>
    {

        Context context;
        List<Sucject>list;

        TestAdapter(Context context , List<Sucject>list)
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

    void loadData()
    {

        name.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_name));
        email.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_email));


        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(SharePreferenceUtils.getInstance().getString(Constant.USER_image) , circleImageView , options);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);


        Log.d("streamm1" , SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id));
        Log.d("streamm2" , SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        Log.d("streamm3" , SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));

        Call<getHomeBean> call = cr.getHome(SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id) , SharePreferenceUtils.getInstance().getString(Constant.USER_id) , SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));
        call.enqueue(new Callback<getHomeBean>() {
            @Override
            public void onResponse(Call<getHomeBean> call, Response<getHomeBean> response) {

                if (Objects.equals(response.body().getStatus() , "1")){


                    if (response.body().getData().getPackage().size() > 0)
                    {
                        packageView.setVisibility(View.VISIBLE);

                        packageTitle.setText(response.body().getData().getPackage().get(0).getTitle());
                        packageFeature.setText(Html.fromHtml(response.body().getData().getPackage().get(0).getFeatures()));
                        packgePurchase.setText(response.body().getData().getPackage().get(0).getPrice() + " INR");

                    }
                    else
                    {
                        packageView.setVisibility(View.GONE);
                    }

                    if (response.body().getData().getScholarship().size() > 0)
                    {
                        //scholarshipView.setVisibility(View.VISIBLE);
                        scholarshipView.setVisibility(View.GONE);
                    }
                    else
                    {
                        scholarshipView.setVisibility(View.GONE);
                    }

                    adapter.setgrid(response.body().getData().getSucjects());

                }else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                //bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getHomeBean> call, Throwable t) {


                // bar.setVisibility(View.GONE);
            }
        });
    }

}
