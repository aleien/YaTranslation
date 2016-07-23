package io.translation.yandex.yatranslation.model.json;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonWTF {
    public InitJsonWordList getLocalJson(Context context) {
        Gson gson = new Gson();
        try {
            InputStream is = context.getResources().getAssets().open("words.json");

            final char[] buffer = new char[1024];
            final StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(is, "UTF-8");
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
            return gson.fromJson(out.toString(), InitJsonWordList.class);
        } catch (Exception e) { // Ничего святого
            Log.e(getClass().getName(), "Problems with first JSON");
            return null;
        }
    }
}
