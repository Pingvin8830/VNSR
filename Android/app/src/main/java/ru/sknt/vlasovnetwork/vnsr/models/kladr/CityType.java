package ru.sknt.vlasovnetwork.vnsr.models.kladr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "kladr_citytype",
        indices = {
                @Index(
                        value = {"short"},
                        unique = true
                ),
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class CityType {
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

    public CityType (String name, String mShort) {
        this.mName = name;
        this.mShort = mShort;
    }
}