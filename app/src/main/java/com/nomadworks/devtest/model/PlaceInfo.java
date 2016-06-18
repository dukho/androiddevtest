package com.nomadworks.devtest.model;

import java.io.Serializable;

/**
 * Created by choidukho on 17/06/2016.
 */
public class PlaceInfo implements Serializable {
    FromCentral fromcentral;
    Integer id;
    Location location;
    String name;

    public FromCentral getFromcentral() {
        return fromcentral;
    }

    public void setFromcentral(FromCentral fromcentral) {
        this.fromcentral = fromcentral;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
