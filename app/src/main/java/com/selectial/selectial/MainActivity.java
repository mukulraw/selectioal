package com.selectial.selectial;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.selectial.selectial.GetProfilePOJO.GetProfileBean;
import com.selectial.selectial.GetStreamPOJO.GetStreamBean;
import com.selectial.selectial.classesPOJO.classesBean;
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

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;

    ImageButton toggle;

    TextView profile, myTest, orders, compare;

    BottomNavigationView bottom;

    TextView edit, name;

    TextView toolbar;

    TextView settings1;

    ImageButton settings;

    RoundedImageView imageView;

    TextView version;

    //TextView change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);

        toggle = findViewById(R.id.imageButton4);
        version = findViewById(R.id.version);

        profile = findViewById(R.id.textView58);

        orders = findViewById(R.id.textView61);

        bottom = findViewById(R.id.bottomNavigationView);

        toolbar = findViewById(R.id.textView27);

        myTest = findViewById(R.id.textView59);

        settings1 = findViewById(R.id.textView62);
        edit = findViewById(R.id.textView56);
        imageView = findViewById(R.id.imageView1);
        name = findViewById(R.id.textView55);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, EditProfile.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        settings = findViewById(R.id.imageButton6);

        compare = findViewById(R.id.textView24);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        myTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toolbar.setText("My Test");

                bottom.setSelectedItemId(R.id.test);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                MyTest test = new MyTest();
                ft.replace(R.id.replace, test);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.class_dialog_layout);
                dialog.show();

                final Spinner cls = dialog.findViewById(R.id.spinner2);
                final Spinner suu = dialog.findViewById(R.id.spinner3);
                Button sub = dialog.findViewById(R.id.button14);
                final ProgressBar bar = dialog.findViewById(R.id.progressBar13);


                final List<String> className = new ArrayList<>();
                final List<String> classId = new ArrayList<>();
                final String[] selClass = new String[1];
                selClass[0] = "";
                final List<String> subClassName = new ArrayList<>();
                final List<String> subClassId = new ArrayList<>();
                final String[] selSubClass = new String[1];
                selSubClass[0] = "";


                bar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final ServiceInterface serviceInterface = retrofit.create(ServiceInterface.class);


                Call<classesBean> call = serviceInterface.getClasses();

                call.enqueue(new Callback<classesBean>() {
                    @Override
                    public void onResponse(Call<classesBean> call, Response<classesBean> response) {


                        className.clear();
                        classId.clear();

                        className.add("Select Class");


                        for (int i = 0; i < response.body().getData().size(); i++) {
                            className.add(response.body().getData().get(i).getName());
                            classId.add(response.body().getData().get(i).getId());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                R.layout.spinner_item, className);

                        cls.setAdapter(adapter);

                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<classesBean> call, Throwable t) {
                        bar.setVisibility(View.GONE);
                    }
                });


                cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position > 0) {
                            selClass[0] = classId.get(position - 1);

                            bar.setVisibility(View.VISIBLE);

                            Call<GetStreamBean> call = serviceInterface.steam(selClass[0]);

                            Log.d("clsid", SharePreferenceUtils.getInstance().getString(Constant.USER_class_id));

                            call.enqueue(new Callback<GetStreamBean>() {
                                @Override
                                public void onResponse(Call<GetStreamBean> call, Response<GetStreamBean> response) {

                                    if (Objects.equals(response.body().getStatus(), "1")) {



                                        subClassName.clear();
                                        subClassId.clear();

                                        subClassName.add("Select Sub Class");

                                        try {
                                            for (int i = 0; i < response.body().getData().size(); i++) {
                                                subClassName.add(response.body().getData().get(i).getName());
                                                subClassId.add(response.body().getData().get(i).getId());
                                            }
                                        }catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }



                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                                R.layout.spinner_item, subClassName);

                                        suu.setAdapter(adapter);


                                        //Toast.makeText(Interest.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    bar.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<GetStreamBean> call, Throwable t) {

                                    bar.setVisibility(View.GONE);
                                }
                            });

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                suu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position > 0) {
                            selSubClass[0] = subClassId.get(position - 1);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (selClass[0].length() > 0 && selSubClass[0].length() > 0)
                        {
                            toolbar.setText(cls.getSelectedItem().toString() + " | " + suu.getSelectedItem().toString());

                            SharePreferenceUtils.getInstance().saveString(Constant.USER_class_id , selClass[0]);
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_id , selSubClass[0]);

                            setFrags();

                            dialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Please choose all fields", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.test:
                        //toolbar.setText("My Test");

                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        MyTest test = new MyTest();
                        ft.replace(R.id.replace, test);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        //ft.addToBackStack(null);
                        ft.commit();
                        drawer.closeDrawer(GravityCompat.START);

                        return true;
                    case R.id.college:
                        //toolbar.setText("College Info");

                        FragmentManager fm1 = getSupportFragmentManager();
                        FragmentTransaction ft1 = fm1.beginTransaction();
                        CollegeInfo test1 = new CollegeInfo();
                        ft1.replace(R.id.replace, test1);
                        ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        //ft.addToBackStack(null);
                        ft1.commit();
                        drawer.closeDrawer(GravityCompat.START);

                        return true;

                    case R.id.home:
                        //toolbar.setText("Home");

                        FragmentManager fm2 = getSupportFragmentManager();
                        FragmentTransaction ft2 = fm2.beginTransaction();
                        Home home = new Home();
                        ft2.replace(R.id.replace, home);
                        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        //ft.addToBackStack(null);
                        ft2.commit();
                        drawer.closeDrawer(GravityCompat.START);

                        return true;

                }

                return true;
            }
        });

        settings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Orders.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Compare.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);


            }
        });


        //bar.setVisibility(View.GONE);


        //toolbar.setText("Home");


        try {
            String versionName = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;


            version.setText("Version " + versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ServiceInterface cr = retrofit.create(ServiceInterface.class);

        Call<GetProfileBean> call = cr.profilee(SharePreferenceUtils.getInstance().getString(Constant.USER_id));
        call.enqueue(new Callback<GetProfileBean>() {
            @Override
            public void onResponse(Call<GetProfileBean> call, Response<GetProfileBean> response) {

                if (Objects.equals(response.body().getStatus(), "1")) {

                    name.setText(response.body().getData().getName());

                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(response.body().getData().getImage(), imageView, options);

                } else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                //bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetProfileBean> call, Throwable t) {


                // bar.setVisibility(View.GONE);
            }
        });


        Call<classesBean> call1 = cr.getClasses();

        call1.enqueue(new Callback<classesBean>() {
            @Override
            public void onResponse(Call<classesBean> call, final Response<classesBean> response) {


                final String ccid = response.body().getData().get(0).getId();

                Call<GetStreamBean> call2 = cr.steam(ccid);

                call2.enqueue(new Callback<GetStreamBean>() {
                    @Override
                    public void onResponse(Call<GetStreamBean> call, Response<GetStreamBean> response2) {

                        if (Objects.equals(response2.body().getStatus(), "1")) {

                            toolbar.setText(response.body().getData().get(0).getName() + " | " + response2.body().getData().get(0).getName());

                            SharePreferenceUtils.getInstance().saveString(Constant.USER_class_id , ccid);
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_id , response2.body().getData().get(0).getId());

                            setFrags();

                        }

                        // bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<GetStreamBean> call, Throwable t) {

                        //bar.setVisibility(View.GONE);
                    }
                });


            }

            @Override
            public void onFailure(Call<classesBean> call, Throwable t) {
                //bar.setVisibility(View.GONE);
            }
        });



    }

    void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    void setFrags()
    {
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        Home home = new Home();
        ft2.replace(R.id.replace, home);
        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //ft.addToBackStack(null);
        ft2.commit();
        drawer.closeDrawer(GravityCompat.START);

        bottom.setSelectedItemId(R.id.home);

    }

}
