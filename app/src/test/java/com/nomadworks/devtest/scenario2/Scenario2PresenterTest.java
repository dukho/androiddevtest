package com.nomadworks.devtest.scenario2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nomadworks.devtest.TestUtils;
import com.nomadworks.devtest.model.PlaceInfo;
import com.nomadworks.devtest.repository.DataRepository;
import com.nomadworks.devtest.screens.scenario2.Scenario2Contract;
import com.nomadworks.devtest.screens.scenario2.Scenario2Presenter;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by choidukho on 18/06/2016.
 */
public class Scenario2PresenterTest {
    Scenario2Contract.ViewListener mView;
    Scenario2Contract.Presenter mPresenter;
    DataRepository mRepository;
    List<PlaceInfo> placeInfoList;

    @Before
    public void setup() throws Exception {
        String jsonString = TestUtils.getStringFromResourceFile("fixtures/testlocation.json");

        Type dataType = new TypeToken<List<PlaceInfo>>() {
        }.getType();
        Gson gson = new Gson();
        placeInfoList = gson.fromJson(jsonString, dataType);

        mView = mock(Scenario2Contract.ViewListener.class);
        mRepository = mock(DataRepository.class);

        when(mRepository.getPlaceList(any(DataRepository.PlaceListCallback.class), anyBoolean())).
                thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        DataRepository.PlaceListCallback callback =
                                (DataRepository.PlaceListCallback) invocation.getArguments()[0];

                        callback.onPlaceDataSuccess(placeInfoList);

                        return null;
                    }
                });

        assertNotNull(placeInfoList);

        mPresenter = new Scenario2Presenter(mView, mRepository);

        assertNotNull(mPresenter);
    }

    @Test
    public void getPlaceListTest() throws Exception {
        final List<PlaceInfo> result = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(1);
        mRepository.getPlaceList(new DataRepository.PlaceListCallback() {
            @Override
            public void onPlaceDataSuccess(List<PlaceInfo> placeList) {
                result.addAll(placeList);
                latch.countDown();
            }

            @Override
            public void onPlaceDataError(String error) {
                latch.countDown();
            }
        }, false);

        latch.await(TestUtils.getApiTimeoutSec(), TimeUnit.SECONDS);

        TestUtils.debugLog("Got callback: " + result.size());

        assertThat(result.size() > 0, CoreMatchers.is(true));
    }

    @Test
    public void testSelectLocation() {
        PlaceInfo place = placeInfoList.get(1);
        mPresenter.setViewListener(mView);
        mPresenter.onLocationSelected(place);

        verify(mView).setInformation(place);
    }

    @Test
    public void testNavigationRequest() {
        PlaceInfo place = placeInfoList.get(1);
        mPresenter.setViewListener(mView);
        mPresenter.onLocationSelected(place);
        mPresenter.onNavigateRequested();

        verify(mView).moveToLocationOnMap(place);
    }
}