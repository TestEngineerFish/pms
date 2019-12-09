package com.einyun.app.pms.approval.utils;

public class IsFastClick {
    public  static long lastClickTime;
    //防止重复点击 事件间隔，在这里我定义的是1000毫秒
    public static boolean isFastDoubleClick(){
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 1000) {
            return false;
        } else {
            lastClickTime = time;
            return true;
        }
    }
}
