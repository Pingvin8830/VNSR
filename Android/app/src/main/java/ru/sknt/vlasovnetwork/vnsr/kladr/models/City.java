package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;

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
    private CityType mType;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public int getTypeId() { return this.mTypeId; }
    public CityType getType(CityTypeDao dao) { return dao.find(this.mTypeId); }

    public void setId(int id) { this.mId = id; }

    public City (String name, int typeId) {
        this.mName = name;
        this.mTypeId = typeId;
    }
}
