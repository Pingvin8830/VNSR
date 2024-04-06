package ru.sknt.vlasovnetwork.vnsr.models.car;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.Address;

public class FuelStation {
    private final String mCompany;
    private final String mName;
    private final String mPhone;
    private final Address mAddress;

    public FuelStation(String company, String name, String phone, Address address) {
        this.mCompany = company;
        this.mName = name;
        this.mPhone = phone;
        this.mAddress = address;
    }
}
