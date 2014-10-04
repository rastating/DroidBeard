package com.rastating.droidbeard;

import android.content.Context;

public class Application extends android.app.Application{
    private static Application mInstance;

    public static Application getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        mInstance = this;
        super.onCreate();
    }
}