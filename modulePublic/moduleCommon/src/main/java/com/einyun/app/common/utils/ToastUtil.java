package com.einyun.app.common.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import java.lang.ref.WeakReference;


/**
 * Description: Toast显示工具类
 *
 * @Author: yaozhjheng
 * @Date: 2018/11/6
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 显示Toast消息
     * @param msg   消息
     */
    public static void show(Context context, String msg) {
        WeakReference<Context> contextWeakReference=new WeakReference<>(context);
        if(TextUtils.isEmpty(msg)){
            return;
        }
        if(contextWeakReference.get()==null){
            return;
        }
        if (toast == null) {
            if(Looper.getMainLooper().getThread() != Thread.currentThread()){
                Looper.prepare();
            }
            toast = Toast.makeText(contextWeakReference.get().getApplicationContext(), msg, Toast.LENGTH_LONG);
        } else {
            if(Looper.getMainLooper().getThread() != Thread.currentThread()){
                Looper.prepare();
            }
            toast.setText(msg);
        }
        toast.show();
    }


    /**
     * 显示Toast消息
     * @param resId   消息资源id
     */
    public static void show(Context context, int resId) {
        show(context, context.getString(resId));
    }


}
