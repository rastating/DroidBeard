package com.rastating.droidbeard.net;

import android.content.Context;

public class ShutdownTask extends SickbeardAsyncTask<Void, Void, Boolean> {
    public ShutdownTask(Context context) {
        super(context);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            return getJson("sb.shutdown").contains("success");
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
