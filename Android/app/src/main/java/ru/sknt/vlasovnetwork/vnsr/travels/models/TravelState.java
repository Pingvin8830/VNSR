package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(
        tableName = "travels_travelstate",
        indices = {
                @Index(
                       value={"name"},
                        unique = true
                )})
public class TravelState {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private final String mName;

    public void setId(int id) {
        this.mId = id;
    }
    public int getId() {
        return this.mId;
    }
    public String getName() {
        return this.mName;
    }

    public TravelState(String name) {
        this.mName = name;
    }
    public TravelState(JSONObject data) throws JSONException { this.mName = data.getString("name"); }

    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "TravelState")
                .put("id", this.getId())
                .put("name", this.getName());
        return res;
    }
}