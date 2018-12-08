package com.selectial.selectial;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class bean extends Application {
    private static Context context;
    private String TAG ="myApp";

    @Override
    public void onCreate() {
        super.onCreate();

        FontsOverride.setDefaultFont(this, "MONOSPACE", "calibri.ttf");

        context = getApplicationContext();
        Log.e(TAG, "  myapp stater");
    }
    public static Context getContext(){
        return context;
    }
}
