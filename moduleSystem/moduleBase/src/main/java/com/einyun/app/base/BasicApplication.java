package com.einyun.app.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.einyun.app.base.db.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BasicApplication extends Application {
    private static BasicApplication app;
    private List<Activity> activityList = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //在init之前打开日志和调试
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);


        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        if (BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(new ApplicationCrashHandler());
        }
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }


    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }



    public static BasicApplication getInstance() {
        return app;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }


}
