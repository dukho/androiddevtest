package com.nomadworks.devtest.screens.scenario1;

import android.os.Bundle;

import com.nomadworks.devtest.utils.Logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by choidukho on 17/06/2016.
 */
public class Scenario1Presenter implements Scenario1Contract.Presenter {
    Scenario1Contract.ViewListener mView;

    private static final String KEY_PRESENTER_STATE = "key.presenter.state";

    PresenterState mPresenterState = new PresenterState();

    //for null view to avoid NPE
    private Scenario1Contract.ViewListener dummyView = new Scenario1Contract.ViewListener() {
        @Override
        public void showToastMessage(String message) { }

        @Override
        public void setText(String text) { }

        @Override
        public void setButtonAreaBackgroundColor(String colorCode) { }

        @Override
        public void setCurrentPage(int index) { }
    };

    public Scenario1Presenter(Scenario1Contract.ViewListener viewListener) {
        mView = viewListener;
    }

    @Override
    public List<String> getPagerTitles() {
        List<String> titleList = new ArrayList<>();
        titleList.add("Page #1");
        titleList.add("Page #2");
        titleList.add("Page #3");
        titleList.add("Page #4");

        return titleList;
    }

    @Override
    public List<String> getScrollableItems() {
        List<String> itemList = new ArrayList<>();
        itemList.add("Item #1");
        itemList.add("Item #2");
        itemList.add("Item #3");
        itemList.add("Item #4");
        itemList.add("Item #5");

        return itemList;
    }

    @Override
    public void onButtonClicked(ButtonColor color) {
        mPresenterState.buttonColor = color;
        mView.setButtonAreaBackgroundColor(color.getColorCode());
    }

    @Override
    public void onPageClicked(String title) {
        mView.showToastMessage(title);
    }

    @Override
    public void onPageSelected(int index) {
        mPresenterState.pageIndex = index;
    }

    @Override
    public void onPause() {
        mView = dummyView; //we can't touch view while it's not active
        Logging.log("SC#1 onPause()");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Logging.log("SC#1 onRestoreInstanceState()");
        if(savedInstanceState.containsKey(KEY_PRESENTER_STATE)) {
            mPresenterState
                    = (PresenterState)savedInstanceState.getSerializable(KEY_PRESENTER_STATE);
        }
    }

    @Override
    public void onResume() {
        Logging.log("SC#1 onResume()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Logging.log("SC#1 onSaveInstanceState()");
        outState.putSerializable(KEY_PRESENTER_STATE, mPresenterState);
    }

    @Override
    public void onScrollItemClicked(String item) {
        mPresenterState.itemInfo = item;
        mView.setText(item);
    }

    @Override
    public void setViewListener(Scenario1Contract.ViewListener viewListener) {
        mView = viewListener;
        Logging.log("SC#1 setViewListener()");
    }
}
