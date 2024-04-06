package ru.sknt.vlasovnetwork.vnsr.models.travels;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.Address;

public class Point {
    private final Travel mTravel;
    private final Address mPlace;
    private long mDatetime;
    private final String mDoing;

    public Point (Travel travel, Address place, long datetime, String doing) {
        this.mTravel = travel;
        this.mPlace = place;
        this.mDatetime = datetime;
        this.mDoing = doing;
    }
}
