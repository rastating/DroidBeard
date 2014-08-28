package com.rastating.droidbeard.entities;

public class HistoricalEvent {
    private String mDate;
    private int mEpisodeNumber;
    private String mProvider;
    private String mQuality;
    private int mSeason;
    private String mShowName;
    private String mStatus;

    public String getDate() {
        return mDate;
    }

    public int getEpisodeNumber() {
        return mEpisodeNumber;
    }

    public String getProvider() {
        return mProvider;
    }

    public String getQuality() {
        return mQuality;
    }

    public int getSeason() {
        return mSeason;
    }

    public String getShowName() {
        return mShowName;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setDate(String value) {
        mDate = value;
    }

    public void setEpisodeNumber(int value) {
        mEpisodeNumber = value;
    }

    public void setProvider(String value) {
        mProvider = value;
    }

    public void setQuality(String value) {
        mQuality = value;
    }

    public void setSeason(int value) {
        mSeason = value;
    }

    public void setShowName(String value) {
        mShowName = value;
    }

    public void setStatus(String value) {
        mStatus = value;
    }
}