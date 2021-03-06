package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.halilibo.adm.core.DownloadManagerPro;
import com.halilibo.adm.report.listener.DownloadManagerListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.GetProfilePOJO.GetProfileBean;
import com.selectial.selectial.bannerPOJO.Scholarship;
import com.selectial.selectial.bannerPOJO.bannerBean;
import com.selectial.selectial.getHomePOJO.Package;
import com.selectial.selectial.getHomePOJO.Sucject;
import com.selectial.selectial.getHomePOJO.getHomeBean;
import com.selectial.selectial.newsPOJO.newsBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
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

    List<com.selectial.selectial.newsPOJO.Scholarship> list;

    CircleImageView circleImageView;

    TextView name, email;

    CardView scholarshipView;

    ViewPager packageGrid;
    GridLayoutManager pManager;
    PackageAdapter pAdapter;

    List<Scholarship> pList;

    CircleIndicator indicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_layout, container, false);

        circleImageView = view.findViewById(R.id.view11);
        name = view.findViewById(R.id.textView80);
        email = view.findViewById(R.id.textView81);
        grid = view.findViewById(R.id.grid);
        packageGrid = view.findViewById(R.id.package_grid);
        scholarshipView = view.findViewById(R.id.view13);
        indicator = view.findViewById(R.id.indicator);


        list = new ArrayList<>();
        pList = new ArrayList<>();

        adapter = new TestAdapter(getContext(), list);

        //pAdapter = new PackageAdapter(getChildFragmentManager() , pList);

        manager = new GridLayoutManager(getContext(), 1);

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

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

        Context context;
        List<com.selectial.selectial.newsPOJO.Scholarship> list;

        TestAdapter(Context context, List<com.selectial.selectial.newsPOJO.Scholarship> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            View view = LayoutInflater.from(context).inflate(R.layout.news_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

            //final Sucject item = list.get(i);

            final com.selectial.selectial.newsPOJO.Scholarship item = list.get(i);

            if (item.getType().equals("LINK")) {

                viewHolder.math.setText(item.getTitle());
                viewHolder.math.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_global, 0);
            } else {

                viewHolder.math.setText(item.getTitle());
                viewHolder.math.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);

            }


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (item.getType().equals("LINK"))
                    {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(item.getUrl()));
                        startActivity(i);
                    }
                    else
                    {

                        DownloadManagerPro dm = new DownloadManagerPro(context);
                        dm.init("Downloads", 12, new DownloadManagerListener() {
                            @Override
                            public void OnDownloadStarted(long taskId) {

                            }

                            @Override
                            public void OnDownloadPaused(long taskId) {

                            }

                            @Override
                            public void onDownloadProcess(long taskId, double percent, long downloadedLength) {

                            }

                            @Override
                            public void OnDownloadFinished(long taskId) {

                            }

                            @Override
                            public void OnDownloadRebuildStart(long taskId) {

                            }

                            @Override
                            public void OnDownloadRebuildFinished(long taskId) {

                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, "File Downloaded in Downloads", Toast.LENGTH_SHORT).show();
                                        //progress.setVisibility(View.GONE);
                                    }
                                });


                            }

                            @Override
                            public void OnDownloadCompleted(long taskId) {

                            }

                            @Override
                            public void connectionLost(long taskId) {
                                Toast.makeText(context, "Download error", Toast.LENGTH_SHORT).show();
                                //progress.setVisibility(View.GONE);
                            }
                        });

                        String uurrll = item.getFile().replace(" ", "%20");


                        int token = dm.addTask(String.valueOf(System.currentTimeMillis()), uurrll, false, false);
                        try {
                            dm.startDownload(token);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Download error", Toast.LENGTH_SHORT).show();
                            //progress.setVisibility(View.GONE);
                        }

                    }

                }
            });

        }

        public void setgrid(List<com.selectial.selectial.newsPOJO.Scholarship> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView math;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                math = itemView.findViewById(R.id.textView100);

            }
        }
    }

    void loadData() {

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

                if (Objects.equals(response.body().getStatus(), "1")) {

                    name.setText(response.body().getData().getName());

                    SharePreferenceUtils.getInstance().saveString(Constant.USER_image, response.body().getData().getImage());

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(response.body().getData().getImage(), circleImageView, options);

                } else {
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

                try {
                    pAdapter = new PackageAdapter(getChildFragmentManager(), response.body().getData().getScholarship());

                    packageGrid.setAdapter(pAdapter);

                    indicator.setViewPager(packageGrid);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<bannerBean> call, Throwable t) {

            }
        });


        Log.d("streamm1", SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id));
        Log.d("streamm2", SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        Log.d("streamm3", SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));

        String str = "";

        if (!SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id).equals("0")) {
            str = SharePreferenceUtils.getInstance().getString(Constant.USER_sub_class_id);
        } else {
            str = "";
        }


        Call<newsBean> call1 = cr.getNews();
        call1.enqueue(new Callback<newsBean>() {
            @Override
            public void onResponse(Call<newsBean> call, Response<newsBean> response) {

                if (Objects.equals(response.body().getStatus(), "1")) {

                    adapter.setgrid(response.body().getData().getScholarship());

                    //packageGrid.setVisibility(View.GONE);

                    //pAdapter.setGridData(response.body().getData().getPackage());

                } else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                //bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<newsBean> call, Throwable t) {

                Log.d("asdasda", t.toString());

                // bar.setVisibility(View.GONE);
            }
        });
    }

    class PackageAdapter extends FragmentStatePagerAdapter {

        List<Scholarship> ll = new ArrayList<>();

        public PackageAdapter(FragmentManager fm, List<Scholarship> ll) {
            super(fm);
            this.ll = ll;
        }

        @Override
        public Fragment getItem(int i) {
            banner frag = new banner();
            Bundle b = new Bundle();
            b.putString("url", ll.get(i).getFile());
            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return ll.size();
        }
    }

}
