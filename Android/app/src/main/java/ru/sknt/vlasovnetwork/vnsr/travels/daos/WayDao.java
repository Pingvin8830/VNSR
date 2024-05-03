package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.Way;

@Dao
public interface WayDao {
    @Query("SELECT * FROM travels_way")
    List<Way> getAll();
    @Query("SELECT count(id) FROM travels_way")
    int getCount();

    @Insert
    void create(Way way);

    @Query("SELECT * FROM travels_way WHERE id=:id")
    Way find(int id);
    @Query(
            "SELECT w.* " +
                    "FROM travels_way w " +
                    "JOIN travels_travel t ON w.travel_id=t.id " +
                    "JOIN travels_point sp ON w.start_point_id=sp.id " +
                    "JOIN travels_point tp ON w.target_point_id=tp.id " +
                    "JOIN kladr_address sa ON sp.address_id=sa.id " +
                    "JOIN kladr_address ta ON tp.address_id=ta.id " +
                    "WHERE t.name=:travelName " +
                    "AND sa.name=:startAddressName " +
                    "AND ta.name=:targetAddressName"
    )
    Way find(String travelName, String startAddressName, String targetAddressName);

    @Delete
    void delete(Way way);
    @Query("DELETE FROM travels_way")
    void truncate();

}
