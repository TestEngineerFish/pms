package com.einyun.app.common.application;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.library.EinyunSDK;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

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
    @Override
    public void onCreate() {
        super.onCreate();
        CommonHttpService.Companion.setNeedTenantId(true);
        EinyunSDK.Companion.init(this, BuildConfig.BASE_URL);
        if (com.einyun.app.base.BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }

        preinitX5WebCore();
        initSkin();
        if (IsFastClick.isDebugVersion(this)) {
            LeakCanary.install(this);
        }
        CrashReport.initCrashReport(getApplicationContext(), "ac69f9ff00", true);//bugly 初始化
        initCloudChannel(this);
    }

    /**
     * 预加载x5内核
     */
    private void preinitX5WebCore() {
        QbSdk.setDownloadWithoutWifi(true);
        if (!QbSdk.isTbsCoreInited()){
            // 这个函数内是异步执行所以不会阻塞 App 主线程，这个函数内是轻量级执行所以对 App 启动性能没有影响
            QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {
//                    Logger.d("X5WebCore initFinished");
                }

                @Override
                public void onViewInitFinished(boolean b) {
                    Logger.d("X5WebCore init->"+b);
                }
            });
        }
    }

    /**
     * 初始化换肤框架
     */
    private void initSkin(){
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
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
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
    }

}
