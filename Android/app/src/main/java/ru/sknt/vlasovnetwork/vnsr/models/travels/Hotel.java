package ru.sknt.vlasovnetwork.vnsr.models.travels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.Address;

@Entity(
        tableName = "travels_hotel"
)
public class Hotel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "travel_id")
    private int mTravelId;
    @Ignore
    private Travel mTravel;
    @ColumnInfo(name = "address_id")
    private int mAddressId;
    @Ignore
    private Address mAddress;
    @ColumnInfo(name = "arrival")
    private long mArrival;
    @ColumnInfo(name = "departure")
    private long mDeparture;
    @ColumnInfo(name = "cost")
    private float mCost;
    @ColumnInfo(name = "state")
    private final String mState;

    public int getId() { return this.mId; }
    public int getTravelId() { return this.mTravelId; }
    public int getAddressId() { return this.mAddressId; }
    public long getArrival() { return this.mArrival; }
    public long getDeparture() { return this.mDeparture; }
    public float getCost() { return this.mCost; }
    public String getState() { return this.mState; }

    public void setId(int id) { this.mId = id; }

    public Hotel (int travelId, int addressId, long arrival, long departure, float cost, String state) {
        this.mTravelId = travelId;
        this.mAddressId = addressId;
        this.mArrival = arrival;
        this.mDeparture = departure;
        this.mCost = cost;
        this.mState = state;
    }
}
