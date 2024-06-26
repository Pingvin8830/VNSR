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
        tableName = "kladr_city",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class City {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "type_id")
    private final CityType mCityType;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public CityType getCityType() { return this.mCityType; }

    public void setId(int id) { this.mId = id; }

    public City(String name, CityType cityType) {
        this.mName = name;
        this.mCityType = cityType;
    }
    public City (JSONObject data) throws JSONException {
        JSONObject cityTypeJson = new JSONObject(data.getString("city_type"));
        this.mName = data.getString("name");
        this.mCityType = MainActivity.CityTypeDao.find(cityTypeJson.getString("name"));
    }

    @NonNull
    @Override
    public String toString() {
        return getCityType().getShort() + ". " + getName();
    }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        JSONObject cityTypeJson = new JSONObject();
        cityTypeJson.put("name", this.getCityType().getName());
        res
                .put("object", "City")
                .put("id", this.getId())
                .put("city_type", cityTypeJson)
                .put("name", this.getName());
        return res;
    }
}
