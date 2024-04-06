package ru.sknt.vlasovnetwork.vnsr.models.kladr;

public class Street {
    private final StreetType mType;
    private final String mName;

    public Street (StreetType type, String name) {
        this.mType = type;
        this.mName = name;
    }
}
