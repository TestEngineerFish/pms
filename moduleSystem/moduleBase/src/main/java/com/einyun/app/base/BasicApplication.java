package com.einyun.app.base;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.ArrayList;
import java.util.List;

public class BasicApplication extends Application {
    private static BasicApplication app;
    private List<AppCompatActivity> activityList = new ArrayList<>();
    private final String TAG = "einyun";

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //在init之前打开日志和调试
        ARouter.init(this);
        if (BuildConfig.DEBUG) {
            LiveEventBus.config().supportBroadcast(this).autoClear(true);
        } else {
            LiveEventBus.config().supportBroadcast(this).autoClear(true).enableLogger(false);
        }
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)// (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return com.einyun.app.base.BuildConfig.DEBUG;
            }
        });
        if (BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(new ApplicationCrashHandler());
        }
    }

    public void addActivity(AppCompatActivity activity) {
        activityList.add(activity);
    }

    public void removeActivity(AppCompatActivity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }


    public void exit() {
        for (AppCompatActivity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }


    public static BasicApplication getInstance() {
        return app;
    }



}
