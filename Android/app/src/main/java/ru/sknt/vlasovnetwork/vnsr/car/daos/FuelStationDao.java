package ru.sknt.vlasovnetwork.vnsr.car.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;

@Dao
public interface FuelStationDao {
    @Query("SELECT * FROM car_fuelstation")
    List<FuelStation> getAll();

    @Insert
    void create(FuelStation fuelStation);

    @Query("SELECT * FROM car_fuelstation WHERE id=:id")
    FuelStation find(int id);
    @Query("SELECT * FROM car_fuelstation WHERE company=:company AND number=:number")
    FuelStation find(String company, String number);
    @Query("SELECT count(id) FROM car_fuelstation")
    int getCount();

    @Delete
    void delete(FuelStation fuelStation);
    @Query("DELETE FROM car_fuelstation")
    void truncate();
}
