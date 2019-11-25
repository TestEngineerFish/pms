package com.einyun.app.base.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.converter.DateConverter;
import com.einyun.app.base.db.dao.UserDao;
import com.einyun.app.base.db.entity.User;


@Database(entities = {User.class}, version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "pms-db";

    public abstract UserDao userDao();
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME).allowMainThreadQueries().build();
                }
            }
        }
        return sInstance;
    }


}
