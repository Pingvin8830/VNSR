package ru.sknt.vlasovnetwork.vnsr.models.travels;

public class ToolRoad {
    private final Travel mTravel;
    private final String mName;
    private final String mStart;
    private final String mEnd;
    private float mPrice;

    public ToolRoad (Travel travel, String name, String start, String end, float price) {
        this.mTravel = travel;
        this.mName = name;
        this.mStart = start;
        this.mEnd = end;
        this.mPrice = price;
    }
}
