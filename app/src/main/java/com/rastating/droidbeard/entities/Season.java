package com.rastating.droidbeard.entities;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private int mSeasonNumber;
    private List<Episode> mEpisodes;

    public Season() {
        mEpisodes = new ArrayList<Episode>();
    }

    public void addEpisode(Episode episode) {
        mEpisodes.add(episode);
    }

    public int getSeasonNumber() {
        return mSeasonNumber;
    }

    public String getTitle() {
        if (mSeasonNumber > 0) {
            return String.format("Season %d", mSeasonNumber);
        }
        else {
            return "Specials";
        }
    }

    public List<Episode> getEpisodes() {
        return mEpisodes;
    }

    public void setSeasonNumber(int value) {
        mSeasonNumber = value;
    }
}