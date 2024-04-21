package ru.sknt.vlasovnetwork.vnsr.models.travels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "travels_travelstate",
        indices = {
                @Index(
                       value={"name"},
                        unique = true
                )})
public class TravelState {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private final String mName;

    public void setId(int id) {
        this.mId = id;
    }
    public int getId() {
        return this.mId;
    }
    public String getName() {
        return this.mName;
    }

    public TravelState(String name) {
        this.mName = name;
    }


}