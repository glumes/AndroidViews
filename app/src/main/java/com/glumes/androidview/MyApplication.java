package com.glumes.androidview;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/12.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
