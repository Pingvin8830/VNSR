package ru.sknt.vlasovnetwork.vnsr.daos.car;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.models.car.Fuel;

@Dao
public interface FuelDao {
    @Query("SELECT * FROM car_fuel")
    List<Fuel> getAll();

    @Insert
    void create(Fuel fuel);

    @Query("SELECT * FROM car_fuel WHERE id=:id")
    Fuel find(int id);

    @Delete
    void delete(Fuel fuel);
    @Query("DELETE FROM car_fuel")
    void truncate();
}
