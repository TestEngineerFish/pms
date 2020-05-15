package com.einyun.app.common.application;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.huawei.HuaWeiRegister;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.MeizuRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.alibaba.sdk.android.push.register.OppoRegister;
import com.alibaba.sdk.android.push.register.VivoRegister;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.utils.AppActiveStatusHelper;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.common.utils.MsgUtils;
import com.einyun.app.library.EinyunSDK;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * @ProjectName: android-framework
 * @Package: com.yykj.app.module.communal.application
 * @ClassName: CommonApplication
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/09 19:12
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/09 19:12
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CommonApplication extends BasicApplication {
    private static final String TAG = "CommonApplication";
    private static CommonApplication app;
    private boolean inBackground = false;
    public IWXAPI api;

    private void registerWeixin() {
        api = WXAPIFactory.createWXAPI(this, DataConstants.WECHAT_APPID);
        api.registerApp(DataConstants.WECHAT_APPID);
    }

    public IWXAPI getWeiXinApi() {
        return api;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        CommonHttpService.Companion.setNeedTenantId(true);
        EinyunSDK.Companion.init(this, BuildConfig.BASE_URL);
        if (com.einyun.app.base.BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }

        preinitX5WebCore();
        initSkin();
        if (IsFastClick.isDebugVersion(this)) {
//            LeakCanary.install(this);
        }
        CrashReport.initCrashReport(getApplicationContext(), "ac69f9ff00", true);//bugly 初始化
        initCloudChannel(this);
        initUmeng();
        initAppStatus();
    }

    private void initAppStatus() {
        AppActiveStatusHelper helper = new AppActiveStatusHelper();
        helper.register(this, new AppActiveStatusHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                Log.e(TAG, "应用进入前台");
                if (inBackground) {
                    inBackground = false;
                }
                MsgUtils.setBadge(CommonApplication.getInstance(),0);
            }

            @Override
            public void onBackground() {

                Log.e(TAG, "应用进入后台");
                inBackground = true;
            }
        });
    }


    private void initUmeng() {
        UMConfigure.init(this, "5ddf3f8a0cafb2f7d700066f", BuildConfig.FLAVOR, UMConfigure.DEVICE_TYPE_PHONE, null);
//        UMConfigure.init(this, "5ddf3f8a0cafb2f7d700066f", BuildConfig.FLAVOR, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        if (com.einyun.app.base.BuildConfig.DEBUG) {
            UMConfigure.setLogEnabled(true);
        }
        PlatformConfig.setWeixin(DataConstants.WECHAT_APPID, DataConstants.WECHAT_APP_SECRET);
    }

    /**
     * 预加载x5内核
     */
    private void preinitX5WebCore() {
        QbSdk.setDownloadWithoutWifi(true);
        if (!QbSdk.isTbsCoreInited()) {
            // 这个函数内是异步执行所以不会阻塞 App 主线程，这个函数内是轻量级执行所以对 App 启动性能没有影响
            QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {
//                    Logger.d("X5WebCore initFinished");
                }

                @Override
                public void onViewInitFinished(boolean b) {
                    Logger.d("X5WebCore init->" + b);
                }
            });
        }
    }

    /**
     * 初始化换肤框架
     */
    private void initSkin() {
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        this.createNotificationChannel();
        PushServiceFactory.init(applicationContext);
        pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
//        bindAccount("1");
        MiPushRegister.register(applicationContext, "2882303761518226834", "5271822697834"); // 初始化小米辅助推送
        HuaWeiRegister.register(this); // 接入华为辅助推送
        VivoRegister.register(applicationContext);
        OppoRegister.register(applicationContext, "797e69652a344010a140cb708c246357", "0d6a234cb5d14821b2a8d4383851faa0");
        MeizuRegister.register(applicationContext, "MEIZU_ID", "MEIZU_KEY");

//        GcmRegister.register(applicationContext, "send_id", "application_id"); // 接入FCM/GCM初始化推送
    }

    CloudPushService pushService;

    public void bindAccount(String accountName) {
        if (pushService != null)
            pushService.bindAccount(accountName, new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    Log.d(TAG, "init cloudchannel success");
                }

                @Override
                public void onFailed(String errorCode, String errorMessage) {
                    Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                }
            });
    }

    public void unbindAccount() {
        if (pushService != null)
            pushService.unbindAccount(new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    Log.d(TAG, "init cloudchannel success");
                }

                @Override
                public void onFailed(String errorCode, String errorMessage) {
                    Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                }
            });
    }

    private void createNotificationChannel() {
        //针对于安卓8.0以上的手机,开启通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "pms_notification_channel_id";
            // 用户可以看到的通知渠道的名字
            CharSequence name = "消息通知";
            // 用户可以看到的通知渠道的描述
            String description = "消息通知";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    public static CommonApplication getInstance() {
        return app;
    }
}
