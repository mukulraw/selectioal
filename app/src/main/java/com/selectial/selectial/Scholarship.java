package com.selectial.selectial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.selectial.selectial.getScholarshipPOJO.Trial;
import com.selectial.selectial.getScholarshipPOJO.getScholarshipBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Scholarship extends Fragment {

    RecyclerView testgrid , scholarshipgrid;

    GridLayoutManager manager  , manager1;

    TestgridAdapter adapter;

    SchgridAdapter adapter1;

    ProgressBar progressBar;

    List<com.selectial.selectial.getScholarshipPOJO.Scholarship> schlist;

    List<Trial> trilist;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scholarship_layout , container , false);

        schlist = new ArrayList<>();
        trilist = new ArrayList<>();

        testgrid = view.findViewById(R.id.recyclerView);
        scholarshipgrid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 1);
        manager1 = new GridLayoutManager(getContext() , 1);

        adapter = new TestgridAdapter(getContext() , trilist);
        adapter1 = new SchgridAdapter(getContext());

        testgrid.setAdapter(adapter);
        scholarshipgrid.setAdapter(adapter1);

        testgrid.setLayoutManager(manager);
        scholarshipgrid.setLayoutManager(manager1);

        progressBar = view.findViewById(R.id.progressBar14);






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface serviceInterface = retrofit.create(ServiceInterface.class);

        Call<getScholarshipBean> call = serviceInterface.getScholarshipTests(SharePreferenceUtils.getInstance().getString(Constant.USER_id));

        call.enqueue(new Callback<getScholarshipBean>() {
            @Override
            public void onResponse(Call<getScholarshipBean> call, Response<getScholarshipBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setData(response.body().getTrial());
                }




                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<getScholarshipBean> call, Throwable t) {
progressBar.setVisibility(View.GONE);
            }
        });


    }

    public class TestgridAdapter extends RecyclerView.Adapter<TestgridAdapter.My>{


        Context context;
        List<Trial> trilist = new ArrayList<>();

        public TestgridAdapter(Context context , List<Trial> trilist){
            this.context = context;
            this.trilist = trilist;
        }

        @NonNull
        @Override
        public TestgridAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.scholar_list_model , viewGroup , false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final TestgridAdapter.My my, int i) {

            final Trial item = trilist.get(i);

            my.testname.setText(item.getTitle());

            my.status.setText(item.getCreatedDate());

            if (item.getStatus().equals("1"))
            {
                my.download.setText("VIEW RESULT");
            }
            else
            {
                my.download.setText("START TEST");
            }

            my.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (my.download.getText().toString().equals("VIEW RESULT"))
                    {

                        byte[] data = new byte[0];
                        try {
                            data = item.getId().getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

                        Intent intent = new Intent(context , AnswerKeyView.class);
                        intent.putExtra("url" , Constant.BASE_URL + "/your-answerkey.php?schlorId=" + base64 + "&userId=" + SharePreferenceUtils.getInstance().getString(Constant.USER_id));
                        context.startActivity(intent);

                    }
                    else
                    {
                        Intent intent = new Intent(context , ScholarshipStartTest.class);
                        intent.putExtra("title" , item.getTitle());
                        intent.putExtra("time" , item.getTime());
                        intent.putExtra("id" , item.getId());
                        intent.putExtra("inst" , item.getInstructions());
                        context.startActivity(intent);
                    }

                }
            });

        }

        public void setData(List<Trial> trilist)
        {
            this.trilist = trilist;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return trilist.size();
        }

        public class My extends RecyclerView.ViewHolder{


            TextView testname , status;

            Button download;

            public My(@NonNull View itemView) {
                super(itemView);


                testname = itemView.findViewById(R.id.textView68);
                status = itemView.findViewById(R.id.textView69);
                download = itemView.findViewById(R.id.button10);
            }
        }
    }






    public class SchgridAdapter extends RecyclerView.Adapter<SchgridAdapter.My>{

        Context context;


        public SchgridAdapter(Context context){

            this.context = context;
        }


        @NonNull
        @Override
        public SchgridAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.scholar_list_model , viewGroup , false);

            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SchgridAdapter.My my, int i) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class My extends RecyclerView.ViewHolder{

            TextView testname , status;

            Button download;


            public My(@NonNull View itemView) {
                super(itemView);

                testname = itemView.findViewById(R.id.textView68);
                status = itemView.findViewById(R.id.textView69);
                download = itemView.findViewById(R.id.button10);
            }
        }
    }









}
