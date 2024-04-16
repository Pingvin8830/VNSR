package ru.sknt.vlasovnetwork.vnsr.models.car;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

@Entity(
        tableName = "car_fuelstation"
)
public class FuelStation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "company")
    private final String mCompany;
    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "phone")
    private final String mPhone;
    @ColumnInfo(name = "address_id")
    private int mAddressId;
    @Ignore
    private Address mAddress;

    public int getId() { return this.mId; }
    public String getCompany() { return this.mCompany; }
    public String getName() { return this.mCompany; }
    public String getPhone() { return this.mPhone; }
    public int getAddressId() { return this.mAddressId; }

    public void setId(int id) { this.mId = id; }

    public FuelStation (String company, String name, String phone, int addressId) {
        this.mCompany = company;
        this.mName = name;
        this.mPhone = phone;
        this.mAddressId = addressId;
    }
}
