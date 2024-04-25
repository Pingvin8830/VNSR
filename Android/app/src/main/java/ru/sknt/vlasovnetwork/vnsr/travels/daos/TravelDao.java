package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;


@Dao
public interface TravelDao {
    @Query("SELECT * FROM travels_travel")
    List<Travel> getAll();

    @Insert
    void create(Travel travel);

    @Query("SELECT * FROM travels_travel WHERE id=:id")
    Travel find(int id);
    @Query("SELECT * FROM travels_travel WHERE name=:name")
    Travel find(String name);

    @Delete
    void delete(Travel travel);

    @Query("DELETE FROM travels_travel")
    void truncate();
}
