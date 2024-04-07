package ru.sknt.vlasovnetwork.vnsr.models.travels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.daos.TravelStateDao;

@Entity(
        tableName = "travels_travel",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class Travel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name", defaultValue = "")
    private final String mName;
    @ColumnInfo(name = "participants", defaultValue = "")
    private final String mParticipants;
    @ColumnInfo(name = "state_id", defaultValue = "0")
    private int mStateId;
    @Ignore
    private TravelState mState;
    @ColumnInfo(name = "fuel_consumption", defaultValue = "0")
    private float mFuelConsumption = 10f;
    @ColumnInfo(name = "fuel_price", defaultValue = "0")
    private float mFuelPrice;

    public int getId() {
        return this.mId;
    }
    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return this.mName;
    };
    public String getParticipants() {
        return this.mParticipants;
    };
    public int getStateId() {
        return this.mStateId;
    }
    public TravelState getState() {
        return this.mState;
    };
    public void setState(TravelStateDao travelStateDao) {
        this.mState = travelStateDao.find(this.mStateId);
    }
    public float getFuelConsumption() {
        return this.mFuelConsumption;
    }
    public float getFuelPrice() {
       return this.mFuelPrice;
    };

    public Travel(String name, String participants, int stateId, float fuelConsumption, float fuelPrice) {
        this.mName = name;
        this.mParticipants = participants;
        this.mStateId = stateId;
        this.mFuelConsumption = fuelConsumption;
        this.mFuelPrice = fuelPrice;
    }
}

//    CREATE TABLE "travels_travel" (
//        "id" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
//        "name" varchar(255) NOT NULL UNIQUE,
//        "participants" varchar(255) NOT NULL,
//        "state_id" bigint NOT NULL REFERENCES "travels_travelstate" ("id") DEFERRABLE INITIALLY DEFERRED,
//        "fuel_consumption" decimal NOT NULL,
//        "fuel_price" decimal NOT NULL
//        );