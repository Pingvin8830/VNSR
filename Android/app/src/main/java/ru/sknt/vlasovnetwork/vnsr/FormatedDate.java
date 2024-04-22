package ru.sknt.vlasovnetwork.vnsr;

import androidx.annotation.NonNull;

import java.util.Date;

public class FormatedDate extends Date {
    public FormatedDate(Long value) {
        setTime(value);
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
