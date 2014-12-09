/*
     DroidBeard - a free, open-source Android app for managing SickBeard
     Copyright (C) 2014 Robert Carr

     This program is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     This program is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program.  If not, see http://www.gnu.org/licenses/.
*/

package com.rastating.droidbeard.fragments;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.ui.CrossFader;
import com.rastating.droidbeard.ui.LoadingAnimation;

public abstract class ListViewFragment extends DroidbeardFragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private View mErrorContainer;
    private ImageView mLoadingImage;
    private TextView mErrorMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_view, container, false);
        mListView = (ListView) root.findViewById(R.id.list_view);
        mErrorContainer = root.findViewById(R.id.error_container);
        mLoadingImage = (ImageView) root.findViewById(R.id.loading);
        mErrorMessage = (TextView) root.findViewById(R.id.error_message);
        return root;
    }

    protected void setAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    protected void setBackgroundColor(int color) {
        mListView.setBackgroundColor(color);
    }

    protected void setChoiceMode(int choiceMode) {
        mListView.setChoiceMode(choiceMode);
    }

    protected void setDivider(int resId, int height) {
        mListView.setDivider(new ColorDrawable(getResources().getColor(resId)));
        mListView.setDividerHeight(height);
    }

    protected void setListSelector(int resId) {
        mListView.setSelector(resId);
    }

    protected void setListSelector(Drawable selector) {
        mListView.setSelector(selector);
    }

    protected void showError(String message, Exception e) {
        mErrorMessage.setText(message);
        mErrorContainer.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        mLoadingImage.clearAnimation();
        mLoadingImage.setVisibility(View.GONE);

        Toast.makeText(getActivity(), e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
    }

    protected void showListView(boolean immediately) {
        if (immediately) {
            mLoadingImage.setAlpha(0.0f);
            mLoadingImage.setVisibility(View.GONE);
            mListView.setAlpha(1.0f);
            mListView.setVisibility(View.VISIBLE);
        }
        else {
            mErrorContainer.setVisibility(View.GONE);
            new CrossFader(mLoadingImage, mListView, 500).start();
        }
    }

    protected void showListView() {
        showListView(false);
    }

    protected void showLoadingAnimation() {
        mErrorContainer.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        mLoadingImage.setAlpha(1f);
        mLoadingImage.setVisibility(View.VISIBLE);
        mLoadingImage.startAnimation(new LoadingAnimation());
    }
}