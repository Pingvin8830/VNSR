package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;

@Entity(
        tableName = "travels_way",
        indices = {
                @Index(
                        value = {"travel_id", "start_point_id", "target_point_id"},
                        unique = true
                )
        }
)
public class Way {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "travel_id")
    private Travel mTravel;
    @ColumnInfo(name = "start_point_id")
    private Point mStartPoint;
    @ColumnInfo(name = "target_point_id")
    private Point mTargetPoint;
    @ColumnInfo(name = "distance")
    private float mDistance;

    public int getId() { return this.mId; }
    public Travel getTravel() { return this.mTravel; }
    public Point getStartPoint() { return this.mStartPoint; }
    public Point getTargetPoint() { return this.mTargetPoint; }
    public float getDistance() { return this.mDistance; }

    public void setId(int id) { this.mId = id; }

    public Way(Travel travel, Point startPoint, Point targetPoint, float distance) {
        this.mTravel = travel;
        this.mStartPoint = startPoint;
        this.mTargetPoint = targetPoint;
        this.mDistance = distance;
    }
    public Way(JSONObject data) throws JSONException {
        this.mTravel = MainActivity.TravelDao.find(data.getString("travel_name"));
        //this.mStartPoint = MainActivity.PointDao.find(data.getString("travel_name"), data.getString("start_point_address_name"));
        //this.mTargetPoint = MainActivity.PointDao.find(data.getString("travel_name"), data.getString("target_point_address_name"));
        this.mDistance = Integer.parseInt(data.getString("distance"));
    }

    @NonNull
    @Override
    public String toString() { return String.valueOf(this.mId); }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "Way")
                .put("id", this.mId)
                .put("travel_name", this.mTravel.getName())
                .put("start_point_address_name", this.mStartPoint.getAddress().getName())
                .put("target_point_address_name", this.mTargetPoint.getAddress().getName())
                .put("distance", this.mDistance);
        return res;
    }
}
