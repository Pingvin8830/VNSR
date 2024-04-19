package ru.sknt.vlasovnetwork.vnsr.car.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "car_fuel",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class Fuel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private final String mName;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }

    public void setId(int id) { this.mId = id; }

    public Fuel (String name) { this.mName = name; }
    @NonNull
    @Override
    public String toString() { return this.mName; }
}
