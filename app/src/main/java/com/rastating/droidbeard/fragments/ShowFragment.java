package com.rastating.droidbeard.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rastating.droidbeard.Preferences;
import com.rastating.droidbeard.comparators.EpisodeComparator;
import com.rastating.droidbeard.R;
import com.rastating.droidbeard.entities.Episode;
import com.rastating.droidbeard.entities.Season;
import com.rastating.droidbeard.entities.TVShow;
import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.EpisodeSearchTask;
import com.rastating.droidbeard.net.FetchShowTask;
import com.rastating.droidbeard.net.SetEpisodeStatusTask;
import com.rastating.droidbeard.ui.CrossFader;
import com.rastating.droidbeard.ui.EpisodeItem;
import com.rastating.droidbeard.ui.EpisodeItemClickListener;
import com.rastating.droidbeard.ui.LoadingAnimation;
import com.rastating.droidbeard.ui.SeasonTable;

import java.util.Collections;
import java.util.List;

public class ShowFragment extends DroidbeardFragment implements ApiResponseListener<TVShow>,EpisodeItemClickListener {
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

    private int mSelectedSeasonNumber;
    private int mSelectedEpisodeNumber;
    private String mSelectedEpisodeName;
    private EpisodeItem mSelectedEpisode;

    public ShowFragment() {
        mShowSummary = null;
    }

    public void setTvShowSummary(TVShowSummary show) {
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

        onRefreshButtonPressed();

        Preferences preferences = new Preferences(getActivity());
        if (!preferences.hasAcknowledgedEpisodeHelp()) {
            showToolTip();
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefreshButtonPressed() {
        // Start loading animation.
        mDataContainer.setAlpha(0.0f);
        mLoadingImage.setAlpha(1.0f);
        mLoadingImage.startAnimation(new LoadingAnimation());

        // Start fetching the show information.
        FetchShowTask task = new FetchShowTask(getActivity());
        task.addResponseListener(this);
        task.start(mShowSummary.getTvDbId());
    }

    private void populateViews() {
        if (mShow.getBanner() != null) {
            mBanner.setImageBitmap(mShow.getBanner());
        }

        mAirs.setText(mShow.getAirs() + " on " + mShow.getNetwork());
        mStatus.setText(mShow.getStatus());
        mLocation.setText(mShow.getLocation());
        mQuality.setText(mShow.getQuality());
        mLanguage.setText(mShow.getLanguage().getCode());
        mLanguageIcon.setImageResource(mShow.getLanguage().getIconResId());
        mFlattenFolders.setImageResource(mShow.getFlattenFolders() ? R.drawable.yes16 : R.drawable.no16);
        mPaused.setImageResource(mShow.getPaused() ? R.drawable.yes16 : R.drawable.no16);
        mAirByDate.setImageResource(mShow.getAirByDate() ? R.drawable.yes16 : R.drawable.no16);

        mSeasonContainer.removeAllViews();
        for (Season season : mShow.getSeasons()) {
            SeasonTable table = new SeasonTable(getActivity());
            table.setTitle(season.getTitle());

            List<Episode> episodes = season.getEpisodes();
            Collections.sort(episodes, new EpisodeComparator());
            Collections.reverse(episodes);

            for (Episode episode : episodes) {
                EpisodeItem item = table.addEpisode(episode);
                item.setOnItemClickListener(this);
                item.setOnCreateContextMenuListener(this);
            }

            mSeasonContainer.addView(table);
        }
    }

    @Override
    public void onItemClick(EpisodeItem item, int seasonNumber, int episodeNumber, String name) {
        mSelectedEpisodeName = name;
        mSelectedSeasonNumber = seasonNumber;
        mSelectedEpisodeNumber = episodeNumber;
        mSelectedEpisode = item;

        item.showContextMenu();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.episode_context_menu, menu);
        menu.setHeaderTitle(String.format("%dx%d - %s", mSelectedSeasonNumber, mSelectedEpisodeNumber, mSelectedEpisodeName));
    }

    private void onSetStatusItemSelected(MenuItem item) {
        SetEpisodeStatusTask task = new SetEpisodeStatusTask(getActivity(), mShowSummary.getTvDbId(), mSelectedSeasonNumber, mSelectedEpisodeNumber);
        String status = "";
        if (item.getItemId() == R.id.set_wanted) {
            status = "wanted";
        }
        else if (item.getItemId() == R.id.set_skipped) {
            status = "skipped";
        }
        else if (item.getItemId() == R.id.set_ignored) {
            status = "ignored";
        }
        else {
            status = "archived";
        }

        final String finalStatus = status;
        final ProgressDialog dialog = createProgressDialog("Setting Status", "Please wait...");
        dialog.show();
        task.addResponseListener(new ApiResponseListener<Boolean>() {
            @Override
            public void onApiRequestFinished(Boolean result) {
                if (result) {
                    mSelectedEpisode.setStatus(finalStatus);
                }
                dialog.dismiss();
            }
        });
        task.start(status);
    }

    private void onEpisodeSearchItemSelected() {
        final ProgressDialog dialog = createProgressDialog("Searching for Episode", "Searching for \"" + mSelectedEpisodeName + "\", please wait...");
        EpisodeSearchTask task = new EpisodeSearchTask(getActivity(), mShowSummary.getTvDbId(), mSelectedSeasonNumber, mSelectedEpisodeNumber);
        dialog.show();
        task.addResponseListener(new ApiResponseListener<Boolean>() {
            @Override
            public void onApiRequestFinished(Boolean result) {
                dialog.dismiss();
            }
        });
        task.start();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_archived:
            case R.id.set_ignored:
            case R.id.set_skipped:
            case R.id.set_wanted:
                onSetStatusItemSelected(item);
                return true;

            case R.id.search:
                onEpisodeSearchItemSelected();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private ProgressDialog createProgressDialog(String title, String message) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        return dialog;
    }

    private void showToolTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.tooltip_dialog, null);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.do_not_show_again);
        builder.setView(view);
        builder.setTitle("DroidBeard Tip");
        builder.setMessage("Tapping an episode in the episode list opens a menu that will allow you to manually search for the episode or change its status.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (checkBox.isChecked()) {
                    Preferences preferences = new Preferences(ShowFragment.this.getActivity());
                    preferences.putBoolean(Preferences.ACKNOWLEDGED_EPISODE_HELP, true);
                }
            }
        });
        builder.show();
    }
}