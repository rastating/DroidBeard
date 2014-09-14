package com.rastating.droidbeard.fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rastating.droidbeard.R;

public class SetupFragment extends DroidbeardFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_initial_setup, null, false);
        Button button = (Button) root.findViewById(R.id.setup);
        button.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        FragmentManager manager = this.getFragmentManager();
        PreferencesFragment fragment = new PreferencesFragment();
        manager.beginTransaction().replace(R.id.container, fragment).commit();
    }
}