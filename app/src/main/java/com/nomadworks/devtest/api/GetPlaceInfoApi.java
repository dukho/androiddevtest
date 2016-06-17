package com.nomadworks.devtest.api;

import com.nomadworks.devtest.model.PlaceInfo;

import java.util.List;

/**
 * Created by choidukho on 17/06/2016.
 */
public interface GetPlaceInfoApi {
    interface PlaceCallback {
        void onPlaceInfo(List<PlaceInfo> placeList);
        void onPlaceError(String error);
    }

    //this callback is maintained as weak reference
    void requestPlace(PlaceCallback callback);
}
