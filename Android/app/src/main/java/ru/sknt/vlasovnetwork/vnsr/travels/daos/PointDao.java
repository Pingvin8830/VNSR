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

    @Insert
    void create(Point point);

    @Query("SELECT * FROM travels_point WHERE id=:id")
    Point find(int id);

    @Delete
    void delete(Point point);
    @Query("DELETE FROM travels_point")
    void truncate();
}
