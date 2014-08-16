package com.rastating.droidbeard.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DroidBeard";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createTable(SQLiteDatabase db, String name, String[] fields) {
        String query = "CREATE TABLE " + name + " (_id INTEGER PRIMARY AUTOINCREMENT";
        for (String field : fields) {
            query += ", " + field;
        }
        query += ")";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
    }
}
