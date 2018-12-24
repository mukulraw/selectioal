package com.selectial.selectial;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.downloader.PRDownloader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class bean extends Application {
    private static Context context;
    private String TAG ="myApp";

    @Override
    public void onCreate() {
        super.onCreate();

        PRDownloader.initialize(getApplicationContext());

        FontsOverride.setDefaultFont(this, "MONOSPACE", "calibri.ttf");

        context = getApplicationContext();
        Log.e(TAG, "  myapp stater");

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }
    public static Context getContext(){
        return context;
    }
}
