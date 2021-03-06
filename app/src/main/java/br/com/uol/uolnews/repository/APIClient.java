package br.com.uol.uolnews.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.uol.uolnews.BuildConfig;
import br.com.uol.uolnews.global.UolApplication;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anderson on 11/07/2018.
 */
@SuppressLint("LogNotTimber")
public class APIClient {

    private static final String TAG = "APIClient";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(UolApplication.apiUrl)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(getOkHttpClient())
                    .build();
        }

        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());

            try {
                return formatter.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        });

        return builder.create();
    }
}