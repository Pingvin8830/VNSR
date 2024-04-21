package ru.sknt.vlasovnetwork.vnsr.models.car;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
    private int mFuelStationId;
    @Ignore
    private FuelStation mFuelStation;
    @ColumnInfo(name = "check_number")
    private int mCheckNumber;
    @ColumnInfo(name = "datetime")
    private long mDateTime;
    @ColumnInfo(name = "trk")
    private int mTrk = 3;
    @ColumnInfo(name = "fuel_id")
    private int mFuelId;
    @Ignore
    private Fuel mFuel;
    @ColumnInfo(name = "count")
    private float mCount;
    @ColumnInfo(name = "price")
    private float mPrice;
    @ColumnInfo(name = "cost")
    private float mCost;
    @ColumnInfo(name = "distance_reserve")
    private int mDistanceReserve = 500;
    @ColumnInfo(name = "fuel_consumption_avg")
    private float mFuelConsumptionAvg = 10f;
    @ColumnInfo(name = "odometer")
    private int mOdometer;
    @ColumnInfo(name = "distance")
    private float mDistance;
    @ColumnInfo(name = "fuel_consumption")
    private float mFuelConsumption = 10f;
    @ColumnInfo(name = "timedelta")
    private final String mTimedelta;

    public int getId() { return this.mId; }
    public int getFuelStationId() { return this.mFuelStationId; }
    public int getCheckNumber() { return this.mCheckNumber; }
    public long getDateTime() { return this.mDateTime; }
    public int getTrk() { return this.mTrk; }
    public int getFuelId() { return this.mFuelId; }
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

    public Refuel(
            int fuelStationId,
            int checkNumber,
            long dateTime,
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
        this.mCheckNumber = checkNumber;
        this.mDateTime = dateTime;
        this.mTrk = trk;
        this.mFuelId = fuelId;
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
