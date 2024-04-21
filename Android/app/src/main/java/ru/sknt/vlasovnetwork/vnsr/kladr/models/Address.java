package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

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
    private final Region mRegion;
    @ColumnInfo(name = "city_id")
    private final City mCity;
    @ColumnInfo(name = "street_id")
    private final Street mStreet;
    @ColumnInfo(name = "house")
    private final String mHouse;
    @ColumnInfo(name = "building")
    private final String mBuilding;
    @ColumnInfo(name = "flat")
    private final int mFlat;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public Region getRegion() { return this.mRegion; }
    public City getCity() { return mCity; }
    public Street getStreet() { return this.mStreet; }
    public String getHouse() { return this.mHouse; }
    public String getBuilding() { return this.mBuilding; }
    public int getFlat() { return this.mFlat; }
    public String getFull() {
        String res = getCity() + ", " + getStreet() + ", д. " + getHouse();
        if (!mBuilding.isEmpty()) { res += ", корп. " + getBuilding(); }
        if (mFlat > 0) { res += ", кв. " + getFlat(); }
        return res;
    }

    public void setId(int id) { this.mId = id; }
    public Address (String name, Region region, City city, Street street, String house, String building, int flat) {
        this.mName = name;
        this.mRegion = region;
        this.mCity = city;
        this.mStreet = street;
        this.mHouse = house;
        this.mBuilding = building;
        this.mFlat = flat;
    }

    @NonNull
    @Override
    public String toString() { return getName(); }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "Address")
                .put("id", this.getId())
                .put("region_id", this.getRegion().getId())
                .put("city_id", this.getCity().getId())
                .put("street_id", this.getStreet().getId())
                .put("house", this.getHouse())
                .put("building", this.getBuilding())
                .put("flat", this.getFlat())
                .put("name", this.getName());
        return res;
    }
}
