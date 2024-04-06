package ru.sknt.vlasovnetwork.vnsr;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RenameTable;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;

import ru.sknt.vlasovnetwork.vnsr.models.travels.TravelState;
import ru.sknt.vlasovnetwork.vnsr.models.travels.TravelStateDao;

@Database(
        version = 4,
        entities = {
                TravelState.class
        },
        autoMigrations = {
                @AutoMigration(from = 1, to = 2),
                @AutoMigration(from = 2, to = 3),
                @AutoMigration(from = 3, to = 4, spec = VNSRDatabase.Migration3to4.class)
        }
)
public abstract class VNSRDatabase extends RoomDatabase {
    @RenameTable(fromTableName = "TravelState", toTableName = "travels_travelstate")
    static class Migration3to4 implements AutoMigrationSpec { }

    public abstract TravelStateDao travelStateDao();
}
