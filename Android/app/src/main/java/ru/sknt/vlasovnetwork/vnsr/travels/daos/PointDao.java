package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;

@Dao
public interface PointDao {
    @Query("SELECT * FROM travels_point")
    List<Point> getAll();
    @Query("SELECT count(id) FROM travels_point")
    int getCount();

    @Insert
    void create(Point point);

    @Query("SELECT * FROM travels_point WHERE id=:id")
    Point find(int id);
    @Query("SELECT * FROM travels_point WHERE arrival_datetime=:arrivalDateTime AND departure_datetime=:departureDateTime")
    Point find(long arrivalDateTime, long departureDateTime);

    @Query(
            "SELECT * " +
                    "FROM travels_point " +
                    "WHERE (departure_datetime>=:travelStartDateTime AND arrival_datetime<=:travelEndDateTime) " +
                    "OR (departure_datetime>=:travelStartDateTime AND arrival_datetime IS NULL)" +
                    "OR (departure_datetime IS NULL ANd arrival_datetime<=:travelEndDateTime)"
    )
    public List<Point> getTravelPoints(long travelStartDateTime, long travelEndDateTime);

    @Delete
    void delete(Point point);
    @Query("DELETE FROM travels_point")
    void truncate();
}
