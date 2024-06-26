package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(
        tableName = "kladr_region",
        indices = {
                @Index(
                        value = {"code"},
                        unique = true
                )
        }
)
public class Region {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "code")
    private final String mCode;
    @ColumnInfo(name = "name")
    private final String mName;

    public int getId() { return this.mId; }
    public String getCode() { return this.mCode; }
    public String getName() { return this.mName; }

    public void setId(int id) { this.mId = id; }

    public Region (String code, String name) {
        this.mCode = code;
        this.mName = name;
    }
    public Region (JSONObject data) throws JSONException {
        this.mCode = data.getString("code");
        this.mName = data.getString("name");
    }

    @NonNull
    @Override
    public String toString() {
        return getCode() + " - " + getName();
    }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "Region")
                .put("id", this.getId())
                .put("code", this.getCode())
                .put("name", this.getName());
        return res;
    }
}
