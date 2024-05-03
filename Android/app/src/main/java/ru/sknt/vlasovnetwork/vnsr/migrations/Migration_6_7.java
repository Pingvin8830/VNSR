package ru.sknt.vlasovnetwork.vnsr.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_6_7 extends Migration {
    public Migration_6_7(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL("ALTER TABLE `travels_hotel` RENAME TO `travels_hotel_old`");
        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `travels_hotel` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`name` TEXT, " +
                "`address_id` INTEGER, " +
                "`arrival` INTEGER, " +
                "`departure` INTEGER, " +
                "`cost` REAL NOT NULL, " +
                "`state` TEXT)"
        );
        supportSQLiteDatabase.execSQL("INSERT INTO `travels_hotel` " +
                "(id, name, address_id, arrival, departure, cost, state) " +
                "SELECT h.id, a.name, h.address_id, h.arrival, h.departure, h.cost, h.state " +
                "FROM travels_hotel_old h " +
                "JOIN kladr_address a ON h.address_id=a.id"
        );
        supportSQLiteDatabase.execSQL("DROP TABLE `travels_hotel_old`");
        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_travels_hotel_arrival_departure` ON `travels_hotel` (`arrival`, `departure`)");
    }
}
