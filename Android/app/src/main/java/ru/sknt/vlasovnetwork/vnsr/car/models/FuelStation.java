package ru.sknt.vlasovnetwork.vnsr.car.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

@Entity(
        tableName = "car_fuelstation",
        indices = {
                @Index(
                        value = {"company", "number"},
                        unique = true
                )
        }
)
public class FuelStation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "company")
    private final String mCompany;
    @ColumnInfo(name="number")
    private final String mNumber;
    @ColumnInfo(name = "phone")
    private final String mPhone;
    @ColumnInfo(name = "address_id")
    private final Address mAddress;

    public int getId() { return this.mId; }
    public String getCompany() { return this.mCompany; }
    public String getNumber() { return this.mNumber; }
    public String getPhone() { return this.mPhone; }
    public Address getAddress() { return this.mAddress; }

    public void setId(int id) { this.mId = id; }

    public FuelStation (String company, String number, String phone, Address address) {
        this.mCompany = company;
        this.mNumber = number;
        this.mPhone = phone;
        this.mAddress = address;
    }
    public FuelStation (JSONObject data) throws JSONException {
        this.mCompany = data.getString("company");
        this.mNumber = data.getString("number");
        this.mPhone = data.getString("phone");
        this.mAddress = MainActivity.AddressDao.find(data.getString("address_name"));
    }

    @NonNull
    @Override
    public String toString() { return this.mCompany + " " + this.mNumber; }
    public JSONObject toJson() throws JSONException {
        JSONObject res = new JSONObject();
        res
                .put("object", "FuelStation")
                .put("id", this.getId())
                .put("company", this.getCompany())
                .put("number", this.getNumber())
                .put("phone", this.getPhone())
                .put("address_name", this.getAddress().getName());
        return res;
    }
}
