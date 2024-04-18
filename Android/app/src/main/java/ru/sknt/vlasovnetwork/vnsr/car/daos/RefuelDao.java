package ru.sknt.vlasovnetwork.vnsr.daos.car;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;

@Dao
public interface RefuelDao {
    @Query("SELECT * FROM car_refuel")
    List<Refuel> getAll();

    @Insert
    void create(Refuel refuel);

    @Query("SELECT * FROM car_refuel WHERE id=:id")
    Refuel find(int id);
    @Query("SELECT * FROM car_refuel WHERE datetime=:datetime")
    Refuel find(long datetime);

    @Delete
    void delete(Refuel refuel);
    @Query("DELETE FROM car_refuel")
    void truncate();
}
