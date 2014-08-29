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

    protected void showError(String message) {
        mErrorMessage.setText(message);
        mErrorContainer.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        mLoadingImage.clearAnimation();
        mLoadingImage.setVisibility(View.GONE);
    }

    protected void showListView() {
        mErrorContainer.setVisibility(View.GONE);
        new CrossFader(mLoadingImage, mListView, 500).start();
    }

    protected void showLoadingAnimation() {
        mErrorContainer.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        mLoadingImage.setVisibility(View.VISIBLE);
        mLoadingImage.startAnimation(new LoadingAnimation());
    }
}