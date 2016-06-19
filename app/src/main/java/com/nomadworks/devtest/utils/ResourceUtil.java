package com.nomadworks.devtest.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by choidukho on 19/06/2016.
 */
public class ResourceUtil {
    public static String getStringFromAssetFile(Context context, String path) throws IOException{
        AssetManager assetManager = context.getAssets();
        StringBuilder buff =new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStream is = assetManager.open(path);
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;

            while ((str = reader.readLine()) != null) {
                buff.append(str);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        } finally {
            reader.close();
        }

        return buff.toString();
    }
}
