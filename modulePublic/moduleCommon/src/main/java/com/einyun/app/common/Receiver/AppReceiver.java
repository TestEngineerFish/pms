package com.einyun.app.common.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.einyun.app.common.application.CommonApplication;
import com.googlecode.eyesfree.utils.LogUtils;

public class AppReceiver extends BroadcastReceiver {
    private static final String TAG = "AppReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        Log.e(TAG, "onReceive: "+"卸载成功");
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
//            Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();

            Log.e(TAG, "onReceive: "+"安装成功");
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();

//            Toast.makeText(context, "替换成功" + packageName, Toast.LENGTH_LONG).show();
            Log.e(TAG, "onReceive: "+"替换成功");
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            CommonApplication.getInstance().unbindAccount();
//            Toast.makeText(context, "卸载成功" + packageName, Toast.LENGTH_LONG).show();
            Log.e(TAG, "onReceive: "+"卸载成功");
        }
    }
}
