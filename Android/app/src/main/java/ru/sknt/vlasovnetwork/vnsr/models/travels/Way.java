package ru.sknt.vlasovnetwork.vnsr.models.travels;

public class Way {
    private final Travel mTravel;
    private final Point mStartPoint;
    private final Point mTargetPoint;
    private float mDistance;

    public Way (Travel travel, Point startPoint, Point targetPoint, float distance) {
        this.mTravel = travel;
        this.mStartPoint = startPoint;
        this.mTargetPoint = targetPoint;
        this.mDistance = distance;
    }
}
