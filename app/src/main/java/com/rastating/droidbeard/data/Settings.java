package com.rastating.droidbeard.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Settings {
    private static String TABLE_NAME = "settings";

    private DatabaseHelper mDatabaseHelper;
    private String mSickbeardUrl;
    private String mApiKey;

    public Settings(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
        if (!read()) {
            createInitialRecord();
        }
    }

    private boolean createInitialRecord() {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("sickbeard_url", "");
        cv.put("api_key", "");
        db.insert(TABLE_NAME, null, cv);

        return true;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getSickbeardUrl() {
        return mSickbeardUrl;
    }

    public boolean read() {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String[] columns = new String[] { "sickbeard_url", "api_key" };
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            mSickbeardUrl = cursor.getString(0);
            mApiKey = cursor.getString(0);
            return true;
        }
        else {
            return false;
        }
    }

    public void setApiKey(String value) {
        mApiKey = value;
    }

    public void setSickbeardUrl(String value) {
        mSickbeardUrl = value;
    }

    public boolean update() {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("sickbeard_url", mSickbeardUrl);
        cv.put("api_key", mApiKey);
        db.update(TABLE_NAME, cv, null, null);

        return true;
    }
}
