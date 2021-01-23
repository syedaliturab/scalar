package com.example.todolist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ToDoList.class}, version = 1, exportSchema = false)
public abstract class ToDoListDatabase extends RoomDatabase {
    public abstract ListDao getListDao();
    private static volatile ToDoListDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ToDoListDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ToDoListDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoListDatabase.class, "list_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE to_do_list ADD COLUMN check_task  BOOLEAN NOT NULL CHECK (mycolumn IN (0,1)) DEFAULT 0");
        }
    };

}

