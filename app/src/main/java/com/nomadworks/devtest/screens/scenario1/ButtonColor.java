package com.nomadworks.devtest.screens.scenario1;

/**
 * Created by choidukho on 17/06/2016.
 */
public enum ButtonColor {
    RED("RED", "#FF0000"),
    GREEN("GREEN", "#00FF00"),
    BLUE("BLUE", "#0000FF");

    String mName;
    String mColorCode;
    ButtonColor(String name, String colorCode) {
        this.mName = name;
        this.mColorCode = colorCode;
    }

    public String getName() {
        return this.mName;
    }

    public String getColorCode() {
        return this.mColorCode;
    }
}
