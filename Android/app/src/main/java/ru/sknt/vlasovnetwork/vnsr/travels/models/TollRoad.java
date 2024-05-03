package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;

@Entity (
        tableName = "travels_tollroad"
)
public class TollRoad {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "travel_id")
    private Travel mTravel;
    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "start")
    private final String mStart;
    @ColumnInfo(name = "end")
    private final String mEnd;
    @ColumnInfo(name = "price")
    private float mPrice;

    public int getId() { return this.mId; }
    public Travel getTravel() { return this.mTravel; }
    public String getName() { return this.mName; }
    public String getStart() { return this.mStart; }
    public String getEnd() { return this.mEnd; }
    public float getPrice() { return this.mPrice; }

    public void setId(int id) { this.mId = id; }

    public TollRoad(Travel travel, String name, String start, String end, float price) {
        this.mTravel = travel;
        this.mName = name;
        this.mStart = start;
        this.mEnd = end;
        this.mPrice = price;
    }
    public TollRoad(JSONObject data) throws JSONException {
        JSONObject travelJson = new JSONObject(data.getString("travel"));

        this.mTravel = MainActivity.TravelDao.find(travelJson.getString("name"));
        this.mName = data.getString("name");
        this.mStart = data.getString("start");
        this.mEnd = data.getString("end");
        this.mPrice = Float.parseFloat(data.getString("price"));
    }

    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        JSONObject travelJson = new JSONObject();

        travelJson.put("name", this.getTravel().getName());

        res
                .put("object", "TollRoad")
                .put("id", this.getId())
                .put("travel", travelJson)
                .put("name", this.getName())
                .put("start", this.getStart())
                .put("end", this.getEnd())
                .put("price", this.getPrice());
        return res;
    }
}
