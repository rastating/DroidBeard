package com.rastating.droidbeard.net;

import android.content.Context;
import android.os.SystemClock;

public class RestartTask extends SickbeardAsyncTask<Void, Void, Boolean> {
    public RestartTask(Context context) {
        super(context);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if (getJson("sb.restart").contains("success")) {
                boolean restarted = false;
                SystemClock.sleep(20000);

                while (!restarted) {
                    String json = getJson("sb.ping");
                    if (json != null && json.contains("Pong")) {
                        restarted = true;
                    }
                }

                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}