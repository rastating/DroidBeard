package com.rastating.droidbeard.net;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EpisodeSearchTask extends SickbeardAsyncTask<Void, Void, Boolean> {
    private int mTvDBId;
    private int mSeason;
    private int mEpisode;

    protected static BlockingQueue BLOCKING_QUEUE = new ArrayBlockingQueue(100);
    protected static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(10, 100, 5, TimeUnit.SECONDS, BLOCKING_QUEUE);

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

        String json = getJson("episode.search", params);
        if (json != null) {
            return json.contains("success");
        }
        else {
            return false;
        }
    }

    @Override
    public void start(Void... args) {
        super.start(EpisodeSearchTask.EXECUTOR, args);
    }
}