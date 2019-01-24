package com.selectial.selectial;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.AddComparePOJO.AddCompareBean;
import com.selectial.selectial.GetInsititudePOJO.GetInsititudeBean;
import com.selectial.selectial.InsitituteDetailsPOJO.InsititutedetailsBean;
import com.selectial.selectial.LikeInsititutePOJO.GetLikeInsitituteBean;
import com.selectial.selectial.RatePOJO.RateBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class insititudedetails extends AppCompatActivity {

    TextView name, namee, fee, estyrr, centerr, airengg, airmed, student, faculties, addcompare;

    Toolbar toolbar;

    TextView like, likes;

    RatingBar ratingBar;

    ProgressBar bar;

    String insid, nme, icon, imge, lik, ratt, fees, estyr, airme, airen, center, students, islike, facu , deta , ha , fil;


    ImageView imageView;
    LinearLayout rat;

    TextView more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insititudedetails);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrow);
        more = findViewById(R.id.more);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //name = findViewById(R.id.name);

        namee = findViewById(R.id.namee);

        fee = findViewById(R.id.fees);

        estyrr = findViewById(R.id.estyr);

        centerr = findViewById(R.id.center);

        airengg = findViewById(R.id.airengg);

        airmed = findViewById(R.id.airmed);

        student = findViewById(R.id.student);

        faculties = findViewById(R.id.faculties);
        imageView = findViewById(R.id.image);

        name = findViewById(R.id.namee);

        like = findViewById(R.id.like);

        likes = findViewById(R.id.likes);

        addcompare = findViewById(R.id.addcompare);
        rat = findViewById(R.id.rat);


        addcompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bar.setVisibility(View.GONE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ServiceInterface cr = retrofit.create(ServiceInterface.class);

                Call<AddCompareBean> call = cr.addd(insid, SharePreferenceUtils.getInstance().getString(Constant.USER_id));
                call.enqueue(new Callback<AddCompareBean>() {
                    @Override
                    public void onResponse(Call<AddCompareBean> call, Response<AddCompareBean> response) {


                        if (Objects.equals(response.body().getStatus(), "1")) {

                            Toast.makeText(insititudedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(insititudedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<AddCompareBean> call, Throwable t) {

                        bar.setVisibility(View.GONE);

                    }
                });


            }
        });

        ratingBar = findViewById(R.id.ratingBar);

        bar = findViewById(R.id.progress);



       /* like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                likes.setVisibility(View.VISIBLE);
                like.setVisibility(View.GONE);
            }
        });


        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                like.setVisibility(View.VISIBLE);
                likes.setVisibility(View.GONE);
            }
        });*/


        insid = getIntent().getStringExtra("id");
        nme = getIntent().getStringExtra("name");
        icon = getIntent().getStringExtra("icon");
        imge = getIntent().getStringExtra("image");
        lik = getIntent().getStringExtra("likes");
        ratt = getIntent().getStringExtra("rating");
        fees = getIntent().getStringExtra("fees");
        estyr = getIntent().getStringExtra("establish");
        center = getIntent().getStringExtra("center");
        islike = getIntent().getStringExtra("isliked");
        students = getIntent().getStringExtra("students");
        airen = getIntent().getStringExtra("airengg");
        airme = getIntent().getStringExtra("airmed");
        facu = getIntent().getStringExtra("faculties");
        deta = getIntent().getStringExtra("details");
        ha = getIntent().getStringExtra("has");
        fil = getIntent().getStringExtra("file");


        like.setText(lik);
        likes.setText(lik);

        if (islike.equals("1")) {


            likes.setVisibility(View.VISIBLE);

        } else {
            likes.setVisibility(View.GONE);
        }


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bar.setVisibility(View.GONE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ServiceInterface cr = retrofit.create(ServiceInterface.class);

                Call<GetLikeInsitituteBean> call = cr.likee(SharePreferenceUtils.getInstance().getString(Constant.USER_id), insid);
                call.enqueue(new Callback<GetLikeInsitituteBean>() {
                    @Override
                    public void onResponse(Call<GetLikeInsitituteBean> call, Response<GetLikeInsitituteBean> response) {


                        if (Objects.equals(response.body().getStatus(), "1")) {


                            likes.setVisibility(View.VISIBLE);
                            start();

                            Toast.makeText(insititudedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(insititudedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<GetLikeInsitituteBean> call, Throwable t) {

                        bar.setVisibility(View.GONE);

                    }
                });


            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomFragment frag = new BottomFragment();
                Bundle b = new Bundle();
                b.putString("details" , deta);
                b.putString("has" , ha);
                b.putString("file" , fil);
                frag.setArguments(b);
                frag.show(getSupportFragmentManager() , "Bottom Sheet");

            }
        });

        rat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("rating", "rating");
                final Dialog dialog = new Dialog(insititudedetails.this);
                dialog.setContentView(R.layout.rating_dialog);
                dialog.setCancelable(true);
                dialog.show();

                RatingBar ratin = dialog.findViewById(R.id.rate);
                Button sub = dialog.findViewById(R.id.submit);

                final ProgressBar bar = dialog.findViewById(R.id.propgress);

                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bar.setVisibility(View.VISIBLE);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constant.BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ServiceInterface cr = retrofit.create(ServiceInterface.class);

                        Call<RateBean> call = cr.rate(SharePreferenceUtils.getInstance().getString(Constant.USER_id), insid, ratt);
                        call.enqueue(new Callback<RateBean>() {
                            @Override
                            public void onResponse(Call<RateBean> call, Response<RateBean> response) {


                                if (Objects.equals(response.body().getStatus(), "1")) {

                                    Toast.makeText(insititudedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    start();
                                } else {

                                    Toast.makeText(insititudedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                                bar.setVisibility(View.GONE);
                                dialog.dismiss();

                            }

                            @Override
                            public void onFailure(Call<RateBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);

                            }
                        });


                    }
                });


            }
        });


    }

    public void start() {
        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<InsititutedetailsBean> call = cr.insi(insid, SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call.enqueue(new Callback<InsititutedetailsBean>() {
            @Override
            public void onResponse(Call<InsititutedetailsBean> call, Response<InsititutedetailsBean> response) {


                if (Objects.equals(response.body().getStatus(), "1")) {


                    fee.setText(response.body().getData().getFees());
                    estyrr.setText(response.body().getData().getEstYear());
                    centerr.setText(response.body().getData().getCentres());
                    airengg.setText(response.body().getData().getAirEngg());
                    airmed.setText(response.body().getData().getAirMed());
                    student.setText(response.body().getData().getStudents());
                    faculties.setText(response.body().getData().getFaculties());
                    name.setText(response.body().getData().getName());
                    faculties.setText(response.body().getData().getFaculties());
                    ratingBar.setRating(Float.parseFloat(response.body().getData().getRating()));
                    toolbar.setTitle(response.body().getData().getName());

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(response.body().getData().getImage() , imageView , options);




                } else {

                    Toast.makeText(insititudedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


                bar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<InsititutedetailsBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        start();

    }
}
