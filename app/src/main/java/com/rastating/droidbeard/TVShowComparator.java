package com.rastating.droidbeard;

import java.util.Comparator;

public class TVShowComparator implements Comparator<TVShow> {
    @Override
    public int compare(TVShow tvShow, TVShow tvShow2) {
        return tvShow.getName().compareTo(tvShow2.getName());
    }
}
