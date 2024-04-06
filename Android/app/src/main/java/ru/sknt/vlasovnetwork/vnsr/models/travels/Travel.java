package ru.sknt.vlasovnetwork.vnsr.models.travels;

public class Travel {
    private final String mName;
    private final String mParticipants;
    private final TravelState mState;
    private float mFuelConsumption = 10f;
    private float mFuelPrice;

    public Travel (String name, String participants, TravelState state, float fuelConsumption, float fuelPrice) {
        this.mName = name;
        this.mParticipants = participants;
        this.mState = state;
        this.mFuelConsumption = fuelConsumption;
        this.mFuelPrice = fuelPrice;
    }
}
