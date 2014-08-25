package com.rastating.droidbeard.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.Episode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EpisodeItem {
    private Context mContext;
    private View mView;
    private TextView mEpisodeNumber;
    private TextView mName;
    private TextView mAirdate;

    public EpisodeItem(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.episode_item, null);
        mEpisodeNumber = (TextView) mView.findViewById(R.id.episode_number);
        mName = (TextView) mView.findViewById(R.id.name);
        mAirdate = (TextView) mView.findViewById(R.id.airdate);
    }

    public void addToTable(TableLayout table) {
        table.addView(mView);
    }

    public void setEpisodeNumber(int value) {
        mEpisodeNumber.setText(String.valueOf(value));
    }

    public void setName(String value) {
        mName.setText(value);
    }

    public void setAirdate(Date value) {
        if (value == null) {
            mAirdate.setText("Never");
        }
        else {
            mAirdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(value));
        }
    }

    public void setStatus(Episode.EpisodeStatus value) {
        Resources resources = mContext.getResources();
        int color = resources.getColor(R.color.skipped_episode_background);
        switch (value) {
            case SNATCHED:
                color = resources.getColor(R.color.snatched_episode_background);
                break;

            case WANTED:
                color = resources.getColor(R.color.wanted_episode_background);
                break;

            case UNAIRED:
                color = resources.getColor(R.color.unaired_episode_background);
                break;

            case IGNORED:
            case SKIPPED:
                color = resources.getColor(R.color.skipped_episode_background);
                break;

            case DOWNLOADED:
                color = resources.getColor(R.color.downloaded_episode_background);
                break;
        }

        mEpisodeNumber.setBackgroundColor(color);
        mName.setBackgroundColor(color);
        mAirdate.setBackgroundColor(color);
    }
}