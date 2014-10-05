package com.rastating.droidbeard.net;

import android.content.Context;
import android.os.SystemClock;

import javax.net.ssl.SSLHandshakeException;

public class RestartTask extends SickbeardAsyncTask<Void, Void, Boolean> {
    public RestartTask(Context context) {
        super(context);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if (getJson("sb.restart").contains("success")) {
                boolean restarted = false;
                SystemClock.sleep(10000);

                int attemptCount = 0;
                while (!restarted) {
                    if (attemptCount == 15) {
                        restarted = true;
                    }
                    else {
                        String json = getJson("sb.ping");
                        if (json != null && json.contains("Pong")) {
                            restarted = true;
                        }

                        Thread.sleep(2500);
                        attemptCount += 1;
                    }
                }

                return true;
            }
            else {
                return false;
            }
        }
        catch (SSLHandshakeException e) {
            // If an SSLHandShakeException is being thrown, it means we've restarted after the
            // HTTPS setting has been changed in Sick Beard and it is in fact back online.
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}