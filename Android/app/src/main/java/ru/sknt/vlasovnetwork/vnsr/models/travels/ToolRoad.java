package ru.sknt.vlasovnetwork.vnsr.models.travels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (
        tableName = "travels_tollroad"
)
public class ToolRoad {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "travel_id")
    private int mTravelId;
    @Ignore
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
    public int getTravelId() { return this.mTravelId; }
    public String getName() { return this.mName; }
    public String getStart() { return this.mStart; }
    public String getEnd() { return this.mEnd; }
    public float getPrice() { return this.mPrice; }

    public void setId(int id) { this.mId = id; }

    public ToolRoad (int travelId, String name, String start, String end, float price) {
        this.mTravelId = travelId;
        this.mName = name;
        this.mStart = start;
        this.mEnd = end;
        this.mPrice = price;
    }
}
