package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "kladr_street",
        indices = {
                @Index(
                        value = {"type_id", "name"},
                        unique = true
                )
        }
)
public class Street {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "type_id")
    private final  StreetType mStreetType;
    @ColumnInfo(name = "name")
    private final String mName;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public StreetType getStreetType() { return mStreetType; }

    public void setId(int id) { this.mId = id; }
    public Street (String name, StreetType streetType) {
        this.mName = name;
        this.mStreetType = streetType;
    }

    @NonNull
    @Override
    public String toString() {
        return getStreetType().getShort() + ". " + getName();
    }
}
