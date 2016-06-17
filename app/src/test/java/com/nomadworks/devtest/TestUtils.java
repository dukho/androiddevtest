package com.nomadworks.devtest;

/**
 * Created by choidukho on 17/06/2016.
 */
public class TestUtils {
    private static final String TAG = "[TEST]";
    private static final int API_TEST_TIMEOUT_SEC = 15;
    public static String getApiBaseUrl() {
        return "http://express-it.optusnet.com.au/";
    }

    public static int getApiTimeoutSec() {
        return API_TEST_TIMEOUT_SEC;
    }

    public static void debugLog(String message) {
        System.out.println(TAG + " " + message);
    }

    public static void debugLog(String subTag, String message) {
        debugLog(subTag + " " + message);
    }
}
