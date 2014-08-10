package com.rastating.droidbeard;

public class NavigationAction {
    private int mIconResourceId;
    private String mText;

    public NavigationAction(int iconResourceId, String text) {
        mIconResourceId = iconResourceId;
        mText = text;
    }

    public int getIconResourceId() {
        return mIconResourceId;
    }

    public String getText() {
        return mText;
    }
}