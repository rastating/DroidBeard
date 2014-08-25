package com.rastating.droidbeard.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Episode {
    public enum EpisodeStatus {
        SKIPPED,
        UNAIRED,
        WANTED,
        DOWNLOADED,
        SNATCHED,
        IGNORED
    }

    private int mEpisodeNumber;
    private Date mAirdate;
    private String mName;
    private String mQuality;
    private EpisodeStatus mStatus;

    public Date getAirdate() {
        return mAirdate;
    }

    public String getName() {
        return mName;
    }

    public int getEpisodeNumber() {
        return mEpisodeNumber;
    }

    public String getQuality() {
        return mQuality;
    }

    public EpisodeStatus getStatus() {
        return mStatus;
    }

    public String getStatusString() {
        switch (mStatus) {
            case DOWNLOADED:
                return "Downloaded";

            case SKIPPED:
                return "Skipped";

            case UNAIRED:
                return "Unaired";

            case WANTED:
                return "Wanted";

            case SNATCHED:
                return "Snatched";

            case IGNORED:
                return "Ignored";
        }

        return "";
    }

    public void setAirdate(Date value) {
        mAirdate = value;
    }

    public void setAirdate(String value) {
        try {
            if (value.equals("")) {
                mAirdate = null;
            }
            else {
                mAirdate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
            }
        } catch (ParseException e) {
            mAirdate = null;
        }
    }

    public void setEpisodeNumber(int value) {
        mEpisodeNumber = value;
    }

    public void setName(String value) {
        mName = value;
    }

    public void setQuality(String value) {
        mQuality = value;
    }

    public void setStatus(EpisodeStatus value) {
        mStatus = value;
    }

    public void setStatus(String value) {
        if (value.equalsIgnoreCase("skipped")) {
            mStatus = EpisodeStatus.SKIPPED;
        }
        else if (value.equalsIgnoreCase("unaired")) {
            mStatus = EpisodeStatus.UNAIRED;
        }
        else if (value.equalsIgnoreCase("wanted")) {
            mStatus = EpisodeStatus.WANTED;
        }
        else if (value.equalsIgnoreCase("downloaded")) {
            mStatus = EpisodeStatus.DOWNLOADED;
        }
        else if (value.equalsIgnoreCase("snatched")) {
            mStatus = EpisodeStatus.SNATCHED;
        }
        else if (value.equalsIgnoreCase("ignored")) {
            mStatus = EpisodeStatus.IGNORED;
        }
        else {
            mStatus = EpisodeStatus.IGNORED;
        }
    }
}