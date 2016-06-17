package com.nomadworks.devtest.api;

import com.nomadworks.devtest.TestUtils;
import com.nomadworks.devtest.api.retrofitimpl.GetPlaceInfoApiRetrofitImpl;
import com.nomadworks.devtest.data.PlaceInfo;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by choidukho on 17/06/2016.
 */
public class GetPlaceTest {
    private static final String KEY_RESULT = "key.result";
    @Test
    public void testGetPlace() throws InterruptedException {
        GetPlaceInfoApi api = new GetPlaceInfoApiRetrofitImpl(TestUtils.getApiBaseUrl());
        final HashMap<String, List<PlaceInfo>> resultMap = new HashMap<>();
        final CountDownLatch latch = new CountDownLatch(1);
        GetPlaceInfoApi.PlaceCallback callback = new GetPlaceInfoApi.PlaceCallback() {
            @Override
            public void onPlaceInfo(List<PlaceInfo> placeList) {
                resultMap.put(KEY_RESULT, placeList);
                latch.countDown();
            }

            @Override
            public void onPlaceError(String error) {
                latch.countDown();
            }
        };

        api.requestPlace(callback);
        latch.await(TestUtils.getApiTimeoutSec(), TimeUnit.SECONDS);

        List<PlaceInfo> placeInfoList = resultMap.get(KEY_RESULT);

        assertNotNull(placeInfoList);
        assertThat(placeInfoList.size() > 0, is(true));
        TestUtils.debugLog("entry: " + placeInfoList.size());
    }
}
