package com.rastating.droidbeard.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.Episode;

public class SeasonTable extends LinearLayout {
    private Context mContext;
    private String mTitle;
    private TableLayout mTable;

    public SeasonTable(Context context) {
        super(context);

        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.season_table, this, true);
        mTable = (TableLayout) this.findViewById(R.id.table);
    }

    public EpisodeItem addEpisode(Episode episode) {
        EpisodeItem item = new EpisodeItem(mContext);
        item.setEpisodeNumber(episode.getEpisodeNumber());
        item.setName(episode.getName());
        item.setAirdate(episode.getAirdate());
        item.setStatus(episode.getStatus());
        item.setSeasonNumber(episode.getSeasonNumber());
        item.addToTable(mTable);

        return item;
    }

    public void setTitle(String value) {
        mTitle = value;
        TextView titleView = (TextView) this.findViewById(R.id.season_title);
        titleView.setText(value);
    }
}