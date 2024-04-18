package ru.sknt.vlasovnetwork.vnsr.car.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;


@Entity(
        tableName = "car_fuel"
)
public class Fuel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "fuel_station_id")
    private final int mFuelStationId;
    @Ignore
    private FuelStation mFuelStation;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public int getFuelStationId() { return this.mFuelStationId; }
    public FuelStation getFuelStation() { return this.mFuelStation; }

    public void setId(int id) { this.mId = id; }
    public void setFuelStation() { this.mFuelStation = MainActivity.FuelStationDao.find(this.mFuelStationId); }

    public Fuel(String name, int fuelStationId) {
        this.mName = name;
        this.mFuelStationId = fuelStationId;
        this.setFuelStation();
    }
    public Fuel(String name, FuelStation fuelStation) {
        this.mName = name;
        this.mFuelStation = fuelStation;
        this.mFuelStationId = fuelStation.getId();
    }

    @NonNull
    @Override
    public String toString() { return this.mName; }
}
