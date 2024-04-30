package ru.sknt.vlasovnetwork.vnsr;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class FormatedDate extends Date {
    public FormatedDate(Long value) {
        setTime(value);
    }

    public FormatedDate(int year, int month, int day, int hour, int minute) {
        setTime(0L);
        setYear(year);
        setMonth(month);
        setDate(day);
        setHours(hour);
        setMinutes(minute);
        setSeconds(0);
    }
    public FormatedDate(JSONObject data) throws JSONException {
        setTime(0L);
        setYear(data.getInt("year"));
        setMonth(data.getInt("month")-1);
        setDate(data.getInt("day"));
        setHours(data.getInt("hour"));
        setMinutes(data.getInt("minute"));
        setSeconds(0);
    }

    @NonNull
    @Override
    public String toString() {
        String text = "";
        if (getDate() < 10) { text += "0"; }
        text += getDate() + "-";
        if (getMonth() < 9) { text += "0"; }
        text += (getMonth() + 1) + "-" + getYear() + " ";
        if (getHours() < 10) { text += "0"; }
        text += getHours() + ":";
        if (getMinutes() < 10) { text += "0"; }
        text += getMinutes() + "";
        return text;
    }
}
