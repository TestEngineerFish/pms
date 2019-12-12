package com.einyun.app.base.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.converter.ButtonTypeConvert;
import com.einyun.app.base.db.converter.DataBeanTypeConvert;
import com.einyun.app.base.db.converter.DateConverter;
import com.einyun.app.base.db.converter.DelayExtensionApplicationBeanConvert;
import com.einyun.app.base.db.converter.ExtensionApplicationBeanConvert;
import com.einyun.app.base.db.converter.InitDataTypeConvert;
import com.einyun.app.base.db.converter.InspectionTypeConvert;
import com.einyun.app.base.db.converter.PatrolContentConvert;
import com.einyun.app.base.db.converter.StringTypeConvert;
import com.einyun.app.base.db.converter.SubInspectionTypeConvert;
import com.einyun.app.base.db.converter.WorkNoteTypeConvert;
import com.einyun.app.base.db.dao.DistributeDao;
import com.einyun.app.base.db.dao.PatrolDao;
import com.einyun.app.base.db.dao.PatrolInfoDao;
import com.einyun.app.base.db.dao.SearchHistoryDao;
import com.einyun.app.base.db.dao.UserDao;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.db.entity.SearchHistory;
import com.einyun.app.base.db.entity.User;


@Database(entities = {User.class, Patrol.class, SearchHistory.class,
        PatrolInfo.class, PatrolLocal.class, Distribute.class
}, version = 4, exportSchema = false)
@TypeConverters({DateConverter.class, StringTypeConvert.class, ButtonTypeConvert.class,
        DataBeanTypeConvert.class, DelayExtensionApplicationBeanConvert.class,
        ExtensionApplicationBeanConvert.class, InitDataTypeConvert.class,
        InspectionTypeConvert.class, PatrolContentConvert.class,
        SubInspectionTypeConvert.class, WorkNoteTypeConvert.class
})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "pms-db";

    public abstract UserDao userDao();

    public abstract PatrolDao patrolDao();

    public abstract PatrolInfoDao patrolInfoDao();

    public abstract SearchHistoryDao searchHistoryDao();

    public abstract DistributeDao distributeDao();

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
                }
            }
        }
        return sInstance;
    }


}
