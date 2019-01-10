package com.selectial.selectial;

import android.content.Context;
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
import com.selectial.selectial.testListPOJO.Datum;
import com.selectial.selectial.testListPOJO.testListBean;
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

public class Download extends Fragment {

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

        Call<testListBean> call = cr.getTestBySubjects(subjectId , SharePreferenceUtils.getInstance().getString(Constant.USER_id));

        call.enqueue(new Callback<testListBean>() {
            @Override
            public void onResponse(Call<testListBean> call, Response<testListBean> response) {


                if (response.body().getStatus().equals("1"))
                {
                    adapter.setgrid(response.body().getData());
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<testListBean> call, Throwable t) {
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
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            final Datum item = list.get(i);

            holder.name.setText(item.getTitle());
            holder.date.setText(item.getCreatedDate());

            if (item.getType().equals("PDF"))
            {
                holder.action.setText("DOWNLOAD");
            }
            else
            {
                holder.action.setText("TAKE TEST");
            }



            holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress.setVisibility(View.VISIBLE);



                    DownloadManagerPro dm = new DownloadManagerPro(context);
                    dm.init("SelectialIndia", 12, new DownloadManagerListener() {
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
                                    Toast.makeText(context , "File Downloaded in SelectialIndia" , Toast.LENGTH_SHORT).show();
                                    progress.setVisibility(View.GONE);
                                }
                            });




                        }

                        @Override
                        public void OnDownloadCompleted(long taskId) {

                        }

                        @Override
                        public void connectionLost(long taskId) {
                            Toast.makeText(context , "Download error" , Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    });

                    String uurrll = item.getFile().replace(" " , "%20");


                    int token = dm.addTask(item.getFileName() , uurrll , false , false);
                    try {
                        dm.startDownload(token);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context , "Download error" , Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }

                    /*PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                            .setDatabaseEnabled(false)
                            .setReadTimeout(30_000)
                            .setConnectTimeout(30_000)
                            .build();

                    PRDownloader.initialize(getApplicationContext(), config);

                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

                    int downloadId = PRDownloader.download(item.getFile() , path , item.getFileName())
                            .build()
                            .setOnProgressListener(new OnProgressListener() {
                                @Override
                                public void onProgress(Progress progress) {

                                }
                            })
                            .start(new OnDownloadListener() {
                                @Override
                                public void onDownloadComplete() {

                                    Toast.makeText(context , "File Downloaded in Downloads" , Toast.LENGTH_SHORT).show();

                                    progress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Error error) {

                                    Toast.makeText(context , error.toString() , Toast.LENGTH_SHORT).show();
                                    progress.setVisibility(View.GONE);

                                }
                            });*/

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
