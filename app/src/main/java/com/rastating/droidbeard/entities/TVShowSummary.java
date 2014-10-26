/*
     DroidBeard - a free, open-source Android app for managing SickBeard
     Copyright (C) 2014 Robert Carr

     This program is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     This program is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program.  If not, see http://www.gnu.org/licenses/.
*/

package com.rastating.droidbeard.entities;

import java.util.Date;

public class TVShowSummary {
    private String mAirs;
    private String mName;
    private String mNetwork;
    private Date mNextAirDate;
    private boolean mPaused;
    private String mStatus;
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

    public boolean getPaused() {
        return mPaused;
    }

    public String getStatus() {
        return mStatus;
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

    public void setPaused(boolean value) {
        mPaused = value;
    }

    public void setStatus(String value) {
        mStatus = value;
    }

    public void setTvDbId(int value) {
        mTvDbId = value;
    }
}
