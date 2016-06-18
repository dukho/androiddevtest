package com.nomadworks.devtest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

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

    public static String getStringFromResourceFile(String resourceFileName) throws IOException {
        URL url = Resources.getResource(resourceFileName);
        return Resources.toString(url, Charsets.UTF_8);
    }
}
