package com.rastating.droidbeard;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationActionAdapter extends ArrayAdapter<NavigationAction> {
    private Context mContext;
    private int mLayoutResourceId;
    private NavigationAction[] mObjects;
    private int mSelectedPosition;
    private LayoutInflater mInflater;
    private int mInactiveTextColor;
    private int mActiveTextColor;

    public NavigationActionAdapter(Context context, LayoutInflater inflater, int layoutResourceId, NavigationAction[] objects) {
        super(context, layoutResourceId, objects);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mObjects = objects;
        mSelectedPosition = 0;
        mInflater = inflater;
        mInactiveTextColor = Color.parseColor("#838383");
        mActiveTextColor = Color.parseColor("#333333");
    }

    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        NavigationActionHolder holder;

        if (row == null) {
            row = mInflater.inflate(mLayoutResourceId, parent, false);

            holder = new NavigationActionHolder();
            holder.icon = (ImageView) row.findViewById(R.id.icon);
            holder.text = (TextView) row.findViewById(R.id.text);

            row.setTag(holder);
        }
        else {
            holder = (NavigationActionHolder) row.getTag();
        }

        NavigationAction action = mObjects[position];
        holder.text.setText(action.getText());

        if (position == mSelectedPosition) {
            row.setBackgroundColor(mContext.getResources().getColor(R.color.navigation_list_item_selected));
            holder.text.setTextColor(mActiveTextColor);
            holder.icon.setImageResource(action.getActiveIconResourceId());
        }
        else {
            row.setBackgroundColor(Color.TRANSPARENT);
            holder.text.setTextColor(mInactiveTextColor);
            holder.icon.setImageResource(action.getInactiveIconResourceId());
        }

        return row;
    }

    private class NavigationActionHolder {
        public ImageView icon;
        public TextView text;
    }
}