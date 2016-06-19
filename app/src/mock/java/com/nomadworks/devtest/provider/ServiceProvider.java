package com.nomadworks.devtest.provider;

import android.content.Context;

import com.nomadworks.devtest.repository.DataRepository;
import com.nomadworks.devtest.repository.FakeRepository;

/**
 * Created by choidukho on 19/06/2016.
 */
public class ServiceProvider {
    public static DataRepository provideDataRepository(Context context) {
        return new FakeRepository(context);
    }
}
