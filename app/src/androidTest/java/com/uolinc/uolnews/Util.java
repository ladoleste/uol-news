package com.uolinc.uolnews;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
    public static String readFileFromAssets(Context cx, String fileName, int indice) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream stream = cx.getAssets().open(fileName);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            while (true) {
                line = bReader.readLine();
                if (line == null) {
                    break;
                }
                builder.append(line);
            }
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
        }

        return builder.toString().substring(indice);
    }
}
