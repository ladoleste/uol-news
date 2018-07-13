package com.uolinc.uolnews.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static String readFile(String fileName) {
        StringBuffer datax = new StringBuffer("");
        try {

            ClassLoader classLoader = Util.class.getClassLoader();
            URL resource = classLoader.getResource(fileName);
            File file = new File(resource.getPath());

            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader buffreader = new BufferedReader(isr);

            String readString = buffreader.readLine();
            while (readString != null) {
                datax.append(readString);
                readString = buffreader.readLine();
            }

            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return datax.toString();
    }

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());

            try {
                return formatter.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ParseException e) {
                return null;
            }

        });

        return builder.create();
    }
}
