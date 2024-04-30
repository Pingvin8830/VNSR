package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;

@Dao
public interface TollRoadDao {
    @Query("SELECT * FROM travels_tollroad")
    List<TollRoad> getAll();

    @Insert
    void create(TollRoad tollRoad);

    @Query("SELECT * FROM travels_tollroad WHERE id=:id")
    TollRoad find(int id);

    @Delete
    void delete(TollRoad tollRoad);
    @Query("DELETE FROM travels_tollroad")
    void truncate();
}
