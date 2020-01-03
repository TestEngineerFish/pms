package com.einyun.app.pms.mine.viewmodule;

import android.content.Context;
import android.content.pm.PackageManager;

import com.einyun.app.base.BaseViewModel;

public class SettingViewModel extends BaseViewModel {
    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
