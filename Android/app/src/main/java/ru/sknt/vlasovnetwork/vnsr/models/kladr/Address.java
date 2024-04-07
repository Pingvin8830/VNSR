package ru.sknt.vlasovnetwork.vnsr.models.kladr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "kladr_address",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class Address {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "region_id")
    private int mRegionId;
    @Ignore
    private Region mRegion;
    @ColumnInfo(name = "city_id")
    private int mCityId;
    @Ignore
    private City mCity;
    @ColumnInfo(name = "street_id")
    private int mStreetId;
    @Ignore
    private Street mStreet;
    @ColumnInfo(name = "house")
    private final String mHouse;
    @ColumnInfo(name = "building")
    private final String mBuilding;
    @ColumnInfo(name = "flat")
    private int mFlat;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public int getRegionId() { return this.mRegionId; }
    public int getCityId() { return this.mCityId; }
    public int getStreetId() { return this.mStreetId; }
    public String getHouse() { return this.mHouse; }
    public String getBuilding() { return this.mBuilding; }
    public int getFlat() { return this.mFlat; }

    public void setId(int id) { this.mId = id; }

    public Address (String name, int regionId, int cityId, int streetId, String house, String building, int flat) {
        this.mName = name;
        this.mRegionId = regionId;
        this.mCityId = cityId;
        this.mStreetId = streetId;
        this.mHouse = house;
        this.mBuilding = building;
        this.mFlat = flat;
    }
}
