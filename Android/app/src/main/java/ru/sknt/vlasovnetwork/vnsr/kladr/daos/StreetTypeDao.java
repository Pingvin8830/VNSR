package ru.sknt.vlasovnetwork.vnsr.kladr.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

@Dao
public interface StreetTypeDao {
    @Query("SELECT * FROM kladr_streettype ORDER BY name")
    List<StreetType> getAll();
    @Query("SELECT name FROM kladr_streettype ORDER BY name")
    List<String> getAllNames();

    @Insert
    void create(StreetType streetType);

    @Query("SELECT * FROM kladr_streettype WHERE id=:id")
    StreetType find(int id);
    @Query("SELECT * FROM kladr_streettype WHERE name=:name")
    StreetType find(String name);

    @Delete
    void delete(StreetType streetType);
    @Query("DELETE FROM kladr_streettype")
    void truncate();
}
