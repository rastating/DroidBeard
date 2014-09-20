package com.rastating.droidbeard.net;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class EpisodeSearchTask extends SickbeardAsyncTask<Void, Void, Boolean> {
    private int mTvDBId;
    private int mSeason;
    private int mEpisode;

    public EpisodeSearchTask(Context context, int tvdbid, int season, int episode) {
        super(context);

        mTvDBId = tvdbid;
        mSeason = season;
        mEpisode = episode;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        List<Pair<String, Object>> params = new ArrayList<Pair<String, Object>>();
        params.add(new Pair<String, Object>("tvdbid", mTvDBId));
        params.add(new Pair<String, Object>("season", mSeason));
        params.add(new Pair<String, Object>("episode", mEpisode));
        return getJson("episode.search", params).contains("success");
    }
}
