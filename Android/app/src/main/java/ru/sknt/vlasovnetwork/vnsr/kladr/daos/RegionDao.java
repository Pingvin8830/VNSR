package ru.sknt.vlasovnetwork.vnsr.kladr.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

@Dao
public interface RegionDao {
    @Query("SELECT * FROM kladr_region ORDER BY code")
    List<Region> getAll();

    @Insert
    void create(Region region);

    @Query("SELECT * FROM kladr_region WHERE id=:id")
    Region find(int id);
    @Query("SELECT * FROM kladr_region WHERE code=:code")
    Region find(String code);
    @Query("SELECT * FROM kladr_region WHERE name=:name")
    Region findByName(String name);
    @Query("SELECT count(id) FROM kladr_region")
    int getCount();

    @Delete
    void delete(Region region);
    @Query("DELETE FROM kladr_region")
    void truncate();
}
