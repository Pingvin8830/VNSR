package ru.sknt.vlasovnetwork.vnsr.kladr.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

@Dao
public interface CityTypeDao {
    @Query("SELECT * FROM kladr_citytype ORDER BY name")
    List<CityType> getAll();
    @Query("SELECT name FROM kladr_citytype ORDER BY name")
    List<String> getAllNames();

    @Insert
    void create(CityType cityType);

    @Query("SELECT * FROM kladr_citytype WHERE id=:id")
    CityType find(int id);
    @Query("SELECT * FROM kladr_citytype WHERE name=:name")
    CityType find(String name);
    @Query("SELECT count(id) FROM kladr_citytype")
    int getCount();

    @Delete
    void delete(CityType cityType);
    @Query("DELETE FROM kladr_citytype")
    void truncate();
}
