package ru.sknt.vlasovnetwork.vnsr.kladr.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;

@Dao
public interface CityDao {
    @Query("SELECT * FROM kladr_city ORDER BY name")
    List<City> getAll();

    @Insert
    void create(City city);

    @Query("SELECT * FROM kladr_city WHERE id=:id")
    City find(int id);
    @Query("SELECT * FROM kladr_city WHERE name=:name")
    City find(String name);
    @Query("SELECT count(id) FROM kladr_city")
    int getCount();

    @Delete
    void delete(City city);
    @Query("DELETE FROM kladr_city")
    void truncate();
}
