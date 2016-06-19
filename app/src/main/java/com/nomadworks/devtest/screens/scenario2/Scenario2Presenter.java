package com.nomadworks.devtest.screens.scenario2;

import android.os.Bundle;

import com.nomadworks.devtest.model.PlaceInfo;
import com.nomadworks.devtest.repository.DataRepository;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by choidukho on 18/06/2016.
 */
public class Scenario2Presenter implements Scenario2Contract.Presenter {
    private static final String KEY_PRESENTER_STATE = "key.presenter.state";

    Scenario2Contract.ViewListener mView;
    DataRepository mRepository;

    Senario2PresenterState mPresenterState = new Senario2PresenterState();

    DataRepository.PlaceListCallback mPlaceListCallback = new DataRepository.PlaceListCallback() {
        @Override
        public void onPlaceDataSuccess(List<PlaceInfo> placeList) {
            if(isViewPlaceInfoCallbackValid()) {
                mRefPlaceInfoCallback.get().onPlaceInfoSuccess(placeList);
            }

            mView.setUiLock(false);
            mView.setWaitState(false);
        }

        @Override
        public void onPlaceDataError(String error) {
            if(isViewPlaceInfoCallbackValid()) {
                mRefPlaceInfoCallback.get().onPlaceError(error);
            }

            mView.setUiLock(false);
            mView.setWaitState(false);
        }
    };

    private boolean isViewPlaceInfoCallbackValid() {
        return mRefPlaceInfoCallback != null && mRefPlaceInfoCallback.get() != null;
    }

    //null object for view
    Scenario2Contract.ViewListener dummyView = new Scenario2Contract.ViewListener() {
        @Override
        public void setWaitState(boolean wait) { }

        @Override
        public void setUiLock(boolean locked) { }

        @Override
        public void setInformation(PlaceInfo placeInfo) { }

        @Override
        public void showError(String error) { }

        @Override
        public void moveToLocationOnMap(PlaceInfo placeInfo) { }
    };

    WeakReference<Scenario2Contract.PlaceInfoCallback> mRefPlaceInfoCallback;

    public Scenario2Presenter(Scenario2Contract.ViewListener view, DataRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void onLocationSelected(PlaceInfo placeInfo) {
        mPresenterState.selectedPlace = placeInfo;
        mView.setInformation(mPresenterState.selectedPlace);
    }

    @Override
    public void onNavigateRequested() {
        if(mPresenterState.selectedPlace != null) {
            mView.moveToLocationOnMap(mPresenterState.selectedPlace);
        } else {
            //TODO
        }
    }

    @Override
    public void onPause() {
        mView = dummyView; //set null view to avoid potential NPE
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey(KEY_PRESENTER_STATE)) {
            Senario2PresenterState state = (Senario2PresenterState) savedInstanceState.getSerializable(KEY_PRESENTER_STATE);
            if(state != null) {
                mPresenterState = state;
                mView.setInformation(state.selectedPlace);
            }
        } else {
            mPresenterState = new Senario2PresenterState();
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_PRESENTER_STATE, mPresenterState);
    }

    @Override
    public void requestPlaceInfoList(Scenario2Contract.PlaceInfoCallback callback) {
        mView.setUiLock(true);
        mView.setWaitState(true);

        mRefPlaceInfoCallback = new WeakReference<Scenario2Contract.PlaceInfoCallback>(callback);
        mRepository.getPlaceList(mPlaceListCallback, false);
    }

    @Override
    public void setViewListener(Scenario2Contract.ViewListener viewListener) {
        mView = viewListener;
    }
}
