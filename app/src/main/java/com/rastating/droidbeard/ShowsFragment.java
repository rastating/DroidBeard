package com.rastating.droidbeard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rastating.droidbeard.net.ShowsRequestTask;

public class ShowsFragment extends Fragment implements AdapterView.OnItemClickListener, ApiResponseListener<TVShow> {
    private ListView mListView;
    private TVShowAdapter mAdapter;

    public ShowsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_list, container, false);
        mListView = (ListView) root.findViewById(R.id.show_list_view);

        //TVShow[] shows = new TVShow[] { new TVShow("Breaking Bad"), new TVShow("Rectify"), new TVShow("American Horror Story") };

        ShowsRequestTask task = new ShowsRequestTask(getActivity());
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        view.setSelected(true);
    }

    @Override
    public void onApiRequestFinished(TVShow[] objects) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mAdapter = new TVShowAdapter(this.getActivity(), inflater, R.layout.tv_show_list_item, objects);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

    }
}