package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;

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

    //@ColumnInfo(name = "type_id")
    //private int mTypeId;
    //@Ignore
    @ColumnInfo(name = "type_id")
    private StreetType mStreetType;
    @ColumnInfo(name = "name")
    private final String mName;

    public int getId() { return this.mId; }
    //public int getTypeId() { return this.mTypeId; }
    public String getName() { return this.mName; }
    public StreetType getStreetType() { return mStreetType; }

    public void setId(int id) { this.mId = id; }
    //public void setStreetType() { this.mStreetType = MainActivity.StreetTypeDao.find(mTypeId); }

    /*public Street (int typeId, String name) {
        this.mTypeId = typeId;
        this.mName = name;
    }*/
    /*public Street (StreetType type, String name) {
        this.mStreetType = type;
        this.mTypeId = mStreetType.getId();
        this.mName = name;
    }*/
    public Street (String name, StreetType streetType) {
        this.mName = name;
        this.mStreetType = streetType;
    }

    @Override
    public String toString() {
        return getStreetType().getShort() + ". " + getName();
    }
}
