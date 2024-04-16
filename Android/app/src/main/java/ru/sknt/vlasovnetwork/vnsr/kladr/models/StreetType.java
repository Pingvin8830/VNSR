package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "kladr_streettype",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                ),
                @Index(
                        value = {"short"},
                        unique = true
                )
        }
)
public class StreetType {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "short")
    private final String mShort;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public String getShort() { return this.mShort; }

    public void setId(int id) { this.mId = id; }

    public StreetType (String name, String mShort) {
        this.mName = name;
        this.mShort = mShort;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
