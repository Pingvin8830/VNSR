package ru.sknt.vlasovnetwork.vnsr.models.kladr;

public class Address {
    private final String mName;
    private final Region mRegion;
    private final City mCity;
    private final Street mStreet;
    private final String mHouse;
    private final String mBuilding;
    private int mFlat;

    public Address (String name, Region region, City city, Street street, String house, String building, int flat) {
        this.mName = name;
        this.mRegion = region;
        this.mCity = city;
        this.mStreet = street;
        this.mHouse = house;
        this.mBuilding = building;
        this.mFlat = flat;
    }
}
