package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.selectial.selectial.GetProfilePOJO.GetProfileBean;
import com.selectial.selectial.bannerPOJO.Scholarship;
import com.selectial.selectial.bannerPOJO.bannerBean;
import com.selectial.selectial.getHomePOJO.Package;
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

    CardView scholarshipView;

    ViewPager packageGrid;
    GridLayoutManager pManager;
    PackageAdapter pAdapter;

    List<Scholarship> pList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_layout , container , false);

        circleImageView = view.findViewById(R.id.view11);
        name = view.findViewById(R.id.textView80);
        email = view.findViewById(R.id.textView81);
        grid = view.findViewById(R.id.grid);
        packageGrid = view.findViewById(R.id.package_grid);
        scholarshipView = view.findViewById(R.id.view13);


        list = new ArrayList<>();
        pList = new ArrayList<>();

        adapter = new TestAdapter(getContext() , list);

        //pAdapter = new PackageAdapter(getChildFragmentManager() , pList);

        manager = new GridLayoutManager(getContext() , 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        //packageGrid.setAdapter(pAdapter);


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


           View view = LayoutInflater.from(context).inflate(R.layout.news_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            //final Sucject item = list.get(i);

            //viewHolder.math.setText(item.getTitle());

/*
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context , MyTests.class);
                    intent.putExtra("sub_id" , item.getId());
                    intent.putExtra("title" , item.getTitle());
                    context.startActivity(intent);

                }
            });*/

        }

        public void setgrid(List<Sucject>list){

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            //return list.size();
            return 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            //TextView math;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                //math = itemView.findViewById(R.id.textView68);

            }
        }
    }

    void loadData()
    {

        name.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_name));
        email.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_email));



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);



        Call<GetProfileBean> call2 = cr.profilee(SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call2.enqueue(new Callback<GetProfileBean>() {
            @Override
            public void onResponse(Call<GetProfileBean> call, Response<GetProfileBean> response) {

                if (Objects.equals(response.body().getStatus() , "1")){

                    name.setText(response.body().getData().getName());

                    SharePreferenceUtils.getInstance().saveString(Constant.USER_image , response.body().getData().getImage());

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(response.body().getData().getImage() , circleImageView , options);

                }else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                //bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetProfileBean> call, Throwable t) {


                // bar.setVisibility(View.GONE);
            }
        });


        Call<bannerBean> call = cr.getBanners();

        call.enqueue(new Callback<bannerBean>() {
            @Override
            public void onResponse(Call<bannerBean> call, Response<bannerBean> response) {

                pAdapter = new PackageAdapter(getChildFragmentManager() , response.body().getData().getScholarship());

                packageGrid.setAdapter(pAdapter);

            }

            @Override
            public void onFailure(Call<bannerBean> call, Throwable t) {

            }
        });


        Log.d("streamm1" , SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id));
        Log.d("streamm2" , SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        Log.d("streamm3" , SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));

        String str = "";

        if (!SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id).equals("0"))
        {
            str = SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id);
        }
        else
        {
            str = "";
        }


        Call<getHomeBean> call1 = cr.getHome(SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id) , SharePreferenceUtils.getInstance().getString(Constant.USER_id) , SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));
        call1.enqueue(new Callback<getHomeBean>() {
            @Override
            public void onResponse(Call<getHomeBean> call, Response<getHomeBean> response) {

                if (Objects.equals(response.body().getStatus() , "1")){





                    if (response.body().getData().getScholarship().size() > 0)
                    {
                        //scholarshipView.setVisibility(View.VISIBLE);
                        scholarshipView.setVisibility(View.GONE);
                    }
                    else
                    {
                        scholarshipView.setVisibility(View.GONE);
                    }

                    Log.d("asdasd" , String.valueOf(response.body().getData().getSucjects().size()));

                    adapter.setgrid(response.body().getData().getSucjects());

                    //packageGrid.setVisibility(View.GONE);

                    //pAdapter.setGridData(response.body().getData().getPackage());

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
    }

    class PackageAdapter extends FragmentStatePagerAdapter
    {

        List<Scholarship> ll = new ArrayList<>();

        public PackageAdapter(FragmentManager fm , List<Scholarship> ll) {
            super(fm);
            this.ll = ll;
        }

        @Override
        public Fragment getItem(int i) {
            banner frag = new banner();
            Bundle b = new Bundle();
            b.putString("url" , ll.get(i).getFile());
            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return ll.size();
        }
    }

}
