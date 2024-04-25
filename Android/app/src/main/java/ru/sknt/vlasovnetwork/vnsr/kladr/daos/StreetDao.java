package ru.sknt.vlasovnetwork.vnsr.kladr.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

@Dao
public interface StreetDao {
    @Query("SELECT * FROM kladr_street ORDER BY name")
    List<Street> getAll();

    @Insert
    void create(Street street);

    @Query("SELECT * FROM kladr_street WHERE id=:id")
    Street find(int id);
    @Query("SELECT * FROM kladr_street WHERE type_id=:typeId AND name=:name")
    Street find(int typeId, String name);
    @Query("SELECT s.* FROM kladr_street s JOIN kladr_streettype st ON s.type_id=st.id WHERE s.name=:name AND st.name=:typeName")
    Street find(String name, String typeName);
    @Query("SELECt count(id) FROM kladr_street")
    int getCount();

    @Delete
    void delete(Street street);
    @Query("DELETE FROM kladr_street")
    void truncate();
}
