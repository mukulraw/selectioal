package com.selectial.selectial;

import android.app.Application;

public class bean extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FontsOverride.setDefaultFont(this, "MONOSPACE", "calibri.ttf");

    }
}
