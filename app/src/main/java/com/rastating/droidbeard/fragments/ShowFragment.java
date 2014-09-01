package com.rastating.droidbeard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rastating.droidbeard.comparators.EpisodeComparator;
import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.Episode;
import com.rastating.droidbeard.entities.Season;
import com.rastating.droidbeard.entities.TVShow;
import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchShowTask;
import com.rastating.droidbeard.ui.CrossFader;
import com.rastating.droidbeard.ui.LoadingAnimation;
import com.rastating.droidbeard.ui.SeasonTable;

import java.util.Collections;
import java.util.List;

public class ShowFragment extends DroidbeardFragment implements ApiResponseListener<TVShow> {
    private TVShowSummary mShowSummary;
    private ImageView mBanner;
    private ImageView mLoadingImage;
    private View mDataContainer;
    private TVShow mShow;
    private TextView mAirs;
    private TextView mStatus;
    private TextView mLocation;
    private TextView mQuality;
    private TextView mLanguage;
    private ImageView mLanguageIcon;
    private ImageView mFlattenFolders;
    private ImageView mPaused;
    private ImageView mAirByDate;
    private LinearLayout mSeasonContainer;

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

        // Get references to all required views.
        mBanner = (ImageView) root.findViewById(R.id.banner);
        mLoadingImage = (ImageView) root.findViewById(R.id.loading);
        mDataContainer = root.findViewById(R.id.data);
        mAirs = (TextView) root.findViewById(R.id.airs);
        mStatus = (TextView) root.findViewById(R.id.status);
        mLocation = (TextView) root.findViewById(R.id.location);
        mQuality = (TextView) root.findViewById(R.id.quality);
        mLanguage = (TextView) root.findViewById(R.id.language);
        mLanguageIcon = (ImageView) root.findViewById(R.id.language_icon);
        mFlattenFolders = (ImageView) root.findViewById(R.id.flatten_folders);
        mPaused = (ImageView) root.findViewById(R.id.paused);
        mAirByDate = (ImageView) root.findViewById(R.id.air_by_date);
        mSeasonContainer = (LinearLayout) root.findViewById(R.id.season_container);

        // Start loading animation.
        mLoadingImage.startAnimation(new LoadingAnimation());

        // Start fetching the show information.
        FetchShowTask task = new FetchShowTask(getActivity());
        task.addResponseListener(this);
        task.execute(mShowSummary.getTvDbId());

        return root;
    }

    @Override
    public void onApiRequestFinished(TVShow result) {
        if (activityStillExists()) {
            mShow = result;
            populateViews();
            new CrossFader(mLoadingImage, mDataContainer, 500).start();
        }
    }

    private void populateViews() {
        mBanner.setImageBitmap(mShow.getBanner());
        mAirs.setText(mShow.getAirs() + " on " + mShow.getNetwork());
        mStatus.setText(mShow.getStatus());
        mLocation.setText(mShow.getLocation());
        mQuality.setText(mShow.getQuality());
        mLanguage.setText(mShow.getLanguage().getCode());
        mLanguageIcon.setImageResource(mShow.getLanguage().getIconResId());
        mFlattenFolders.setImageResource(mShow.getFlattenFolders() ? R.drawable.yes16 : R.drawable.no16);
        mPaused.setImageResource(mShow.getPaused() ? R.drawable.yes16 : R.drawable.no16);
        mAirByDate.setImageResource(mShow.getAirByDate() ? R.drawable.yes16 : R.drawable.no16);

        for (Season season : mShow.getSeasons()) {
            SeasonTable table = new SeasonTable(getActivity());
            table.setTitle(season.getTitle());

            List<Episode> episodes = season.getEpisodes();
            Collections.sort(episodes, new EpisodeComparator());
            Collections.reverse(episodes);

            for (Episode episode : episodes) {
                table.addEpisode(episode);
            }

            mSeasonContainer.addView(table);
        }
    }
}