package com.rastating.droidbeard.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rastating.droidbeard.MainActivity;
import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.adapters.TVShowSummaryAdapter;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchShowSummariesTask;

public class ShowsFragment extends Fragment implements AdapterView.OnItemClickListener, ApiResponseListener<TVShowSummary[]> {
    private ListView mListView;
    private TVShowSummaryAdapter mAdapter;

    public ShowsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_list, container, false);
        mListView = (ListView) root.findViewById(R.id.show_list_view);

        FetchShowSummariesTask task = new FetchShowSummariesTask(getActivity());
        task.addResponseListener(this);
        task.execute();

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mAdapter = new TVShowSummaryAdapter(this.getActivity(), inflater, R.layout.tv_show_list_item, objects);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
    }
}