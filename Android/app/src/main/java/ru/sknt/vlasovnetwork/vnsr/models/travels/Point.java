package ru.sknt.vlasovnetwork.vnsr.models.travels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.Address;

@Entity(
        tableName = "travels_point"
)
public class Point {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "travel_id")
    private int mTravelId;
    @Ignore
    private Travel mTravel;
    @ColumnInfo(name = "place_id")
    private int mPlaceId;
    @Ignore
    private Address mPlace;
    @ColumnInfo(name = "datetime")
    private long mDatetime;
    @ColumnInfo(name = "doing")
    private final String mDoing;

    public int getId() { return this.mId; }
    public int getTravelId() { return this.mTravelId; }
    public int getPlaceId() { return this.mPlaceId; }
    public long getDatetime() { return this.mDatetime; }
    public String getDoing() { return this.mDoing; }

    public void setId(int id) { this.mId = id; }

    public Point (int travelId, int placeId, long datetime, String doing) {
        this.mTravelId = travelId;
        this.mPlaceId = placeId;
        this.mDatetime = datetime;
        this.mDoing = doing;

    }

}
