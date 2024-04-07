package ru.sknt.vlasovnetwork.vnsr.daos.kladr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.Street;

@Dao
public interface StreetDao {
    @Query("SELECT * FROM kladr_street")
    List<Street> getAll();

    @Insert
    void create(Street street);

    @Query("SELECT * FROM kladr_street WHERE id=:id")
    Street find(int id);
    @Query("SELECT * FROM kladr_street WHERE type_id=:typeId AND name=:name")
    Street find(int typeId, String name);

    @Delete
    void delete(Street street);
    @Query("DELETE FROM kladr_street")
    void truncate();
}
