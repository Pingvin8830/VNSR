package ru.sknt.vlasovnetwork.vnsr.daos.kladr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.CityType;

@Dao
public interface CityTypeDao {
    @Query("SELECT * FROM kladr_citytype")
    List<CityType> getAll();

    @Insert
    void create(CityType cityType);

    @Query("SELECT * FROM kladr_citytype WHERE id=:id")
    CityType find(int id);
    @Query("SELECT * FROM kladr_citytype WHERE name=:name")
    CityType find(String name);

    @Delete
    void delete(CityType cityType);
    @Query("DELETE FROM kladr_citytype")
    void truncate();
}
