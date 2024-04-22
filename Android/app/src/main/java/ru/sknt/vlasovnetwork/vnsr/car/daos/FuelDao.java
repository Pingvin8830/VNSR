package ru.sknt.vlasovnetwork.vnsr.car.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;

@Dao
public interface FuelDao {
    @Query("SELECT * FROM car_fuel")
    List<Fuel> getAll();

    @Insert
    void create(Fuel fuel);

    @Query("SELECT * FROM car_fuel WHERE id=:id")
    Fuel find(int id);
    @Query("SELECT * FROM car_fuel WHERE name=:name")
    Fuel find(String name);
    @Query("SELECT count(id) FROM car_fuel")
    int getCount();

    @Delete
    void delete(Fuel fuel);
    @Query("DELETE FROM car_fuel")
    void truncate();
}
