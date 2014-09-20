package com.rastating.droidbeard.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.Episode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EpisodeItem implements View.OnClickListener {
    private Context mContext;
    private View mView;
    private TextView mEpisodeNumber;
    private TextView mName;
    private TextView mAirdate;
    private int mSeasonNumber;
    private EpisodeItemClickListener mItemClickListener;

    public EpisodeItem(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.episode_item, null);
        mEpisodeNumber = (TextView) mView.findViewById(R.id.episode_number);
        mName = (TextView) mView.findViewById(R.id.name);
        mAirdate = (TextView) mView.findViewById(R.id.airdate);

        mView.setOnClickListener(this);
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

    public void setStatus(String value) {
        if (value.equalsIgnoreCase("skipped")) {
            setStatus(Episode.EpisodeStatus.SKIPPED);
        }
        else if (value.equalsIgnoreCase("unaired")) {
            setStatus(Episode.EpisodeStatus.UNAIRED);
        }
        else if (value.equalsIgnoreCase("wanted")) {
            setStatus(Episode.EpisodeStatus.WANTED);
        }
        else if (value.equalsIgnoreCase("downloaded")) {
            setStatus(Episode.EpisodeStatus.DOWNLOADED);
        }
        else if (value.equalsIgnoreCase("snatched")) {
            setStatus(Episode.EpisodeStatus.SNATCHED);
        }
        else if (value.equalsIgnoreCase("ignored")) {
            setStatus(Episode.EpisodeStatus.IGNORED);
        }
        else if (value.equalsIgnoreCase("archived")) {
            setStatus(Episode.EpisodeStatus.ARCHIVED);
        }
        else {
            setStatus(Episode.EpisodeStatus.IGNORED);
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

            case ARCHIVED:
            case DOWNLOADED:
                color = resources.getColor(R.color.downloaded_episode_background);
                break;
        }

        mEpisodeNumber.setBackgroundColor(color);
        mName.setBackgroundColor(color);
        mAirdate.setBackgroundColor(color);
    }

    public void setSeasonNumber(int value) {
        mSeasonNumber = value;
    }

    public void setOnItemClickListener(EpisodeItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(this, mSeasonNumber, Integer.valueOf(mEpisodeNumber.getText().toString()), mName.getText().toString());
        }
    }
}