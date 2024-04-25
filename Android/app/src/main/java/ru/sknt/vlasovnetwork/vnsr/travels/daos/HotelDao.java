package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.Hotel;

@Dao
public interface HotelDao {
    @Query("SELECT * FROM travels_hotel")
    List<Hotel> getAll();

    @Insert
    void create(Hotel hotel);

    @Query("SELECT * FROM travels_hotel WHERE id=:id")
    Hotel find(int id);

    @Delete
    void delete(Hotel hotel);
    @Query("DELETE FROM travels_hotel")
    void truncate();
}
