package com.rastating.droidbeard.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.rastating.droidbeard.MainActivity;

public abstract class DroidbeardFragment extends Fragment {
    private int mTitleResId;
    private String mTitle;

    protected boolean activityStillExists() {
        return getActivity() != null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity) {
            if (mTitleResId != 0 && mTitle == null) {
                mTitle = getString(mTitleResId);
            }

            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setCurrentFragment(this);
            mainActivity.setTitle(mTitle);
        }
    }

    public boolean onBackPressed() {
        return true;
    }

    public void onRefreshButtonPressed() {
    }

    protected void setTitle(int value) {
        mTitleResId = value;
    }

    protected void setTitle(String value) {
        mTitle = value;
        mTitleResId = 0;
    }
}
