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
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.GetProfilePOJO.GetProfileBean;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Home extends Fragment {

    RecyclerView grid;

    TestAdapter adapter;

    GridLayoutManager manager;

    List<String>list;

    CircleImageView circleImageView;

    TextView name , email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_layout , container , false);

        circleImageView = view.findViewById(R.id.view11);
        name = view.findViewById(R.id.textView80);
        email = view.findViewById(R.id.textView81);
        grid = view.findViewById(R.id.grid);

        list = new ArrayList<>();

        adapter = new TestAdapter(getContext() , list);

        manager = new GridLayoutManager(getContext() , 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<GetProfileBean> call = cr.profilee(SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call.enqueue(new Callback<GetProfileBean>() {
            @Override
            public void onResponse(Call<GetProfileBean> call, Response<GetProfileBean> response) {

                if (Objects.equals(response.body().getStatus() , "1")){

                    name.setText(response.body().getData().getName());
                    email.setText(response.body().getData().getName());

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

        return view;
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>
    {

        Context context;
        List<String>list = new ArrayList();

        TestAdapter(Context context , List<String>list)
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



            String item = list.get(i);
            viewHolder.math.setText("");
            viewHolder.status.setText("");
            viewHolder.commerace.setText("");


            viewHolder.takeTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context , StartTest.class);
                    context.startActivity(intent);

                }
            });

        }

        public void setgrid(List<String>list){

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            Button takeTest;

            TextView math , status , commerace;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                takeTest = itemView.findViewById(R.id.button10);

                math = itemView.findViewById(R.id.textView68);

                status = itemView.findViewById(R.id.textView69);

                commerace = itemView.findViewById(R.id.textView67);

            }
        }
    }

}
