package ru.sknt.vlasovnetwork.vnsr.models.travels;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.Address;

public class Hotel {
    private final Travel mTravel;
    private final Address mAddress;
    private long mArrival;
    private long mDeparture;
    private float mCost;
    private final String mState;

    public Hotel (Travel travel, Address address, long arrival, long departure, float cost, String state) {
        this.mTravel = travel;
        this.mAddress = address;
        this.mArrival = arrival;
        this.mDeparture = departure;
        this.mCost = cost;
        this.mState = state;
    }
}
