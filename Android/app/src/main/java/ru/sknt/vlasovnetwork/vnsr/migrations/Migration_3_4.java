package ru.sknt.vlasovnetwork.vnsr.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_3_4 extends Migration {
    public Migration_3_4(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL("ALTER TABLE `travels_point` RENAME TO `travels_point_old`");
        supportSQLiteDatabase.execSQL("ALTER TABLE `travels_way` RENAME TO `travels_way_old`");

        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_point` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`travel_id` INTEGER, " +
                        "`address_id` INTEGER, " +
                        "`datetime` INTEGER, " +
                        "`doing` TEXT, " +
                        "`odometer` INTEGER NOT NULL)"
        );
        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_way` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`travel_id` INTEGER, " +
                "`start_point_id` INTEGER, " +
                "`target_point_id` INTEGER, " +
                "`distance` REAL NOT NULL)"
        );

        supportSQLiteDatabase.execSQL("INSERT INTO `travels_point` SELECT * FROM `travels_point_old`");
        supportSQLiteDatabase.execSQL("INSERT INTO `travels_way` SELECT * FROM `travels_way_old`");

        supportSQLiteDatabase.execSQL("DROP TABLE `travels_point_old`");
        supportSQLiteDatabase.execSQL("DROP TABLE `travels_way_old`");

        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_travels_point_datetime` ON `travels_point` (`datetime`)");
        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_travels_way_travel_id_start_point_id_target_point_id` ON `travels_way` (`travel_id`, `start_point_id`, `target_point_id`)");
    }
}
