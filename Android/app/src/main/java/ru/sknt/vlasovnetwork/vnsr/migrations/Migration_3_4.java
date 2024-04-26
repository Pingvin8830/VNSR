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
        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_point` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`travel_id` INTEGER, " +
                        "`address_id` INTEGER, " +
                        "`datetime` INTEGER, " +
                        "`doing` TEXT, " +
                        "`odometer` INTEGER NOT NULL)"
        );
        supportSQLiteDatabase.execSQL("INSERT INTO `travels_point` SELECT * FROM `travels_point_old`");
        supportSQLiteDatabase.execSQL("DROP TABLE `travels_point_old`");
        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_travels_point_datetime` ON `travels_point` (`datetime`)");
    }
}
