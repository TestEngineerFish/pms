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
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.einyun.app.base.ApplicationCrashHandler;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.utils.AppActiveStatusHelper;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.common.utils.MsgUtils;
import com.einyun.app.common.utils.whitecrash.CrashWhiteListManager;
import com.einyun.app.library.EinyunSDK;
import com.facebook.stetho.Stetho;
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
 * @Description: java???????????????
 * @Author: chumingjun
 * @CreateDate: 2019/09/09 19:12
 * @UpdateUser: ????????????
 * @UpdateDate: 2019/09/09 19:12
 * @UpdateRemark: ???????????????
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
//        initSkin();
        if (IsFastClick.isDebugVersion(this)) {
//            LeakCanary.install(this);
        }

        if (!BuildConfig.DEBUG) {
            CrashWhiteListManager.start();
            CrashReport.initCrashReport(getApplicationContext(), "ac69f9ff00", true);//bugly ?????????
        }
        initCloudChannel(this);
        initUmeng();
        initBaiduMap();
        initAppStatus();
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        inStetho();
    }

    /**
     * ????????????
     */
    private void initBaiduMap() {
        SDKInitializer.initialize(this);
        //???4.3.0??????????????????SDK????????????????????????????????????????????????????????????????????????????????????????????????.
        //??????BD09LL???GCJ02????????????????????????BD09LL?????????
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void initAppStatus() {
        AppActiveStatusHelper helper = new AppActiveStatusHelper();
        helper.register(this, new AppActiveStatusHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                Log.e(TAG, "??????????????????");
                if (inBackground) {
                    inBackground = false;
                }
                MsgUtils.setBadge(CommonApplication.getInstance(), 0);
            }

            @Override
            public void onBackground() {

                Log.e(TAG, "??????????????????");
                inBackground = true;
            }
        });
    }


    private void initUmeng() {
        UMConfigure.init(this, "5f33450bb4b08b653e936773", BuildConfig.FLAVOR, UMConfigure.DEVICE_TYPE_PHONE, null);
//        UMConfigure.init(this, "5ddf3f8a0cafb2f7d700066f", BuildConfig.FLAVOR, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        if (com.einyun.app.base.BuildConfig.DEBUG) {
            UMConfigure.setLogEnabled(true);
        }
        PlatformConfig.setWeixin(DataConstants.WECHAT_APPID, DataConstants.WECHAT_APP_SECRET);
    }

    /**
     * ?????????x5??????
     */
    private void preinitX5WebCore() {
        QbSdk.setDownloadWithoutWifi(true);
        if (!QbSdk.isTbsCoreInited()) {
            // ???????????????????????????????????????????????? App ?????????????????????????????????????????????????????? App ????????????????????????
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
     * ?????????????????????
     */
    private void initSkin() {
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())           // ???????????????????????????
                .addInflater(new SkinMaterialViewInflater())            // material design ?????????????????????[??????]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout ?????????????????????[??????]
                .addInflater(new SkinCardViewInflater())                // CardView v7 ?????????????????????[??????]
                .setSkinStatusBarColorEnable(true)                     // ????????????????????????????????????[??????]
                .setSkinWindowBackgroundEnable(false)                   // ??????windowBackground?????????????????????[??????]
                .loadSkin();
    }

    /**
     * ????????????????????????
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
        MiPushRegister.register(applicationContext, "2882303761518226834", "5271822697834"); // ???????????????????????????
        HuaWeiRegister.register(this); // ????????????????????????
        VivoRegister.register(applicationContext);
        OppoRegister.register(applicationContext, "797e69652a344010a140cb708c246357", "0d6a234cb5d14821b2a8d4383851faa0");
        MeizuRegister.register(applicationContext, "MEIZU_ID", "MEIZU_KEY");

//        GcmRegister.register(applicationContext, "send_id", "application_id"); // ??????FCM/GCM???????????????
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
        //???????????????8.0???????????????,????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // ???????????????id
            String id = "pms_notification_channel_id";
            // ??????????????????????????????????????????
            CharSequence name = "????????????";
            // ??????????????????????????????????????????
            String description = "????????????";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // ???????????????????????????
            mChannel.setDescription(description);
            // ??????????????????????????????????????? android ?????????????????????
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // ??????????????????????????????????????? android ?????????????????????
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //?????????notificationmanager????????????????????????
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    public static CommonApplication getInstance() {
        return app;
    }

    /**
     * ??????Android?????????
     */
    private void inStetho(){
        Stetho.initialize(Stetho
                .newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this)).build());
    }
}
