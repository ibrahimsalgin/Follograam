package com.developers.yeditepe.follograam.utils;

import android.graphics.drawable.Drawable;
import android.os.Message;
import android.widget.ImageView;

import com.developers.yeditepe.follograam.FollograamActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

public class Utils {
    public static String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }
}
