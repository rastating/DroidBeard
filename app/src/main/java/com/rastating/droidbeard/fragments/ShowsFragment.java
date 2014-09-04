package com.rastating.droidbeard.fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.adapters.TVShowSummaryAdapter;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchShowSummariesTask;

public class ShowsFragment extends ListViewFragment implements ApiResponseListener<TVShowSummary[]> {
    private TVShowSummaryAdapter mAdapter;

    public ShowsFragment() {
        setTitle(R.string.title_shows);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        showLoadingAnimation();
        FetchShowSummariesTask task = new FetchShowSummariesTask(getActivity());
        task.addResponseListener(this);
        task.execute();

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
        if (activityStillExists()) {
            if (objects != null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                mAdapter = new TVShowSummaryAdapter(this.getActivity(), inflater, R.layout.tv_show_list_item, objects);
                setAdapter(mAdapter);
                showListView();
            } else {
                showError(getString(R.string.error_fetching_show_list));
            }
        }
    }
}