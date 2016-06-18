package com.nomadworks.devtest.utils;

import android.util.Log;

import com.nomadworks.devtest.BuildConfig;

/**
 * Created by choidukho on 18/06/2016.
 */
public class Logging {
    private static final String TAG = "[DEV_TEST]";

    public static void log(String message) {
        if(BuildConfig.ENABLE_DEBUG_LOG) {
            android.util.Log.d(TAG, message);
        }
    }
}
