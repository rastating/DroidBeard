package com.rastating.droidbeard.comparators;

import com.rastating.droidbeard.entities.Season;

import java.util.Comparator;

public class SeasonComparator implements Comparator<Season> {
    @Override
    public int compare(Season season, Season season2) {
        return ((Integer) season.getSeasonNumber()).compareTo(season2.getSeasonNumber());
    }
}