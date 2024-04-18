package ru.sknt.vlasovnetwork.vnsr.car.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
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
    private final int mAddressId;
    @Ignore
    private Address mAddress;

    public int getId() { return this.mId; }
    public String getCompany() { return this.mCompany; }
    public String getName() { return this.mName; }
    public String getPhone() { return this.mPhone; }
    public int getAddressId() { return this.mAddressId; }
    public Address getAddress() { return this.mAddress; }

    public void setId(int id) { this.mId = id; }
    public void setAddress() {
        this.mAddress = MainActivity.AddressDao.find(this.mAddressId);
        this.mAddress.setRegion();
        this.mAddress.setCity();
        this.mAddress.setStreet();
    }

    public FuelStation (String company, String name, String phone, int addressId) {
        this.mCompany = company;
        this.mName = name;
        this.mPhone = phone;
        this.mAddressId = addressId;
        this.setAddress();
    }
    public FuelStation (String company, String name, String phone, Address address) {
        this.mCompany = company;
        this.mName = name;
        this.mPhone = phone;
        this.mAddress = address;
        this.mAddressId = address.getId();
    }

    @NonNull
    @Override
    public String toString() { return this.getName(); }
}
