package com.rastating.droidbeard.entities;

public class TvDBResult {
    private String mName;
    private int mTvDbId;
    private String mFirstAired;

    public String getFirstAired() {
        return (mFirstAired == null || mFirstAired.equalsIgnoreCase("null")) ? "N/A" : mFirstAired;
    }

    public int getId() {
        return mTvDbId;
    }

    public String getName() {
        return mName;
    }

    public void setFirstAired(String value) {
        mFirstAired = value;
    }

    public void setId(int value) {
        mTvDbId = value;
    }

    public void setName(String value) {
        mName = value;
    }
}