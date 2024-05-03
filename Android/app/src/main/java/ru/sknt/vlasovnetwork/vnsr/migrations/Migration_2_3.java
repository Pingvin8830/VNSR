package ru.sknt.vlasovnetwork.vnsr.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_2_3 extends Migration {
    public Migration_2_3(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL("ALTER TABLE `travels_travel` RENAME TO `travels_travel_old`");
        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_travel` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`name` TEXT, " +
                "`participants` TEXT, " +
                "`state_id` INTEGER, " +
                "`fuel_consumption` REAL NOT NULL, " +
                "`fuel_price` REAL NOT NULL)"
        );
        supportSQLiteDatabase.execSQL("INSERT INTO `travels_travel` SELECT * FROM `travels_travel_old`");
        supportSQLiteDatabase.execSQL("DROP TABLE `travels_travel_old`");
        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX `index_travels_travel_name` ON `travels_travel` (`name`)");
    }
}
