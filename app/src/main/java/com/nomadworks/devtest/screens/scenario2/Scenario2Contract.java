package com.nomadworks.devtest.screens.scenario2;

import android.os.Bundle;

import com.nomadworks.devtest.model.PlaceInfo;

import java.util.List;

/**
 * Created by choidukho on 18/06/2016.
 */
public interface Scenario2Contract {
    interface ViewListener {
        void setWaitState(boolean wait);
        void setUiLock(boolean locked);
        void setInformation(PlaceInfo placeInfo);
        void showError(String error);
        void moveToLocationOnMap(PlaceInfo placeInfo);
    }

    interface Presenter {
        void requestPlaceInfoList(PlaceInfoCallback callback);

        void onLocationSelected(PlaceInfo placeInfo);
        void onNavigateRequested();

        //life cycle delegate
        void onResume();
        void onPause();
        void onSaveInstanceState(Bundle outState);
        void onRestoreInstanceState(Bundle savedInstanceState);
    }

    interface PlaceInfoCallback {
        void onPlaceInfoSuccess(List<PlaceInfo> placeList);
        void onPlaceError(String error);
    }

}
