package com.rastating.droidbeard.entities;

import android.graphics.Bitmap;

import java.util.Date;

public class TVShow {
    private boolean mAirByDate;
    private String mAirs;
    private Bitmap mBanner;
    private boolean mFlattenFolders;
    private String[] mGenres;
    private Language mLanguage;
    private String mLocation;
    private String mNetwork;
    private Date mNextAirdate;
    private boolean mPaused;
    private String mQuality;
    private String mShowName;
    private String mStatus;

    public boolean getAirByDate() {
        return mAirByDate;
    }

    public String getAirs() {
        return mAirs;
    }

    public Bitmap getBanner() {
        return mBanner;
    }

    public boolean getFlattenFolders() {
        return mFlattenFolders;
    }

    public String[] getGenres() {
        return mGenres;
    }

    public Language getLanguage() {
        return mLanguage;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getNetwork() {
        return mNetwork;
    }

    public Date getNextAirdate() {
        return mNextAirdate;
    }

    public boolean getPaused() {
        return mPaused;
    }

    public String getQuality() {
        return mQuality;
    }

    public String getShowName() {
        return mShowName;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setAirByDate(boolean value) {
        mAirByDate = value;
    }

    public void setAirs(String value) {
        mAirs = value;
    }

    public void setBanner(Bitmap value) {
        mBanner = value;
    }

    public void setFlattenFolders(boolean value) {
        mFlattenFolders = value;
    }

    public void setGenres(String[] value) {
        mGenres = value;
    }

    public void setLanguage(Language value) {
        mLanguage = value;
    }

    public void setLocation(String value) {
        mLocation = value;
    }

    public void setNetwork(String value) {
        mNetwork = value;
    }

    public void setNextAirdate(Date value) {
        mNextAirdate = value;
    }

    public void setPaused(boolean value) {
        mPaused = value;
    }

    public void setQuality(String value) {
        mQuality = value;
    }

    public void setShowName(String value) {
        mShowName = value;
    }

    public void setStatus(String value) {
        mStatus = value;
    }
}