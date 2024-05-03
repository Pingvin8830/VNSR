package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;

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
    private String mBuilding;
    @ColumnInfo(name = "flat")
    private int mFlat;

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
    public Address (JSONObject data) throws JSONException {
        JSONObject regionJson = new JSONObject(data.getString("region"));
        JSONObject cityJson = new JSONObject(data.getString("city"));
        JSONObject streetJson = new JSONObject(data.getString("street"));
        JSONObject streetTypeJson = new JSONObject(streetJson.getString("street_type"));

        this.mName = data.getString("name");
        this.mRegion = MainActivity.RegionDao.find(regionJson.getString("code"));
        this.mCity = MainActivity.CityDao.find(cityJson.getString("name"));
        this.mStreet = MainActivity.StreetDao.find(
                streetJson.getString("name"),
                streetTypeJson.getString("name")
        );
        this.mHouse = data.getString("house");
        this.mBuilding = data.getString("building");
        if (this.mBuilding.equals("null")) { this.mBuilding = ""; }
        try { this.mFlat = Integer.parseInt(data.getString("flat")); }
        catch (NumberFormatException e) { this.mFlat = 0; }
    }

    @NonNull
    @Override
    public String toString() { return getName(); }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        JSONObject regionJson = new JSONObject();
        JSONObject cityJson = new JSONObject();
        JSONObject streetJson = new JSONObject();
        JSONObject streetTypeJson = new JSONObject();
        regionJson.put("code", this.getRegion().getCode());
        cityJson.put("name", this.getCity().getName());
        streetTypeJson.put("name", this.getStreet().getStreetType().getName());
        streetJson.put("name", this.getStreet().getName());
        streetJson.put("street_type", streetTypeJson);
        res
                .put("object", "Address")
                .put("id", this.getId())
                .put("region", regionJson)
                .put("city", cityJson)
                .put("street", streetJson)
                .put("house", this.getHouse())
                .put("building", this.getBuilding())
                .put("flat", this.getFlat())
                .put("name", this.getName());
        return res;
    }
}
