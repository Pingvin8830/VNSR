package ru.sknt.vlasovnetwork.vnsr.models.travels;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TravelStateDao {
    @Query("SELECT * FROM travels_travelstate ORDER BY name")
    List<TravelState> getAll();

    @Insert
    void create(TravelState travelState);

    @Query("SELECT * FROM travels_travelstate WHERE id=:id")
    TravelState find(int id);
    @Query("SELECT * FROM travels_travelstate WHERE name=:name")
    TravelState find(String name);
    @Delete
    void delete(TravelState travelState);

    @Query("DELETE FROM travels_travelstate")
    void truncate();
}
