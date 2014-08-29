package com.rastating.droidbeard.comparators;

import com.rastating.droidbeard.entities.UpcomingEpisode;

import java.util.Comparator;

public class UpcomingEpisodeComparator implements Comparator<UpcomingEpisode> {
    @Override
    public int compare(UpcomingEpisode upcomingEpisode, UpcomingEpisode upcomingEpisode2) {
        return upcomingEpisode.getAirdate().compareTo(upcomingEpisode2.getAirdate());
    }
}