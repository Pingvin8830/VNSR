package ru.sknt.vlasovnetwork.vnsr;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static FormatedDate timestampToDate(Long value) {
        return value == null ? null : new FormatedDate(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
