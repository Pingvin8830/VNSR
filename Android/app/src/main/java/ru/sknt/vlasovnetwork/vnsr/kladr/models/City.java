package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;

@Entity(
        tableName = "kladr_city",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class City {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "type_id")
    private int mTypeId;
    @Ignore
    private CityType mCityType;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public int getTypeId() { return this.mTypeId; }
    public CityType getCityType() { return this.mCityType; }

    public void setId(int id) { this.mId = id; }
    public void setCityType() { this.mCityType = MainActivity.CityTypeDao.find(mTypeId); }


    public City (String name, int typeId) {
        this.mName = name;
        this.mTypeId = typeId;
    }
    public City (String name, CityType type) {
        this.mName = name;
        this.mCityType = type;
        this.mTypeId = type.getId();
    }

    @Override
    public String toString() {
        return getCityType().getShort() + ". " + getName();
    }
}
