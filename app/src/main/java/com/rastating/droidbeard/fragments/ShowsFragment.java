package com.rastating.droidbeard.fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.adapters.TVShowSummaryAdapter;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchShowSummariesTask;
import com.rastating.droidbeard.ui.CrossFader;
import com.rastating.droidbeard.ui.LoadingAnimation;

public class ShowsFragment extends DroidbeardFragment implements AdapterView.OnItemClickListener, ApiResponseListener<TVShowSummary[]> {
    private ListView mListView;
    private View mErrorContainer;
    private TVShowSummaryAdapter mAdapter;
    private ImageView mLoadingImage;

    public ShowsFragment() {
        setTitle(R.string.title_shows);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_list, container, false);
        mListView = (ListView) root.findViewById(R.id.show_list_view);
        mErrorContainer = root.findViewById(R.id.error_container);
        mLoadingImage = (ImageView) root.findViewById(R.id.loading);

        if (mAdapter == null) {
            mListView.setVisibility(View.GONE);
            mLoadingImage.startAnimation(new LoadingAnimation());

            FetchShowSummariesTask task = new FetchShowSummariesTask(getActivity());
            task.addResponseListener(this);
            task.execute();
        }
        else {
            mListView.setVisibility(View.VISIBLE);
            mLoadingImage.setVisibility(View.GONE);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(this);
            mListView.setVisibility(View.VISIBLE);
            mErrorContainer.setVisibility(View.GONE);
        }

        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        view.setSelected(true);
        TVShowSummary show = mAdapter.getItem(position);
        FragmentManager manager = this.getFragmentManager();
        ShowFragment fragment = new ShowFragment(show);
        manager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onApiRequestFinished(TVShowSummary[] objects) {
        if (objects != null) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            mAdapter = new TVShowSummaryAdapter(this.getActivity(), inflater, R.layout.tv_show_list_item, objects);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(this);
            mErrorContainer.setVisibility(View.GONE);
            new CrossFader(mLoadingImage, mListView, 500).start();
        }
        else {
            mListView.setVisibility(View.GONE);
            mErrorContainer.setVisibility(View.VISIBLE);
        }
    }
}