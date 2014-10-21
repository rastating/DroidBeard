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

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

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
