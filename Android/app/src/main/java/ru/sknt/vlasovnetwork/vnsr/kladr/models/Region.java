package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "kladr_region",
        indices = {
                @Index(
                        value = {"code"},
                        unique = true
                ),
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class Region {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "code")
    private final String mCode;
    @ColumnInfo(name = "name")
    private final String mName;

    public int getId() { return this.mId; }
    public String getCode() { return this.mCode; }
    public String getName() { return this.mName; }

    public void setId(int id) { this.mId = id; }

    public Region (String code, String name) {
        this.mCode = code;
        this.mName = name;
    }

    @NonNull
    @Override
    public String toString() {
        return getCode() + " - " + getName();
    }
}
