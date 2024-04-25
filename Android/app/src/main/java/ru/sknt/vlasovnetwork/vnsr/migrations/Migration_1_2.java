package ru.sknt.vlasovnetwork.vnsr.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_1_2 extends Migration {
    public Migration_1_2(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `sync_task` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " `object_name` TEXT," +
                " `object_id` INTEGER NOT NULL," +
                " `field` TEXT," +
                " `value` TEXT" +
                ")"
        );
        supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_sync_task_objectname_objectid_field` ON `sync_task` (`object_name`, `object_id`, `field`)");
    }
}
