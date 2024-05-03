package ru.sknt.vlasovnetwork.vnsr.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_4_5 extends Migration {
    public Migration_4_5(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL("ALTER TABLE `travels_travel` RENAME TO `travels_travel_old`");
        supportSQLiteDatabase.execSQL("ALTER TABLE `travels_point` RENAME TO `travels_point_old`");

        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_travel` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`name` TEXT, " +
                "`participants` TEXT, " +
                "`state` TEXT, " +
                "`fuel_consumption` REAL NOT NULL, " +
                "`fuel_price` REAL NOT NULL, " +
                "`start_datetime` INTEGER, " +
                "`end_datetime` INTEGER)"
        );
        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_point` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`address_id` INTEGER, " +
                "`arrival_datetime` INTEGER, " +
                "`departure_datetime` INTEGER, " +
                "`doing` TEXT, " +
                "`odometer` INTEGER NOT NULL)"
        );

        supportSQLiteDatabase.execSQL("INSERT INTO `travels_travel` (`id`, `name`, `participants`, `state`, `fuel_consumption`, `fuel_price`) SELECT `id`, `name`, `participants`, 'U', `fuel_consumption`, `fuel_price` FROM `travels_travel_old`");
        supportSQLiteDatabase.execSQL("INSERT INTO `travels_point` (`id`, `address_id`, `doing`, `odometer`) SELECT `id`, `address_id`, 'U', `odometer` FROM `travels_point_old`");

        supportSQLiteDatabase.execSQL("DROP TABLE `travels_travel_old`");
        supportSQLiteDatabase.execSQL("DROP TABLE `travels_point_old`");
        supportSQLiteDatabase.execSQL("DROP TABLE `travels_travelstate`");

        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_travels_travel_name` ON `travels_travel` (`name`)");
        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_travels_point_arrival_datetime_departure_datetime` ON `travels_point` (`arrival_datetime`, `departure_datetime`)");

        supportSQLiteDatabase.execSQL("DROP INDEX `index_kladr_region_name`");
        supportSQLiteDatabase.execSQL("DROP INDEX `index_kladr_citytype_short`");
        supportSQLiteDatabase.execSQL("DROP INDEX `index_kladr_streettype_short`");
    }
}
