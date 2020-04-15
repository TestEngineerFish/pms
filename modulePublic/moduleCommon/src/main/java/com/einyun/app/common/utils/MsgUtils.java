package com.einyun.app.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.List;

import static com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder.getPackageName;

public class MsgUtils {

    public static void setBadge(Context context, int num) {
        setBadgeNum(num, context);
        setVivoBadge(context, String.valueOf(num));
//        setXiaoMiBadge(context, num);
        setSanXingBadge(context, num);
        setGoogleBadge(context, String.valueOf(num));
        setOPPOBadge(num, context);
        setOPPOBadge2(num, context);
    }

    /**
     * 华为手机设置角标
     */
    public static void setBadgeNum(int num, Context context) {
        try {
            Bundle bunlde = new Bundle();
            bunlde.putString("package", "com.einyun.app.pms");
            bunlde.putString("class", "com.einyun.app.pms.user.core.ui.SplashViewModelActivity");
            bunlde.putInt("badgenumber", num);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * s设置小米角标
     */
    public static void setXiaoMiBadge(Context context, int number) {
        try {
            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            field.set(miuiNotification, String.valueOf(number == 0 ? "" : number));
        } catch (Exception e) {
            e.printStackTrace();
            // miui6之前
//            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
//            localIntent.putExtra("android.intent.extra.update_application_component_name", context.getPackageName() + "/." + "SplashViewModelActivity");
//            localIntent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(number == 0 ? "" : number));
//            context.sendBroadcast(localIntent);
        }
    }

    /**
     * s设置三星角标
     */
    public static void setSanXingBadge(Context context, int number) {
        try {
            String launcherClassName = "com.einyun.app.pms.user.core.ui.SplashViewModelActivity";
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", number);
            intent.putExtra("badge_count_package_name", getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * vivo
     */
    public static void setVivoBadge(Context context, String num) {
        try {
            Intent localIntent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            localIntent.putExtra("packageName", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            localIntent.putExtra("className", launchClassName);
            if (TextUtils.isEmpty(num)) {
                num = "";
            } else {
                int numInt = Integer.valueOf(num);
                if (numInt > 0) {
                    if (numInt > 99) {
                        num = "99";
                    }
                } else {
                    num = "0";
                }
            }
            localIntent.putExtra("notificationNum", Integer.parseInt(num));
            context.sendBroadcast(localIntent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * google
     */
    public static void setGoogleBadge(Context context, String num) {
        try {
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", num);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", "com.einyun.app.pms.user.core.ui.SplashViewModelActivity"); // com.test. badge.MainActivity is your apk main activity
            context.sendBroadcast(intent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void setOPPOBadge(int count, Context context) {
        try {
            Bundle extras = new Bundle();
            extras.putInt("app_badge_count", count);
            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", String.valueOf(count), extras);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean setOPPOBadge2(int count, Context context) {
        try {
            Intent intent = new Intent("com.oppo.unsettledevent");
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("number", count);
            intent.putExtra("upgradeNumber", count);
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
            if (receivers != null && receivers.size() > 0) {
                context.sendBroadcast(intent);
            } else {
                Bundle extras = new Bundle();
                extras.putInt("app_badge_count", count);
                context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extras);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
