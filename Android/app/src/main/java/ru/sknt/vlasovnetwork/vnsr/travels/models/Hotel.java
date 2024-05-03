package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

@Entity(
        tableName = "travels_hotel",
        indices = {
                @Index(
                        value = {"arrival", "departure"},
                        unique = true
                )
        }
)
public class Hotel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "address_id")
    private Address mAddress;
    @ColumnInfo(name = "arrival")
    private FormatedDate mArrival;
    @ColumnInfo(name = "departure")
    private FormatedDate mDeparture;
    @ColumnInfo(name = "cost")
    private float mCost;
    @ColumnInfo(name = "state")
    private final String mState;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public Address getAddress() { return this.mAddress; }
    public FormatedDate getArrival() { return this.mArrival; }
    public FormatedDate getDeparture() { return this.mDeparture; }
    public float getCost() { return this.mCost; }
    public String getState() { return this.mState; }

    public void setId(int id) { this.mId = id; }

    public Hotel (String name, Address address, FormatedDate arrival, FormatedDate departure, float cost, String state) {
        this.mName = name;
        this.mAddress = address;
        this.mArrival = arrival;
        this.mDeparture = departure;
        this.mCost = cost;
        this.mState = state;
    }
    public Hotel (JSONObject data) throws JSONException {
        JSONObject addressJson = new JSONObject(data.getString("address"));

        this.mName = data.getString("name");
        this.mAddress = MainActivity.AddressDao.find(addressJson.getString("name"));
        this.mArrival = new FormatedDate(new JSONObject(data.getString("arrival")));
        this.mDeparture = new FormatedDate(new JSONObject(data.getString("departure")));
        this.mCost = Float.parseFloat(data.getString("cost"));
        this.mState = data.getString("state");
    }

    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        JSONObject addressJson = new JSONObject();
        JSONObject arrivalJson = new JSONObject();
        JSONObject departureJson = new JSONObject();

        addressJson.put("name", this.getAddress().getName());
        arrivalJson
                .put("year", this.getArrival().getYear())
                .put("month", this.getArrival().getMonth()+1)
                .put("day", this.getArrival().getDate())
                .put("hour", this.getArrival().getHours())
                .put("minute", this.getArrival().getMinutes());
        departureJson
                .put("year", this.getDeparture().getYear())
                .put("month", this.getDeparture().getMonth()+1)
                .put("day", this.getDeparture().getDate())
                .put("hour", this.getDeparture().getHours())
                .put("minute", this.getDeparture().getMinutes());

        res
                .put("object", "Hotel")
                .put("id", this.getId())
                .put("name", this.getName())
                .put("address", addressJson)
                .put("arrival", arrivalJson)
                .put("departure", departureJson)
                .put("cost", this.getCost())
                .put("state", this.getState());
        return res;
    }
}
