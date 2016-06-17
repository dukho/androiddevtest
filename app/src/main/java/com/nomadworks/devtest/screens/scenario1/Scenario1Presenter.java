package com.nomadworks.devtest.screens.scenario1;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choidukho on 17/06/2016.
 */
public class Scenario1Presenter implements Scenario1Contract.Presenter {
    Scenario1Contract.ViewListener mView;

    private int mCurrentPageIndex = 0;
    private ButtonColor currentColor = ButtonColor.RED; //default color

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
        mView.setButtonAreaBackgroundColor(color.getColorCode());
    }

    @Override
    public void onPageClicked(String title) {
        mView.showToastMessage(title);
    }

    @Override
    public void onPageSelected(int index) {
        mCurrentPageIndex = index;
    }

    @Override
    public void onPause() {
        mView = dummyView; //we can't touch view while it's not active
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        //TODO
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onScrollItemClicked(String item) {
        mView.setText(item);
    }

    @Override
    public void setViewListener(Scenario1Contract.ViewListener viewListener) {
        mView = viewListener;
    }
}
