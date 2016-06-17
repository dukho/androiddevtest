package com.nomadworks.devtest.screens.scenario1;

import android.os.Bundle;

import java.util.List;

/**
 * Created by choidukho on 17/06/2016.
 */
public interface Scenario1Contract {
    interface ViewListener {
        void showToastMessage(String message);
        void setText(String text);
        void setButtonAreaBackgroundColor(String colorCode);
        void setCurrentPage(int index);
    }

    interface Presenter {
        List<String> getScrollableItems();
        List<String> getPagerTitles();

        void setViewListener(ViewListener viewListener);

        void onScrollItemClicked(String item);
        void onPageClicked(String title);
        void onPageSelected(int index);
        void onButtonClicked(ButtonColor color);

        //life cycle delegate
        void onResume();
        void onPause();
        void onSaveInstanceState(Bundle outState);
        void onRestoreInstanceState(Bundle state);
    }
}
