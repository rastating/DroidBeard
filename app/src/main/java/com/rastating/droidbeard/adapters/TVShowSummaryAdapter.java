package com.rastating.droidbeard.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.TVShowSummary;

public class TVShowSummaryAdapter extends ArrayAdapter<TVShowSummary> {
    private Context mContext;
    private int mLayoutResourceId;
    private TVShowSummary[] mObjects;
    private LayoutInflater mInflater;

    public TVShowSummaryAdapter(Context context, LayoutInflater inflater, int layoutResourceId, TVShowSummary[] objects) {
        super(context, layoutResourceId, objects);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mObjects = objects;
        mInflater = inflater;
    }

    @Override
    public TVShowSummary getItem(int position) {
        return mObjects[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TVShowHolder holder;

        if (row == null) {
            row = mInflater.inflate(mLayoutResourceId, parent, false);
            holder = new TVShowHolder();
            holder.showName = (TextView) row.findViewById(R.id.show_name);
            holder.airs = (TextView) row.findViewById(R.id.airs);
            row.setTag(holder);
        }
        else {
            holder = (TVShowHolder) row.getTag();
        }

        if (position % 2 == 0) {
            row.setBackgroundResource(R.drawable.alternate_list_item_bg);
        }
        else {
            row.setBackgroundColor(Color.TRANSPARENT);
        }

        TVShowSummary show = mObjects[position];
        holder.showName.setText(show.getName());
        if (show.getNextAirDate() != null) {
            holder.airs.setText(String.format("Next episode on %tB %te, %tY on %s", show.getNextAirDate(), show.getNextAirDate(), show.getNextAirDate(), show.getNetwork()));
        }
        else {
            holder.airs.setText("No upcoming episodes scheduled");
        }

        return row;
    }

    private class TVShowHolder {
        public TextView showName;
        public TextView airs;
    }
}