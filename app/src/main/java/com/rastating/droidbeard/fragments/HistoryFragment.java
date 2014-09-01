package com.rastating.droidbeard.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.HistoricalEvent;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchHistoryTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryFragment extends ListViewFragment implements ApiResponseListener<HistoricalEvent[]> {

    public HistoryFragment() {
        setTitle(R.string.title_history);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        setChoiceMode(ListView.CHOICE_MODE_NONE);
        setListSelector(android.R.color.transparent);

        showLoadingAnimation();
        FetchHistoryTask task = new FetchHistoryTask(getActivity());
        task.addResponseListener(this);
        task.execute();

        return root;
    }

    @Override
    public void onApiRequestFinished(HistoricalEvent[] result) {
        if (result != null) {
            if (activityStillExists()) {
                ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>(result.length);
                for (HistoricalEvent event : result) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("name", String.format("%s %dx%d", event.getShowName(), event.getSeason(), event.getEpisodeNumber()));
                    item.put("desc", String.format("%s (%s) on %s", event.getStatus(), event.getQuality(), event.getDate()));
                    data.add(item);
                }

                String[] from = new String[]{"name", "desc"};
                int[] to = new int[]{R.id.episode, R.id.event_details};
                SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.historical_event_item, from, to) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        if (position % 2 == 0) {
                            view.setBackgroundResource(R.drawable.alternate_list_item_bg);
                        } else {
                            view.setBackgroundColor(Color.TRANSPARENT);
                        }

                        return view;
                    }

                    @Override
                    public boolean isEnabled(int position) {
                        return false;
                    }
                };
                setAdapter(adapter);

                showListView();
            }
        }
        else {
            showError(getString(R.string.error_fetching_history));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }
}