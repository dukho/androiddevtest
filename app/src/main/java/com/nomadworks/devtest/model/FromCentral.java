package com.nomadworks.devtest.model;

import java.io.Serializable;

/**
 * Created by choidukho on 17/06/2016.
 */
public class FromCentral implements Serializable {
    String car;
    String train;

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }
}
