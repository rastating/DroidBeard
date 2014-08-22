package com.rastating.droidbeard.entities;

import java.util.Date;

public class TVShowSummary {
    private String mAirs;
    private String mName;
    private String mNetwork;
    private Date mNextAirDate;
    private int mTvDbId;

    public TVShowSummary(String name) {
        mName = name;
        mNextAirDate = null;
    }

    public TVShowSummary() {
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

    public int getTvDbId() {
        return mTvDbId;
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

    public void setTvDbId(int value) {
        mTvDbId = value;
    }
}
