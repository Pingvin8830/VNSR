package ru.sknt.vlasovnetwork.vnsr.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.models.travels.Way;

@Dao
public interface WayDao {
    @Query("SELECT * FROM travels_way")
    List<Way> getAll();

    @Insert
    void create(Way way);

    @Query("SELECT * FROM travels_way WHERE id=:id")
    Way find(int id);

    @Delete
    void delete(Way way);
    @Query("DELETE FROM travels_way")
    void truncate();

}
