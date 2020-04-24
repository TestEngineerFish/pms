package com.einyun.app.pms.main.core.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.databinding.ObservableField;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.model.HomeModel;
import com.einyun.app.pms.main.core.viewmodel.contract.HomeTabViewModelContract;
import com.orhanobut.logger.Logger;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;

public class HomeTabViewModel extends BaseViewModel implements HomeTabViewModelContract{

    public ObservableField<HomeModel> homeModel = new ObservableField<>();

    public HomeTabViewModel() {
        homeModel.set(new HomeModel(CommonApplication.getInstance().getResources().getString(R.string.tv_tab_work_bench),
                CommonApplication.getInstance().getResources().getString(R.string.tv_tab_mine)));
    }

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    /**
     * 获取用户Id
     *
     * @return
     */
    @Override
    public String getUserId() {
        return userModuleService.getUserId();
    }

    public void setNotify(Context context){
        try {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(EXTRA_APP_PACKAGE, context.getPackageName());
            intent.putExtra(EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);

            //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);

            // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
            //  if ("MI 6".equals(Build.MODEL)) {
            //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            //      Uri uri = Uri.fromParts("package", getPackageName(), null);
            //      intent.setData(uri);
            //      // intent.setAction("com.android.settings/.SubSettings");
            //  }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();

            //下面这种方案是直接跳转到当前应用的设置界面。
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }
}
