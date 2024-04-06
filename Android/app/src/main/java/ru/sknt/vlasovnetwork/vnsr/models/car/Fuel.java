package ru.sknt.vlasovnetwork.vnsr.models.car;

public class Fuel {
    private final String mName;
    private final FuelStation mFuelStation;

    public Fuel(String name, FuelStation fuelStation) {
        this.mName = name;
        this.mFuelStation = fuelStation;
    }
}
