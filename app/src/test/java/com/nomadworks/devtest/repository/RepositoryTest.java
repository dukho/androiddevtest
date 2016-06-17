package com.nomadworks.devtest.repository;

import com.nomadworks.devtest.TestUtils;
import com.nomadworks.devtest.api.GetPlaceInfoApi;
import com.nomadworks.devtest.api.retrofitimpl.GetPlaceInfoApiRetrofitImpl;
import com.nomadworks.devtest.model.PlaceInfo;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by choidukho on 17/06/2016.
 */
public class RepositoryTest {
    private static final String KEY_RESULT = "key.result";

    @Test
    public void testRepository() throws InterruptedException {
        GetPlaceInfoApi placeInfoApi = new GetPlaceInfoApiRetrofitImpl(TestUtils.getApiBaseUrl());
        DataRepository repository = new DataRepositoryImpl(placeInfoApi);

        final HashMap<String, List<PlaceInfo>> resultMap = new HashMap<>();
        final CountDownLatch latch = new CountDownLatch(1);

        DataRepository.PlaceListCallback placeListCallback = new DataRepository.PlaceListCallback() {
            @Override
            public void onPlaceDataSuccess(List<PlaceInfo> placeList) {
                resultMap.put(KEY_RESULT, placeList);
                latch.countDown();
            }

            @Override
            public void onPlaceDataError(String error) {
                latch.countDown();
            }
        };

        repository.getPlaceList(placeListCallback, true);
        latch.await(TestUtils.getApiTimeoutSec(), TimeUnit.SECONDS);

        List<PlaceInfo> result = resultMap.get(KEY_RESULT);

        assertNotNull(result);
    }
}
