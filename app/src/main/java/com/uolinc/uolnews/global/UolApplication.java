package com.uolinc.uolnews.global;

import android.app.Application;

import com.uolinc.uolnews.BuildConfig;

public class UolApplication extends Application {

    public static String apiUrl = BuildConfig.API_URL;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
