package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(
        tableName = "kladr_citytype",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class CityType {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "short")
    private final String mShort;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public String getShort() { return this.mShort; }

    public void setId(int id) { this.mId = id; }

    public CityType (String name, String mShort) {
        this.mName = name;
        this.mShort = mShort;
    }
    public CityType (JSONObject data) throws JSONException {
        this.mName = data.getString("name");
        this.mShort = data.getString("short");
    }

    @NonNull
    @Override
    public String toString() { return getName(); }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "CityType")
                .put("id", this.getId())
                .put("name", this.getName())
                .put("short", this.getShort());
        return res;
    }
}
