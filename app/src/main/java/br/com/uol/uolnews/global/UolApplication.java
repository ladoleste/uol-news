package br.com.uol.uolnews.global;

import android.app.Application;

import br.com.uol.uolnews.BuildConfig;

public class UolApplication extends Application {

    public static String apiUrl = BuildConfig.API_URL;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
