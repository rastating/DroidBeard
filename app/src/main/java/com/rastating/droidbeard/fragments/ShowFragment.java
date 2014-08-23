package com.rastating.droidbeard.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.TVShow;
import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchShowTask;

public class ShowFragment extends DroidbeardFragment implements ApiResponseListener<TVShow> {
    private TVShowSummary mShow;
    private ImageView mBanner;

    public ShowFragment() {
        mShow = null;
    }

    public ShowFragment(TVShowSummary show) {
        mShow = show;
        setTitle(show.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show, container, false);
        mBanner = (ImageView) root.findViewById(R.id.banner);

        FetchShowTask task = new FetchShowTask(getActivity());
        task.addResponseListener(this);
        task.execute(mShow.getTvDbId());

        return root;
    }

    @Override
    public void onApiRequestFinished(TVShow result) {
        mBanner.setImageBitmap(result.getBanner());
    }
}