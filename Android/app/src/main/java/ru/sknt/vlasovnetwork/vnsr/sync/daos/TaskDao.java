package ru.sknt.vlasovnetwork.vnsr.sync.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.sync.models.Task;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM sync_task")
    List<Task> getAll();

    @Insert
    void create(Task task);

    @Query("SELECT * FROM sync_task WHERE id=:id")
    Task find(int id);
    @Query("SELECT * FROM sync_task WHERE object_name=:name AND object_id=:id")
    List<Task> filter(String name, int id);

    @Delete
    void delete(Task task);
    @Query("DELETE FROM sync_task")
    void truncate();
}
