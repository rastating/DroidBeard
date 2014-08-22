package com.rastating.droidbeard;

import com.rastating.droidbeard.entities.TVShowSummary;

import java.util.Comparator;

public class TVShowSummaryComparator implements Comparator<TVShowSummary> {
    @Override
    public int compare(TVShowSummary tvShowSummary, TVShowSummary tvShowSummary2) {
        return tvShowSummary.getName().compareTo(tvShowSummary2.getName());
    }
}
