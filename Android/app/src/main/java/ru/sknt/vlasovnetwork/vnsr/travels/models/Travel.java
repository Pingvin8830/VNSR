package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.TravelStateDao;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TravelState;

@Entity(
        tableName = "travels_travel",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class Travel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "participants")
    private final String mParticipants;
    @ColumnInfo(name = "state_id")
    private TravelState mTravelState;
    @ColumnInfo(name = "fuel_consumption")
    private float mFuelConsumption = 10f;
    @ColumnInfo(name = "fuel_price")
    private float mFuelPrice;

    public int getId() {
        return this.mId;
    }
    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return this.mName;
    };
    public String getParticipants() {
        return this.mParticipants;
    };
    public TravelState getState() {
        return this.mTravelState;
    };
    public TravelState getTravelState() {
        return this.mTravelState;
    };
    public float getFuelConsumption() {
        return this.mFuelConsumption;
    }
    public float getFuelPrice() {
       return this.mFuelPrice;
    };

    public Travel(String name, String participants, TravelState travelState, float fuelConsumption, float fuelPrice) {
        this.mName = name;
        this.mParticipants = participants;
        this.mTravelState = travelState;
        this.mFuelConsumption = fuelConsumption;
        this.mFuelPrice = fuelPrice;
    }
    public Travel(JSONObject data) throws JSONException {
        this.mName = data.getString("name");
        this.mParticipants = data.getString("participants");
        this.mTravelState = MainActivity.TravelStateDao.find(data.getString("travel_state_name"));
        this.mFuelConsumption = Float.parseFloat(data.getString("fuel_consumption"));
        this.mFuelPrice = Float.parseFloat(data.getString("fuel_price"));
    }

    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "Travel")
                .put("id", this.getId())
                .put("name", this.getName())
                .put("participants", this.getParticipants())
                .put("travel_state_name", this.getTravelState().getName())
                .put("fuel_consumption", this.getFuelConsumption())
                .put("fuel_price", this.getFuelPrice());
        return res;
    }
}
