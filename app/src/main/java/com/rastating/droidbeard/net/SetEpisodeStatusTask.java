package com.rastating.droidbeard.net;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SetEpisodeStatusTask extends SickbeardAsyncTask<String, Void, Boolean> {
    private int mTvDBId;
    private int mSeason;
    private int mEpisode;

    public SetEpisodeStatusTask(Context context, int tvdbid, int season, int episode) {
        super(context);

        mTvDBId = tvdbid;
        mSeason = season;
        mEpisode = episode;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        List<Pair<String, Object>> params = new ArrayList<Pair<String, Object>>();
        params.add(new Pair<String, Object>("tvdbid", mTvDBId));
        params.add(new Pair<String, Object>("season", mSeason));
        params.add(new Pair<String, Object>("episode", mEpisode));
        params.add(new Pair<String, Object>("status", strings[0]));
        params.add(new Pair<String, Object>("force", 1));

        try {
            return getJson("episode.setstatus", params).contains("success");
        }
        catch (Exception e) {
            return false;
        }
    }
}