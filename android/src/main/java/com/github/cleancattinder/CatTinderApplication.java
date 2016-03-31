package com.github.cleancattinder;

import android.app.Application;

import timber.log.Timber;

public class CatTinderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Hook up some logging
        Timber.plant(new Timber.DebugTree());
    }
}
