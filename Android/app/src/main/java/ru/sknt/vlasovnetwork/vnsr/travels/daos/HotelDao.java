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
    @Query("SELECT count(id) FROM travels_hotel")
    int getCount();

    @Insert
    void create(Hotel hotel);

    @Query("SELECT * FROM travels_hotel WHERE id=:id")
    Hotel find(int id);
    @Query("SELECT * FROM travels_hotel WHERE arrival=:arrival AND departure=:departure")
    Hotel find(long arrival, long departure);

    @Query("SELECT * FROM travels_hotel WHERE arrival BETWEEN :travel_start AND :travel_end OR departure BETWEEN :travel_start AND :travel_end")
    List<Hotel> filter(long travel_start, long travel_end);

    @Delete
    void delete(Hotel hotel);
    @Query("DELETE FROM travels_hotel")
    void truncate();
}
