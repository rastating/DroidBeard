package com.rastating.droidbeard;

import java.util.Date;

public class TVShow {
    private String mAirs;
    private String mName;
    private String mNetwork;
    private Date mNextAirDate;

    public TVShow(String name) {
        mName = name;
        mNextAirDate = null;
    }

    public TVShow() {
        mName = "";
        mNextAirDate = null;
    }

    public String getAirs() {
        return mAirs;
    }

    public String getName() {
        return mName;
    }

    public String getNetwork() {
        return mNetwork;
    }

    public Date getNextAirDate() {
        return mNextAirDate;
    }

    public void setAirs(String value) {
        mAirs = value;
    }

    public void setNetwork(String value) {
        mNetwork = value;
    }

    public void setNextAirDate(Date value) {
        mNextAirDate = value;
    }
}
