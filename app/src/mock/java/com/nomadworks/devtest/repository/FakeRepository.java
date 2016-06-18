package com.nomadworks.devtest.repository;

import android.content.Context;

/**
 * Created by choidukho on 18/06/2016.
 */
public class FakeRepository implements DataRepository {
    private Context mContext;

    public FakeRepository(Context context) {
        mContext = context;
    }

    @Override
    public Void getPlaceList(PlaceListCallback callback, boolean needRefresh) {

        return (Void)null;
    }
}
