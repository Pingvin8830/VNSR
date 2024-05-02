package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
import ru.sknt.vlasovnetwork.vnsr.MainActivity;

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
    @Ignore
    public static final String[][] STATES = {
            {"U", "Неизвестно"},
            {"P", "Планирование"},
            {"R", "Выполнение"},
            {"S", "Завершена"},
            {"C", "Отменена"}
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "participants")
    private final String mParticipants;
    @ColumnInfo(name = "state")
    private String mState;
    @ColumnInfo(name = "fuel_consumption")
    private float mFuelConsumption = 10f;
    @ColumnInfo(name = "fuel_price")
    private float mFuelPrice;
    @ColumnInfo(name = "start_datetime")
    private FormatedDate mStartDateTime;
    @ColumnInfo(name="end_datetime")
    private FormatedDate mEndDateTime;

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
    public String getState() {
        return this.mState;
    };
    public String getStateText() {
        for (String[] stateList : Travel.STATES) {
            if (this.mState.equals(stateList[0])) { return stateList[1]; }
        }
        return "";
    }
    public float getFuelConsumption() {
        return this.mFuelConsumption;
    }
    public float getFuelPrice() {
       return this.mFuelPrice;
    };
    public FormatedDate getStartDateTime() { return this.mStartDateTime; }
    public FormatedDate getEndDateTime() { return this.mEndDateTime; }
    public List<Point> getPoints() { return MainActivity.PointDao.getTravelPoints(this.getStartDateTime().getTime(), this.getEndDateTime().getTime()); }

    public Travel(String name, String participants, String state, float fuelConsumption, float fuelPrice, FormatedDate startDateTime, FormatedDate endDateTime) {
        this.mName = name;
        this.mParticipants = participants;
        this.mState = state;
        this.mFuelConsumption = fuelConsumption;
        this.mFuelPrice = fuelPrice;
        this.mStartDateTime = startDateTime;
        this.mEndDateTime = endDateTime;
    }
    public Travel(JSONObject data) throws JSONException {
        JSONObject startDateTimeJson = new JSONObject(data.getString("start_datetime"));
        JSONObject endDateTimeJson = new JSONObject(data.getString("end_datetime"));

        this.mName = data.getString("name");
        this.mParticipants = data.getString("participants");
        this.mState = data.getString("travel_state_code");
        this.mFuelConsumption = Float.parseFloat(data.getString("fuel_consumption"));
        this.mFuelPrice = Float.parseFloat(data.getString("fuel_price"));
        this.mStartDateTime = new FormatedDate(startDateTimeJson);
        this.mEndDateTime = new FormatedDate(endDateTimeJson);
    }

    @NonNull
    @Override
    public String toString() { return this.mName; }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        JSONObject startDateTimeJson = new JSONObject();
        JSONObject endDateTimeJson = new JSONObject();
        startDateTimeJson
                .put("year", this.getStartDateTime().getYear())
                .put("month", this.getStartDateTime().getMonth()+1)
                .put("day", this.getStartDateTime().getDate())
                .put("hour", this.getStartDateTime().getHours())
                .put("minute", this.getStartDateTime().getMinutes());
        endDateTimeJson
                .put("year", this.getEndDateTime().getYear())
                .put("month", this.getEndDateTime().getMonth()+1)
                .put("day", this.getEndDateTime().getDate())
                .put("hour", this.getEndDateTime().getHours())
                .put("minute", this.getEndDateTime().getMinutes());
        res
                .put("object", "Travel")
                .put("id",                this.getId())
                .put("name",              this.getName())
                .put("participants",      this.getParticipants())
                .put("travel_state_code", this.getState())
                .put("fuel_consumption",  this.getFuelConsumption())
                .put("fuel_price",        this.getFuelPrice())
                .put("start_datetime",    startDateTimeJson)
                .put("end_datetime",      endDateTimeJson);
        return res;
    }
}
