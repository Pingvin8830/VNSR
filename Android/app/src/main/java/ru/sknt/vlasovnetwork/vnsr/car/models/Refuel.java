package ru.sknt.vlasovnetwork.vnsr.car.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


import org.json.JSONException;
import org.json.JSONObject;

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
    private final FuelStation mFuelStation;
    @ColumnInfo(name = "check_number")
    private final int mCheckNumber;
    @ColumnInfo(name = "datetime")
    private final FormatedDate mDateTime;
    @ColumnInfo(name = "trk")
    private final int mTrk;
    @ColumnInfo(name = "fuel_id")
    private final Fuel mFuel;
    @ColumnInfo(name = "count")
    private final float mCount;
    @ColumnInfo(name = "price")
    private final float mPrice;
    @ColumnInfo(name = "cost")
    private final float mCost;
    @ColumnInfo(name = "distance_reserve")
    private final int mDistanceReserve;
    @ColumnInfo(name = "fuel_consumption_avg")
    private final float mFuelConsumptionAvg;
    @ColumnInfo(name = "odometer")
    private final int mOdometer;
    @ColumnInfo(name = "distance")
    private final float mDistance;
    @ColumnInfo(name = "fuel_consumption")
    private final float mFuelConsumption;
    @ColumnInfo(name = "timedelta")
    private final String mTimedelta;

    public int getId() { return this.mId; }
    public FuelStation getFuelStation() { return this.mFuelStation; }
    public int getCheckNumber() { return this.mCheckNumber; }
    public FormatedDate getDateTime() { return this.mDateTime; }
    public int getTrk() { return this.mTrk; }
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
        this.mCheckNumber = checkNumber;
        this.mDateTime = dateTime;
        this.mTrk = trk;
        this.mFuel = fuel;
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
    public Refuel(JSONObject data) throws JSONException {
        this.mFuelStation = MainActivity.FuelStationDao.find(data.getString("fuel_station_company"), data.getString("fuel_station_number"));
        this.mCheckNumber = Integer.parseInt(data.getString("check_number"));
        this.mDateTime = new FormatedDate(0L);
        this.mDateTime.setYear(Integer.parseInt(data.getString("year")));
        this.mDateTime.setMonth(Integer.parseInt(data.getString("month"))-1);
        this.mDateTime.setDate(Integer.parseInt(data.getString("day")));
        this.mDateTime.setHours(Integer.parseInt(data.getString("hour")));
        this.mDateTime.setMinutes(Integer.parseInt(data.getString("minute")));
        this.mDateTime.setSeconds(0);
        this.mTrk = Integer.parseInt(data.getString("trk"));
        this.mFuel = MainActivity.FuelDao.find(data.getString("fuel_name"));
        this.mCount = Float.parseFloat(data.getString("count"));
        this.mPrice = Float.parseFloat(data.getString("price"));
        this.mCost = Float.parseFloat(data.getString("cost"));
        this.mDistanceReserve = Integer.parseInt(data.getString("distance_reserve"));
        this.mFuelConsumptionAvg = Float.parseFloat(data.getString("fuel_consumption_avg"));
        this.mOdometer = Integer.parseInt(data.getString("odometer"));
        this.mDistance = Float.parseFloat(data.getString("distance"));
        this.mFuelConsumption = Float.parseFloat(data.getString("fuel_consumption"));
        this.mTimedelta = data.getString("timedelta");
    }

    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "Refuel")
                .put("id",                   this.getId())
                .put("check_number",         this.getCheckNumber())
                .put("datetime",             this.getDateTime())
                .put("price",                this.getPrice())
                .put("count",                this.getCount())
                .put("cost",                 this.getCost())
                .put("distance",             this.getDistance())
                .put("fuel_name",            this.getFuel().getName())
                .put("distance_reserve",     this.getDistanceReserve())
                .put("fuel_consumption",     this.getFuelConsumption())
                .put("fuel_consumption_avg", this.getFuelConsumptionAvg())
                .put("fuel_station_company", this.getFuelStation().getCompany())
                .put("fuel_station_number",  this.getFuelStation().getNumber())
                .put("odometer",             this.getOdometer())
                .put("timedelta",            this.getTimedelta())
                .put("trk",                  this.getTrk());
        return res;
    }
}
