package com.rastating.droidbeard.entities;

public class UpcomingEpisode extends Episode {
    public enum UpcomingEpisodeStatus {
        PAST,
        CURRENT,
        FUTURE,
        DISTANT
    }

    private int mSeasonNumber;
    private String mShowName;
    private UpcomingEpisodeStatus mUpcomingStatus;

    public int getSeasonNumber() {
        return mSeasonNumber;
    }

    public String getShowName() {
        return mShowName;
    }

    public UpcomingEpisodeStatus getUpcomingStatus() {
        return mUpcomingStatus;
    }

    public void setSeasonNumber(int value) {
        mSeasonNumber = value;
    }

    public void setShowName(String value) {
        mShowName = value;
    }

    public void setUpcomingStatus(UpcomingEpisodeStatus value) {
        mUpcomingStatus = value;
    }
}