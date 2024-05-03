package ru.sknt.vlasovnetwork.vnsr.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_5_6 extends Migration {
    public Migration_5_6(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL("ALTER TABLE `travels_tollroad` RENAME TO `travels_tollroad_old`");
        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_tollroad` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`travel_id` INTEGER, " +
                "`name` TEXT, " +
                "`start` TEXT, " +
                "`end` TEXT, " +
                "`price` REAL NOT NULL)"
        );
        supportSQLiteDatabase.execSQL("INSERT INTO `travels_tollroad` SELECT * FROM `travels_tollroad_old`");
        supportSQLiteDatabase.execSQL("DROP TABLE `travels_tollroad_old`");
    }
}
