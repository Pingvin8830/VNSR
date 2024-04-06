package ru.sknt.vlasovnetwork.vnsr.models.car;

public class Refuel {
    private final FuelStation mFuelStation;
    private int mCheckNumber;
    private long mDateTime;
    private int mTrk = 3;
    private final Fuel mFuel;
    private float mCount;
    private float mPrice;
    private float mCost;
    private int mDistanceReserve = 500;
    private float mFuelConsumptionAvg = 10f;
    private int mOdometer;
    private float mDistance;
    private float mFuelConsumption = 10f;
    private final String mTimedelta;

    public Refuel(
            FuelStation fuelStation,
            int checkNumber,
            long dateTime,
            int trk,
            Fuel fuel,
            float count,
            float price,
            float cost,
            int distanceReserve,
            float fuelConsumptionAvg,
            int odometer,
            float distance,
            float fuelConsumption,
            String timedelta
    ) {
        this.mFuelStation = fuelStation;
        this.mCheckNumber = checkNumber;
        this.mDateTime = dateTime;
        this.mTrk = trk;
        this.mFuel = fuel;
        this.mCount = count;
        this.mPrice = price;
        this.mCost = cost;
        this.mDistanceReserve = distanceReserve;
        this.mFuelConsumptionAvg = fuelConsumption;
        this.mOdometer = odometer;
        this.mDistance = distance;
        this.mFuelConsumption = fuelConsumption;
        this.mTimedelta = timedelta;
    }
}
