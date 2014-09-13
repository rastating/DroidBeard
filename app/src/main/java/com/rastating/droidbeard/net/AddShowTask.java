package com.rastating.droidbeard.net;

import android.content.Context;

public class AddShowTask extends SickbeardAsyncTask<Integer, Void, Boolean> {
    public AddShowTask(Context context) {
        super(context);
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        return getJson("show.addnew", "tvdbid", String.valueOf(integers[0])).contains("success");
    }
}