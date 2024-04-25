package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.TravelState;

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
    @Query("SELECT count(id) FROM travels_travelstate")
    int getCount();

    @Delete
    void delete(TravelState travelState);

    @Query("DELETE FROM travels_travelstate")
    void truncate();
}
