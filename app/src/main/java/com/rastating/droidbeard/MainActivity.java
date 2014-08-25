package com.rastating.droidbeard;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.rastating.droidbeard.fragments.DroidbeardFragment;
import com.rastating.droidbeard.fragments.NavigationDrawerFragment;
import com.rastating.droidbeard.fragments.PreferencesFragment;
import com.rastating.droidbeard.fragments.ShowFragment;
import com.rastating.droidbeard.fragments.ShowsFragment;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Fragment mCurrentFragment;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ShowsFragment mShowsFragment;
    private CharSequence mTitle;

    private Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public void setCurrentFragment(Fragment value) {
        mCurrentFragment = value;
    }

    @Override
    public void onBackPressed() {
        if (getCurrentFragment() == null) {
            super.onBackPressed();
        }
        else {
            DroidbeardFragment fragment = (DroidbeardFragment) getCurrentFragment();
            if (fragment.onBackPressed()) {
                if (fragment instanceof ShowFragment) {
                    onNavigationDrawerItemSelected(0);
                }
                else {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        if (mTitle == null) {
            mTitle = getTitle();
        }

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = null;

        if (position == 0) {
            if (mShowsFragment == null) {
                mShowsFragment = new ShowsFragment();
            }

            fragment = mShowsFragment;
        }
        else if (position == 4) {
            fragment = new PreferencesFragment();
        }

        if (fragment != null) {
            FragmentManager manager = this.getFragmentManager();
            manager.beginTransaction().replace(R.id.container, fragment).commit();
            setCurrentFragment(fragment);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);

        if (mTitle != null) {
            actionBar.setTitle(mTitle);
        }
    }

    public void setTitle(String value) {
        mTitle = value;
        restoreActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
