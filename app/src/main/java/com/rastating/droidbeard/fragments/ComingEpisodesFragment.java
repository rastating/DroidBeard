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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.UpcomingEpisode;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchUpcomingEpisodesTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComingEpisodesFragment extends ListViewFragment implements ApiResponseListener<UpcomingEpisode[]> {
    public ComingEpisodesFragment() {
        setTitle("Coming Episodes");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        setChoiceMode(ListView.CHOICE_MODE_NONE);
        setListSelector(android.R.color.transparent);
        setBackgroundColor(getResources().getColor(android.R.color.white));
        setDivider(android.R.color.white, 3);

        onRefreshButtonPressed();

        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onApiRequestFinished(UpcomingEpisode[] result) {
        if (activityStillExists()) {
            if (result != null) {
                ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
                for (UpcomingEpisode episode : result) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("name", episode.getName());
                    item.put("desc", String.format("%s - %dx%d - %s", episode.getShowName(), episode.getSeasonNumber(), episode.getEpisodeNumber(), episode.getAirdateString("yyyy-MM-dd")));
                    data.add(item);
                }

                String[] from = new String[]{"name", "desc"};
                int[] to = new int[]{R.id.episode, R.id.event_details};
                final UpcomingEpisode[] episodes = result;
                SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.historical_event_item, from, to) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        switch (episodes[position].getUpcomingStatus()) {
                            case CURRENT:
                                view.setBackgroundColor(getResources().getColor(R.color.upcoming_episode_current));
                                break;

                            case DISTANT:
                                view.setBackgroundColor(getResources().getColor(R.color.upcoming_episode_distant));
                                break;

                            case FUTURE:
                                view.setBackgroundColor(getResources().getColor(R.color.upcoming_episode_future));
                                break;

                            case PAST:
                                view.setBackgroundColor(getResources().getColor(R.color.upcoming_episode_past));
                                break;
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
            } else {
                showError(getString(R.string.error_fetching_coming_episodes));
            }
        }
    }

    @Override
    public void onRefreshButtonPressed() {
        showLoadingAnimation();
        FetchUpcomingEpisodesTask task = new FetchUpcomingEpisodesTask(getActivity());
        task.addResponseListener(this);
        task.start();
    }
}