package ru.sknt.vlasovnetwork.vnsr.sync.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "sync_task",
        indices = {
                @Index(
                        value = {"object_name", "object_id", "field"},
                        unique = true
                )
        }
)
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "object_name")
    private final String mObjectName;
    @ColumnInfo(name = "object_id")
    private final int mObjectId;
    @ColumnInfo(name = "field")
    private final String mField;
    @ColumnInfo(name = "value")
    private final String mValue;

    public int getId() { return this.mId; }
    public String getObjectName() { return this.mObjectName; }
    public int getObjectId() { return this.mObjectId; }
    public String getField() { return this.mField; }
    public String getValue() { return this.mValue; }

    public void setId(int id) { this.mId = id; }

    public Task (String objectName, int objectId, String field, String value) {
        this.mObjectName = objectName;
        this.mObjectId = objectId;
        this.mField = field;
        this.mValue = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "Object: " + this.getObjectName()
                + ", id: " + this.getObjectId()
                + ", field: " + this.getField()
                + ", value: " + this.getValue();
    }
}
