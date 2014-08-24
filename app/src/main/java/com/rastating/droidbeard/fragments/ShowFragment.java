package com.rastating.droidbeard.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.TVShow;
import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchShowTask;

import org.w3c.dom.Text;

public class ShowFragment extends DroidbeardFragment implements ApiResponseListener<TVShow> {
    private TVShowSummary mShowSummary;
    private ImageView mBanner;
    private View mRootView;

    public ShowFragment() {
        mShowSummary = null;
    }

    public ShowFragment(TVShowSummary show) {
        mShowSummary = show;
        setTitle(show.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show, container, false);
        mBanner = (ImageView) root.findViewById(R.id.banner);

        FetchShowTask task = new FetchShowTask(getActivity());
        task.addResponseListener(this);
        task.execute(mShowSummary.getTvDbId());

        mRootView = root;
        return root;
    }

    @Override
    public void onApiRequestFinished(TVShow result) {
        mBanner.setImageBitmap(result.getBanner());

        TextView airs = (TextView) mRootView.findViewById(R.id.airs);
        TextView status = (TextView) mRootView.findViewById(R.id.status);
        TextView location = (TextView) mRootView.findViewById(R.id.location);
        TextView quality = (TextView) mRootView.findViewById(R.id.quality);
        TextView language = (TextView) mRootView.findViewById(R.id.language);
        ImageView languageIcon = (ImageView) mRootView.findViewById(R.id.language_icon);
        ImageView flattenFolders = (ImageView) mRootView.findViewById(R.id.flatten_folders);
        ImageView paused = (ImageView) mRootView.findViewById(R.id.paused);
        ImageView airByDate = (ImageView) mRootView.findViewById(R.id.air_by_date);

        airs.setText(result.getAirs() + " on " + result.getNetwork());
        status.setText(result.getStatus());
        location.setText(result.getLocation());
        quality.setText(result.getQuality());
        language.setText(result.getLanguage().getCode());
        languageIcon.setImageResource(result.getLanguage().getIconResId());
        flattenFolders.setImageResource(result.getFlattenFolders() ? R.drawable.yes16 : R.drawable.no16);
        paused.setImageResource(result.getPaused() ? R.drawable.yes16 : R.drawable.no16);
        airByDate.setImageResource(result.getAirByDate() ? R.drawable.yes16 : R.drawable.no16);
    }
}