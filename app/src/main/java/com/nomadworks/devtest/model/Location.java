package com.nomadworks.devtest.model;

import java.io.Serializable;

/**
 * Created by choidukho on 17/06/2016.
 */
public class Location implements Serializable{
    Double latitude;
    Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
