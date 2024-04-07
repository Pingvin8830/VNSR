package ru.sknt.vlasovnetwork.vnsr.models.car;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    private int mFuelStationId;
    @Ignore
    private FuelStation mFuelStation;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public int getFuelStationId() { return this.mFuelStationId; }

    public void setId(int id) { this.mId = id; }

    public Fuel(String name, int fuelStationId) {
        this.mName = name;
        this.mFuelStationId = fuelStationId;
    }
}
