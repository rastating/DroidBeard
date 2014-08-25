package com.rastating.droidbeard.comparators;

import com.rastating.droidbeard.entities.Episode;

import java.util.Comparator;

public class EpisodeComparator implements Comparator<Episode> {
    @Override
    public int compare(Episode episode, Episode episode2) {
        return ((Integer) episode.getEpisodeNumber()).compareTo(episode2.getEpisodeNumber());
    }
}
