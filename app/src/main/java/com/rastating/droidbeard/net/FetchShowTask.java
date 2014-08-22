package com.rastating.droidbeard.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Pair;

import com.rastating.droidbeard.entities.TVShow;

import java.util.ArrayList;
import java.util.List;

public class FetchShowTask extends SickbeardAsyncTask<Integer, Void, TVShow> {

    public FetchShowTask(Context context) {
        super(context);
    }

    private Bitmap getBanner(int tvdbid) {
        ArrayList<Pair<String, Object>> params = new ArrayList<Pair<String, Object>>();
        params.add(new Pair<String, Object>("tvdbid", tvdbid));
        return getBitmap("show.getbanner", params);
    }

    @Override
    protected TVShow doInBackground(Integer... integers) {
        int tvdbid = integers[0];
        TVShow show = new TVShow();
        Bitmap banner = getBanner(tvdbid);
        show.setBanner(banner);
        return show;
    }

    @Override
    protected void onPostExecute(TVShow show) {
        List<ApiResponseListener<TVShow>> listeners = getResponseListeners();
        for (ApiResponseListener<TVShow> listener : listeners) {
            listener.onApiRequestFinished(show);
        }
    }
}
