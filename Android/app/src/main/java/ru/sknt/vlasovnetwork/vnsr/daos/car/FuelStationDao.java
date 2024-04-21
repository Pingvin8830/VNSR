package ru.sknt.vlasovnetwork.vnsr.daos.car;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.models.car.FuelStation;

@Dao
public interface FuelStationDao {
    @Query("SELECT * FROM car_fuelstation")
    List<FuelStation> getAll();

    @Insert
    void create(FuelStation fuelStation);

    @Query("SELECT * FROM car_fuelstation WHERE id=:id")
    FuelStation find(int id);

    @Delete
    void delete(FuelStation fuelStation);
    @Query("DELETE FROM car_fuelstation")
    void truncate();
}
