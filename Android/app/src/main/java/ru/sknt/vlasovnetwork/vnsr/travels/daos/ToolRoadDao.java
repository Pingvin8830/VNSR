package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.ToolRoad;

@Dao
public interface ToolRoadDao {
    @Query("SELECT * FROM travels_tollroad")
    List<ToolRoad> getAll();

    @Insert
    void create(ToolRoad toolRoad);

    @Query("SELECT * FROM travels_tollroad WHERE id=:id")
    ToolRoad find(int id);

    @Delete
    void delete(ToolRoad toolRoad);
    @Query("DELETE FROM travels_tollroad")
    void truncate();
}
