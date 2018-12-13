package com.selectial.selectial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    ImageButton toggle;
    TextView profile , myTest , orders , compare;
    BottomNavigationView bottom;
    TextView toolbar;
    TextView settings1;
    ImageButton settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);
        toggle = findViewById(R.id.imageButton4);
        profile = findViewById(R.id.textView58);
        orders = findViewById(R.id.textView61);
        bottom = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.textView27);
        myTest = findViewById(R.id.textView59);
        settings1 = findViewById(R.id.textView62);
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
                toolbar.setText("My Test");

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

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.test:
                        toolbar.setText("My Test");

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
                        toolbar.setText("College Info");

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
                        toolbar.setText("Home");

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

        toolbar.setText("Home");

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

    void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

}
