package ru.sknt.vlasovnetwork.vnsr.travels.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;

@Dao
public interface TollRoadDao {
    @Query("SELECT * FROM travels_tollroad")
    List<TollRoad> getAll();
    @Query("SELECT count(id) FROM travels_tollroad")
    int getCount();

    @Insert
    void create(TollRoad tollRoad);

    @Query("SELECT * FROM travels_tollroad WHERE id=:id")
    TollRoad find(int id);
    @Query("SELECT r.* " +
            "FROM travels_tollroad r " +
            "JOIN travels_travel t ON r.travel_id=t.id " +
            "WHERE t.name=:travel_name " +
            "AND r.start=:start " +
            "AND r.`end`=:end"
    )
    TollRoad find(String travel_name, String start, String end);

    @Query("SELECT * FROM travels_tollroad WHERE travel_id=:travel_id")
    List<TollRoad> filter(int travel_id);

    @Delete
    void delete(TollRoad tollRoad);
    @Query("DELETE FROM travels_tollroad")
    void truncate();
}
