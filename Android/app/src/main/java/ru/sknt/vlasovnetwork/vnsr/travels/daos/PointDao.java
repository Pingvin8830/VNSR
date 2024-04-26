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
    @Query("SELECT * FROM travels_point WHERE datetime=:datetime")
    Point find(long datetime);
    @Query("SELECT * FROM travels_point WHERE travel_id=:travelId")
    List<Point> filter(int travelId);

    @Delete
    void delete(Point point);
    @Query("DELETE FROM travels_point")
    void truncate();
}
