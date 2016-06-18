package com.nomadworks.devtest.repository;

import com.nomadworks.devtest.api.GetPlaceInfoApi;
import com.nomadworks.devtest.model.PlaceInfo;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by choidukho on 17/06/2016.
 */
public class DataRepositoryImpl implements DataRepository {
    WeakReference<PlaceListCallback> mRefCallback;
    List<PlaceInfo> mCachedPlace;   //simple cache
    GetPlaceInfoApi mPlaceApi;      //api service - constructor injected

    //callback for place api call
    GetPlaceInfoApi.PlaceCallback mPlaceCallback
            = new GetPlaceInfoApi.PlaceCallback() {
        @Override
        public void onPlaceInfo(List<PlaceInfo> placeList) {
            mCachedPlace = placeList; //update cache
            if(isCallbackValid()) {
                mRefCallback.get().onPlaceDataSuccess(mCachedPlace);
            }
        }

        @Override
        public void onPlaceError(String error) {
            mCachedPlace = null; //nullify cached info
            if(isCallbackValid()) {
                mRefCallback.get().onPlaceDataError(error);
            }
        }
    };


    public DataRepositoryImpl(GetPlaceInfoApi placeInfoApi) {
        mPlaceApi = placeInfoApi;
    }

    @Override
    public Void getPlaceList(PlaceListCallback callback, boolean needRefresh) {
        mRefCallback = new WeakReference<PlaceListCallback>(callback);

        if(mCachedPlace == null || needRefresh) {
            mPlaceApi.requestPlace(mPlaceCallback);
        }
        else {
            if(isCallbackValid()) {
                mRefCallback.get().onPlaceDataSuccess(mCachedPlace);
            }
        }

        return (Void)null;
    }

    private boolean isCallbackValid() {
        return mRefCallback != null && mRefCallback.get() != null;
    }
}
