package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

@Entity(
        tableName = "travels_way"
)
public class Way {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "travel_id")
    private int mTravelId;
    @Ignore
    private Travel mTravel;
    @ColumnInfo(name = "start_point_id")
    private int mStartPointId;
    @Ignore
    private Point mStartPoint;
    @ColumnInfo(name = "target_point_id")
    private int mTargetPointId;
    @Ignore
    private Point mTargetPoint;
    @ColumnInfo(name = "distance")
    private float mDistance;

    public int getId() { return this.mId; }
    public int getTravelId() { return this.mTravelId; }
    public int getStartPointId() { return this.mStartPointId; }
    public int getTargetPointId() { return this.mTargetPointId; }
    public float getDistance() { return this.mDistance; }

    public void setId(int id) { this.mId = id; }

    public Way(int travelId, int startPointId, int targetPointId, float distance) {
        this.mTravelId = travelId;
        this.mStartPointId = startPointId;
        this.mTargetPointId = targetPointId;
        this.mDistance = distance;
    }
}
