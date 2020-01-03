package com.einyun.app.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.einyun.app.base.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * Description: 获取设备信息
 * Copyright (c) CSII.
 * All Rights Reserved.
 *
 * @version 1.0 2015-7-30 上午10:10:39 by xupeng(xupeng@csii.com.cn)
 */
public class DeviceUtil {

    /**
     * 得到当前应用版本名称的方法
     *
     * @param context :上下文
     */
    public static String getVersionId(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        String version = "";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取签名信息
     *
     * @param context
     * @return
     */
    public static String getSign(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (packInfo == null || packInfo.signatures == null) {
                return "";
            }
            return packInfo.signatures[0].toCharsString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @return String
     * @biref 获取手机型号
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * @param context 上下文
     * @return String
     * @biref 获取手机MAC地址
     */
    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }

    /**
     * @return int
     * @brief 获取手机设别android SDK版本号
     */
    public static int getSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * @return String
     * @brief 硬件设备类型
     */
    public static String getDeviceType() {
        return "ANDROID";
    }

    /**
     * @param context 上下文
     * @return String
     * @brief 获取手机设备IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (!StringUtil.isNullStr(deviceId)) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * 获取IP
     * @param context
     * @return
     */
    public static String getIp(final Context context) {
        String ip = null;
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // mobile 3G Data Network
        android.net.NetworkInfo.State mobile = conMan.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).getState();
        // wifi
        android.net.NetworkInfo.State wifi = conMan.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState();

        // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
        if (mobile == android.net.NetworkInfo.State.CONNECTED
                || mobile == android.net.NetworkInfo.State.CONNECTING) {
            ip =  getLocalIpAddress();
        }
        if (wifi == android.net.NetworkInfo.State.CONNECTED
                || wifi == android.net.NetworkInfo.State.CONNECTING) {
            //获取wifi服务
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            //判断wifi是否开启
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            ip =(ipAddress & 0xFF ) + "." +
                    ((ipAddress >> 8 ) & 0xFF) + "." +
                    ((ipAddress >> 16 ) & 0xFF) + "." +
                    ( ipAddress >> 24 & 0xFF) ;
        }
        return ip;

    }

    /**
     *
     * @return 手机GPRS网络的IP
     */
    private static String getLocalIpAddress()
    {
        try {
            //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {//获取IPv4的IP地址
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * @param context 上下文
     * @return String(480*800)
     * @brief 获取手机屏幕尺寸
     */
    public static String getDisplayMetrics(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕宽（像素，如：480px）
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
        return String.valueOf(screenWidth) + "*" + String.valueOf(screenHeight);
    }

    /**
     * @param context 上下文
     * @return int
     * @brief 获取手机屏幕尺寸 高度
     */
    public static int getMetricsHeight(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
        return screenHeight;
    }

    /**
     * @param context 上下文
     * @return int
     * @brief 获取手机屏幕尺寸 宽度
     */
    public static int getMetricsWidth(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕高（像素，如：800px）
        return screenWidth;
    }

    /**
     * @param context 上下文
     * @return boolean
     * @brief 判断手机当前网络开关状态 wifi gprs
     */
    public static boolean IsNetWork(Context context) {
        // 判断手机当前网络开关状态 wifi gprs
        boolean isnetwork = true;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean isWifiConnected = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ? true : false;
            boolean isGprsConnected = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ? true : false;
            if (isWifiConnected || isGprsConnected) {
                isnetwork = true;
            } else {
                isnetwork = false;
            }
        } catch (Exception e) {
            isnetwork = true;
        }
        return isnetwork;
    }

    /**
     * @param activity activity对象
     * @return Bitmap
     * @brief 获取指定Activity的截屏
     */
    public static Bitmap getScreenShot(Activity activity) {

        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * @return boolean
     * @brief 判断系统是否root
     */
    public static boolean isRoot() {
        boolean isroot = false;
        try {
            if (Runtime.getRuntime().exec("su").getOutputStream() == null) {
                // 没有root
                isroot = false;
            } else {
                isroot = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isroot;
    }

    /**
     * @return boolean
     * @brief 判断sd卡是否存在
     */
    public static boolean hasSDCard() {
        String SDState = android.os.Environment.getExternalStorageState();
        if (SDState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context 上下文
     * @return boolean
     * @brief 判断手机GPS是否开启
     */
    public static boolean isOpenGPS(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
            // Intent myIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            // mContext.startActivity(myIntent);
        }
    }

    /**
     * @param context 上下文
     * @return String
     * @brief 获取应用程序版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * @param context 上下文
     * @return String
     * @brief 获取应用程序版本名
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * @param context 上下文
     * @return Map（key：SIGNATURE_FLAG，value：apk包签名信息；key：APKMD5_FLAG，value：apk MD5值）
     * @brief 获取应用程序包 MD5值
     */
//    public static Map<String, String> getAPKMD5Info(Context context) {
//        Map<String, String> map = new HashMap<String, String>();
//        String SIGNATURE_FLAG = "";
//        String APKMD5_FLAG = "";
//        PackageManager packageManager = context.getPackageManager();
//        PackageInfo packageInfo;
//        try {
//            packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//            File f = new File(packageInfo.applicationInfo.sourceDir);
//            for (Signature signature : packageInfo.signatures) {
//                // 取到Package的签名
//                SIGNATURE_FLAG = MD5Util.getMD5String(signature.toCharsString());
//            }
//            // 取到APKMD5
//            APKMD5_FLAG = MD5Util.getFileMD5String(f);
//            map.put("SIGNATURE_FLAG", SIGNATURE_FLAG);
//            map.put("APKMD5_FLAG", APKMD5_FLAG);
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return map;
//    }

    /**
     * @param activity activity对象
     * @return int
     * @brief 获取手机屏幕密度
     */
    public static int getMetricDpi(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return densityDpi;
    }

    public static String getClientType() {
        return "Android";
    }

    /**
     * @param context 上下文
     * @return Map<String,String>
     * @brief 获取设备信息
     */
    public static Map<String, String> getDeviceInfo(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        map.clear();
        // IMEI
        map.put("DeviceId", getIMEI(context));
        // 判断sd卡是否存在,1存在，0不存在
        map.put("SDFlag", hasSDCard() ? "1" : "0");
        // 获取设备型号
        map.put("Model", getModel());
        // 设备类型,0：手机1：平板2：电视3：其他
        map.put("DeviceType", "0");
        // Mac地址
        map.put("MacAddress", getMac(context));
        // 系统版本号
        map.put("SYSVersion", String.valueOf(getSDKVersion()));
        // 版本号
        map.put("VersionCode", String.valueOf(getVersionCode(context)));
        // 版本名称
        map.put("VersionName", getVersionName(context));
        // 客户端类型
        map.put("ClientType", "Android");
        // 屏幕信息
        map.put("DisplayMetrics", getDisplayMetrics(context));
        // Root标志0：没有root（未越狱）1：已经root（越狱）
        // map.put("RootFlag", String.valueOf(isRoot()));
        return map;

    }
}
