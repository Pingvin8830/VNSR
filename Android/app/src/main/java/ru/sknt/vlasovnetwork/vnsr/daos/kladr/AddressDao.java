package ru.sknt.vlasovnetwork.vnsr.daos.kladr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.models.kladr.Address;

@Dao
public interface AddressDao {
    @Query("SELECT * FROM kladr_address")
    List<Address> getAll();

    @Insert
    void create(Address address);

    @Query("SELECT * FROM kladr_address WHERE id=:id")
    Address find(int id);
    @Query("SELECT * FROM kladr_address WHERE name=:name")
    Address find(String name);

    @Delete
    void delete(Address address);
    @Query("DELETE FROM kladr_address")
    void truncate();
}
