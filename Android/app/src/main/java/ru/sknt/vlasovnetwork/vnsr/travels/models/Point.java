package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

@Entity(
        tableName = "travels_point",
        indices = {
                @Index(
                        value = {"datetime"},
                        unique = true
                )
        }
)
public class Point {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "travel_id")
    private Travel mTravel;
    @ColumnInfo(name = "address_id")
    private Address mAddress;
    @ColumnInfo(name = "datetime")
    private FormatedDate mDateTime;
    @ColumnInfo(name = "doing")
    private final String mDoing;
    @ColumnInfo(name = "odometer")
    private int mOdometer;

    public int getId() { return this.mId; }
    public Travel getTravel() { return this.mTravel; }
    public FormatedDate getDateTime() { return this.mDateTime; }
    public String getDoing() { return this.mDoing; }
    public int getOdometer() { return this.mOdometer; }
    public Address getAddress() { return this.mAddress; }

    public void setId(int id) { this.mId = id; }

    public Point (Travel travel, Address address, FormatedDate dateTime, String doing, int odometer) {
        this.mTravel = travel;
        this.mAddress = address;
        this.mDateTime = dateTime;
        this.mDoing = doing;
        this.mOdometer = odometer;
    }
    public Point (JSONObject data) throws JSONException {
        this.mTravel = MainActivity.TravelDao.find(data.getString("travel_name"));
        this.mAddress = MainActivity.AddressDao.find(data.getString("address_name"));
        this.mDateTime = new FormatedDate(0L);
        this.mDateTime.setYear(Integer.parseInt(data.getString("year")));
        this.mDateTime.setMonth(Integer.parseInt(data.getString("month"))-1);
        this.mDateTime.setDate(Integer.parseInt(data.getString("day")));
        this.mDateTime.setHours(Integer.parseInt(data.getString("hour")));
        this.mDateTime.setMinutes(Integer.parseInt(data.getString("minute")));
        this.mDateTime.setSeconds(0);
        this.mDoing = data.getString("doing");
        this.mOdometer = Integer.parseInt(data.getString("odometer"));
    }

    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "Point")
                .put("id", this.mId)
                .put("travel_name", this.mTravel.getName())
                .put("address_name", this.mAddress.getName())
                .put("datetime", this.mDateTime.toString())
                .put("doing", this.mDoing)
                .put("odometer", this.mOdometer);
        return res;
    }
}
