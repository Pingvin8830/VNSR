package ru.sknt.vlasovnetwork.vnsr.travels.models;

import androidx.annotation.NonNull;
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
        tableName = "travels_point",
        indices = {
                @Index(
                        value = {"arrival_datetime", "departure_datetime"},
                        unique = true
                )
        }
)
public class Point {
    @Ignore
    public static final String[][] DOINGS = {
            {"U", "Неизвестно"},
            {"A", "Отправление"},
            {"N", "Ночёвка"},
            {"S", "Перерыв"},
            {"R", "Заправка"},
            {"D", "Прибытие"}
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "address_id")
    private Address mAddress;
    @ColumnInfo(name = "arrival_datetime")
    private FormatedDate mArrivalDateTime;
    @ColumnInfo(name = "departure_datetime")
    private FormatedDate mDepartureDateTime;
    @ColumnInfo(name = "doing")
    private final String mDoing;
    @ColumnInfo(name = "odometer")
    private int mOdometer;

    public int getId() { return this.mId; }
    public Address getAddress() { return this.mAddress; }
    public FormatedDate getArrivalDateTime() { return this.mArrivalDateTime; }
    public FormatedDate getDepartureDateTime() { return this.mDepartureDateTime; }
    public String getDoing() { return this.mDoing; }
    public String getDoingText() {
        for (String[] doingList : Point.DOINGS) {
            if (this.mDoing.equals(doingList[0])) { return doingList[1]; }
        }
        return "";
    }
    public int getOdometer() { return this.mOdometer; }

    public void setId(int id) { this.mId = id; }

    public Point (Address address, FormatedDate arrivalDateTime, FormatedDate departureDateTime, String doing, int odometer) {
        this.mAddress = address;
        if (arrivalDateTime == null) { this.mArrivalDateTime = new FormatedDate(0L); }
        else { this.mArrivalDateTime = arrivalDateTime; }
        if (departureDateTime == null) { this.mDepartureDateTime = new FormatedDate(0L); }
        else { this.mDepartureDateTime = departureDateTime; }
        this.mDoing = doing;
        this.mOdometer = odometer;
    }
    public Point (JSONObject data) throws JSONException {
        JSONObject addressJson = new JSONObject(data.getString("address"));
        JSONObject arrivalDateTimeJson;
        JSONObject departureDateTimeJson;

        try { arrivalDateTimeJson = new JSONObject(data.getString("arrival_datetime")); }
        catch (JSONException e) { arrivalDateTimeJson = null; }
        try { departureDateTimeJson = new JSONObject(data.getString("departure_datetime")); }
        catch (JSONException e) { departureDateTimeJson = null; }

        this.mAddress = MainActivity.AddressDao.find(addressJson.getString("name"));

        if (arrivalDateTimeJson == null) { this.mArrivalDateTime = new FormatedDate(0L); }
        else { this.mArrivalDateTime = new FormatedDate(arrivalDateTimeJson); }
        if (departureDateTimeJson == null) { this.mDepartureDateTime = new FormatedDate(0L); }
        else { this.mDepartureDateTime = new FormatedDate(departureDateTimeJson); }
        this.mDoing = data.getString("doing_code");
        this.mOdometer = data.getInt("odometer");
    }

    @NonNull
    @Override
    public String toString() { return this.mAddress.getName() + " " + this.mDoing; }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        JSONObject addressJson = new JSONObject();
        JSONObject arrivalDateTimeJson = new JSONObject();
        JSONObject departureDateTimeJson = new JSONObject();
        addressJson.put("name", this.getAddress().getName());
        if (this.getArrivalDateTime().getTime() != 0L) {
            arrivalDateTimeJson
                    .put("year", this.getArrivalDateTime().getYear())
                    .put("month", this.getArrivalDateTime().getMonth() + 1)
                    .put("day", this.getArrivalDateTime().getDate())
                    .put("hour", this.getArrivalDateTime().getHours())
                    .put("minute", this.getArrivalDateTime().getMinutes());
        }
        if (this.getDepartureDateTime().getTime() != 0L) {
            departureDateTimeJson
                    .put("year", this.getDepartureDateTime().getYear())
                    .put("month", this.getDepartureDateTime().getMonth() + 1)
                    .put("day", this.getDepartureDateTime().getDate())
                    .put("hour", this.getDepartureDateTime().getHours())
                    .put("minute", this.getDepartureDateTime().getMinutes());
        }
        res
                .put("object", "Point")
                .put("id", this.getId())
                .put("address", addressJson)
                .put("arrival_datetime", arrivalDateTimeJson)
                .put("departure_datetime", departureDateTimeJson)
                .put("doing", this.getDoing())
                .put("odometer", this.getOdometer());
        return res;
    }
}
