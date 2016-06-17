package com.nomadworks.devtest.repository;

import com.nomadworks.devtest.model.PlaceInfo;

import java.util.List;

/**
 * Created by choidukho on 17/06/2016.
 */
public interface DataRepository {
    interface PlaceListCallback {
        void onPlaceDataSuccess(List<PlaceInfo> placeList);
        void onPlaceDataError(String error);
    }

    /**
     * Request place list.
     * @param callback: callback to get data. Caution: this callback is maintained as weak reference
     * @param needRefresh: cache can be used if false, refresh otherwise
     */
    void getPlaceList(PlaceListCallback callback, boolean needRefresh);
}
