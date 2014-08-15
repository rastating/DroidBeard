package com.rastating.droidbeard;

public class NavigationAction {
    private int mActiveIconResourceId;
    private int mInactiveIconResourceId;
    private String mText;

    public NavigationAction(int activeIconResourceId, int inactiveIconResourceId, String text) {
        mActiveIconResourceId = activeIconResourceId;
        mInactiveIconResourceId = inactiveIconResourceId;
        mText = text;
    }

    public int getActiveIconResourceId() {
        return mActiveIconResourceId;
    }

    public int getInactiveIconResourceId() {
        return mInactiveIconResourceId;
    }

    public String getText() {
        return mText;
    }
}