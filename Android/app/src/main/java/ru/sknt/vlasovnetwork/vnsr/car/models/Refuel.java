package ru.sknt.vlasovnetwork.vnsr.car.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
import ru.sknt.vlasovnetwork.vnsr.MainActivity;

@Entity(
        tableName = "car_refuel",
        indices = {
                @Index(
                        value = {"datetime"},
                        unique = true
                )
        }
)
public class Refuel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "fuel_station_id")
    private final int mFuelStationId;
    @Ignore
    private FuelStation mFuelStation;
    @ColumnInfo(name = "check_number")
    private final int mCheckNumber;
    @ColumnInfo(name = "datetime")
    private final FormatedDate mDateTime;
    @ColumnInfo(name = "trk")
    private int mTrk = 3;
    @ColumnInfo(name = "fuel_id")
    private final int mFuelId;
    @Ignore
    private Fuel mFuel;
    @ColumnInfo(name = "count")
    private final float mCount;
    @ColumnInfo(name = "price")
    private final float mPrice;
    @ColumnInfo(name = "cost")
    private final float mCost;
    @ColumnInfo(name = "distance_reserve")
    private int mDistanceReserve = 500;
    @ColumnInfo(name = "fuel_consumption_avg")
    private float mFuelConsumptionAvg = 10f;
    @ColumnInfo(name = "odometer")
    private final int mOdometer;
    @ColumnInfo(name = "distance")
    private final float mDistance;
    @ColumnInfo(name = "fuel_consumption")
    private float mFuelConsumption = 10f;
    @ColumnInfo(name = "timedelta")
    private final String mTimedelta;

    public int getId() { return this.mId; }
    public int getFuelStationId() { return this.mFuelStationId; }
    public FuelStation getFuelStation() { return this.mFuelStation; }
    public int getCheckNumber() { return this.mCheckNumber; }
    public FormatedDate getDateTime() { return this.mDateTime; }
    public int getTrk() { return this.mTrk; }
    public int getFuelId() { return this.mFuelId; }
    public Fuel getFuel() { return this.mFuel; }
    public float getCount() { return this.mCount; }
    public float getPrice() { return this.mPrice; }
    public float getCost() { return this.mCost; }
    public int getDistanceReserve() { return this.mDistanceReserve; }
    public float getFuelConsumptionAvg() { return this.mFuelConsumptionAvg; }
    public int getOdometer() { return this.mOdometer; }
    public float getDistance() { return this.mDistance; }
    public float getFuelConsumption() { return this.mFuelConsumption; }
    public String getTimedelta() { return this.mTimedelta; }

    public void setId(int id) { this.mId = id; }
    public void setFuelStation() { this.mFuelStation = MainActivity.FuelStationDao.find(mFuelStationId); }
    public void setFuel() {
        this.mFuel = MainActivity.FuelDao.find(mFuelId);
        this.mFuel.setFuelStation();
    }

    public Refuel(
            int fuelStationId,
            int checkNumber,
            FormatedDate dateTime,
            int trk,
            int fuelId,
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
        this.mFuelStationId = fuelStationId;
        this.setFuelStation();
        this.mCheckNumber = checkNumber;
        this.mDateTime = dateTime;
        this.mTrk = trk;
        this.mFuelId = fuelId;
        this.setFuel();
        this.mCount = count;
        this.mPrice = price;
        this.mCost = cost;
        this.mDistanceReserve = distanceReserve;
        this.mFuelConsumptionAvg = fuelConsumptionAvg;
        this.mOdometer = odometer;
        this.mDistance = distance;
        this.mFuelConsumption = fuelConsumption;
        this.mTimedelta = timedelta;
    }
    public Refuel(
            FuelStation fuelStation,
            int checkNumber,
            FormatedDate dateTime,
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
        this.mFuelStationId = fuelStation.getId();
        this.mCheckNumber = checkNumber;
        this.mDateTime = dateTime;
        this.mTrk = trk;
        this.mFuel = fuel;
        this.mFuelId = fuel.getId();
        this.mCount = count;
        this.mPrice = price;
        this.mCost = cost;
        this.mDistanceReserve = distanceReserve;
        this.mFuelConsumptionAvg = fuelConsumptionAvg;
        this.mOdometer = odometer;
        this.mDistance = distance;
        this.mFuelConsumption = fuelConsumption;
        this.mTimedelta = timedelta;
    }
}
