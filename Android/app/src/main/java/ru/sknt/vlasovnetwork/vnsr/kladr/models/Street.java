package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

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
    private int mTypeId;
    @Ignore
    private StreetType mType;
    @ColumnInfo(name = "name")
    private final String mName;

    public int getId() { return this.mId; }
    public int getTypeId() { return this.mTypeId; }
    public String getName() { return this.mName; }
    public StreetType getType(StreetTypeDao dao) { return dao.find(mTypeId); }

    public void setId(int id) { this.mId = id; }

    public Street (int typeId, String name) {
        this.mTypeId = typeId;
        this.mName = name;
    }
}