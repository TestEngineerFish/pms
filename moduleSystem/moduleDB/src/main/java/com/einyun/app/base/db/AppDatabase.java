package com.einyun.app.base.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.converter.BasicDataTypeConvert;
import com.einyun.app.base.db.converter.BizDataBeanTypeConvert;
import com.einyun.app.base.db.converter.ButtonTypeConvert;
import com.einyun.app.base.db.converter.ButtonTypePlanConvert;
import com.einyun.app.base.db.converter.DataBeanTypeConvert;
import com.einyun.app.base.db.converter.DateConverter;
import com.einyun.app.base.db.converter.DelayExtensionApplicationBeanConvert;
import com.einyun.app.base.db.converter.ExtensionApplicationBeanConvert;
import com.einyun.app.base.db.converter.ExtensionApplicationPlanBeanConvert;
import com.einyun.app.base.db.converter.InitDataTypeConvert;
import com.einyun.app.base.db.converter.InspectionTypeConvert;
import com.einyun.app.base.db.converter.PatrolContentConvert;
import com.einyun.app.base.db.converter.PlanDataSubJhgdgzjdbConvert;
import com.einyun.app.base.db.converter.PlanDataSubJhgdzybConvert;
import com.einyun.app.base.db.converter.PlanDataTypeConvert;
import com.einyun.app.base.db.converter.PlanInitDataConvert;
import com.einyun.app.base.db.converter.PlanInitDataJhgdgzjdbConvert;
import com.einyun.app.base.db.converter.PlanInitDataJhgdwlbConvert;
import com.einyun.app.base.db.converter.PlanInitDatajhgdzybConvert;
import com.einyun.app.base.db.converter.PlanZyjhgdConvert;
import com.einyun.app.base.db.converter.StartFlowParamBeanTypeConvert;
import com.einyun.app.base.db.converter.StringTypeConvert;
import com.einyun.app.base.db.converter.SubInspectionTypeConvert;
import com.einyun.app.base.db.converter.WorkNoteTypeConvert;
import com.einyun.app.base.db.dao.BasicDataDao;
import com.einyun.app.base.db.dao.CheckPointDao;
import com.einyun.app.base.db.dao.CreateQualityRequestDao;
import com.einyun.app.base.db.dao.DistributeDao;
import com.einyun.app.base.db.dao.PatrolDao;
import com.einyun.app.base.db.dao.PatrolInfoDao;
import com.einyun.app.base.db.dao.PlanDao;
import com.einyun.app.base.db.dao.PlanInfoDao;
import com.einyun.app.base.db.dao.QualityRequestDao;
import com.einyun.app.base.db.dao.SearchHistoryDao;
import com.einyun.app.base.db.dao.UserDao;
import com.einyun.app.base.db.entity.BasicDataDb;
import com.einyun.app.base.db.entity.CheckPoint;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.db.entity.Plan;

import com.einyun.app.base.db.entity.PlanInfo;
import com.einyun.app.base.db.entity.PlanLocal;
import com.einyun.app.base.db.entity.QualityRequest;
import com.einyun.app.base.db.entity.SearchHistory;
import com.einyun.app.base.db.entity.User;


@Database(entities = {User.class, Patrol.class, SearchHistory.class,
        PatrolInfo.class, PatrolLocal.class, Distribute.class, CheckPoint.class,
        Plan.class, PlanInfo.class, PlanLocal.class, BasicDataDb.class, QualityRequest.class, CreateUnQualityRequest.class
}, version = 10)
@TypeConverters({DateConverter.class, StringTypeConvert.class, ButtonTypeConvert.class,

        ButtonTypePlanConvert.class, ExtensionApplicationPlanBeanConvert.class,
        PlanDataSubJhgdzybConvert.class, PlanDataSubJhgdgzjdbConvert.class,
        PlanInitDataConvert.class,PlanDataTypeConvert.class, PlanInitDatajhgdzybConvert.class,
        PlanInitDataJhgdgzjdbConvert.class, PlanInitDataJhgdwlbConvert.class,
        PlanZyjhgdConvert.class,


        DataBeanTypeConvert.class, DelayExtensionApplicationBeanConvert.class,
        ExtensionApplicationBeanConvert.class, InitDataTypeConvert.class,
        InspectionTypeConvert.class, PatrolContentConvert.class,
        SubInspectionTypeConvert.class, WorkNoteTypeConvert.class, BasicDataTypeConvert.class,
        BizDataBeanTypeConvert.class, StartFlowParamBeanTypeConvert.class
})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "pms2-db";

    public abstract UserDao userDao();

    public abstract PatrolDao patrolDao();

    public abstract PatrolInfoDao patrolInfoDao();

    public abstract SearchHistoryDao searchHistoryDao();

    public abstract DistributeDao distributeDao();

    public abstract CheckPointDao checkPointDao();

    public abstract PlanDao planDao();
    public abstract PlanInfoDao planInfoDao();

    public abstract BasicDataDao basicDataDao();

    public abstract QualityRequestDao qualityRequestDao();

    public abstract CreateQualityRequestDao createQualityRequestDao();

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()//数据库版本改变 清空数据 不然覆盖安装crash
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return sInstance;
    }

}
