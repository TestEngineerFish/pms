package com.einyun.app.base.util;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc     Activity事件管理 跳转尽量使用该事件管理，尽量不要使用intent flag 等操作
 * author   zhangmeng
 * time     2017/3/21 14:50
 */
public class ActivityUtil {
    /**
     * activity堆栈式管理
     */
    private static List<AppCompatActivity> activityList = new ArrayList<>();
    /**
     * 数据
     */
    private static Map<Class<?>, Object> dataMap = new HashMap<>();
    /**
     * 返回数据
     */
    private static Map<Class<?>, ResultBack> backMap = new HashMap<>();
    /**
     * 当前 Activity 的 class
     */
    private static Class currentClass;
    /**
     * 默认首页
     */
    private static Class defaultClass;

    public static void setDefaultClass(Class className) {
        defaultClass = className;
    }

    private static final int enterAnima = 0;
    private static final int exitAnima = 0;

    /**
     * 跳转页面
     *
     * @param from 起始activity
     * @param to   转向的activity
     */
    public static void startActivity(Activity from, Class<?> to) {
        startActivity(from, to, null, enterAnima, exitAnima);
    }

    /**
     * 跳转页面
     *
     * @param from 起始activity
     * @param to   转向的activity
     * @param data 传递的数据
     */
    public static void startActivity(Activity from, Class<?> to, Object data) {
        startActivity(from, to, data, enterAnima, exitAnima);
    }

    /**
     * 跳转页面
     *
     * @param from       起始activity
     * @param to         转向的activity
     * @param data       传递的数据
     * @param enterAnima 进入动画id
     * @param exitAnima  退出动画id
     */
    public static void startActivity(Activity from, Class<?> to, Object data,
                                     int enterAnima, int exitAnima) {
        if (dataMap == null) {
            dataMap = new HashMap<>();
        }
        if (data != null) {
            dataMap.put(to, data);
        }
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        if (enterAnima != 0 && exitAnima != 0) {
            from.overridePendingTransition(enterAnima, exitAnima);
        }
    }


    /**
     * 跳回之前已经打开的页面
     *
     * @param from       起始activity
     * @param to         转向的activity
     * @param data       新的数据
     * @param enterAnima 进入动画id
     * @param exitAnima  退出动画id
     */
    public static void startExitActivity(Activity from, Class<?> to, Object data,
                                         int enterAnima, int exitAnima) {
        if (isExit(to)) {
            finishToActivity(to);
        } else {
            finishToActivity(defaultClass);
            startActivity(from, to, data, enterAnima, exitAnima);
        }
    }

    public static boolean isExit(Class<?> cls) {
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    private static Activity getLastActivty() {
        if (activityList.size() > 0) {
            return activityList.get(activityList.size() - 1);
        }
        return null;
    }

    /**
     * 跳回之前已经打开的页面
     *
     * @param from 起始activity
     * @param to   转向的activity
     * @param data 新的数据
     */
    public static void startExitActivity(Activity from, Class<?> to, Object data) {
        startExitActivity(from, to, data, enterAnima, exitAnima);
    }

    /**
     * 跳转之前已经打开的页面
     *
     * @param from 起始activity
     * @param to   转向的activity
     */
    public static void startExitActivity(Activity from, Class<?> to) {
        startExitActivity(from, to, dataMap.get(to), enterAnima, exitAnima);
    }


    /**
     * 获取上个页面传递过来的 map 数据
     *
     * @return
     */
    public static Map<String, Object> getMapData() {
        return getMapData(currentClass);
    }

    public static Map<String, Object> getMapData(Class cls) {
        if (dataMap.get(cls) != null && dataMap.get(cls) instanceof Map) {
            return (Map<String, Object>) dataMap.get(cls);
        }
        return new HashMap<>();
    }

    /**
     * 获取上个页面传递过来的 map 中的Object数据
     *
     * @return 不存在返回 null
     */
    public static Object getObjectData(String key) {
        return getMapData().get(key);
    }

    public static Object getObjectData() {
        if (dataMap.get(currentClass) != null) {
            return dataMap.get(currentClass);
        }
        return null;
    }

    public static Object getObjectData(Class cls, String key) {
        return getMapData(cls).get(key);
    }

    /**
     * 获取上个页面传递的String数据
     *
     * @param key 数据对应的key
     * @return 默认返回 null
     */
    public static String getStringData(String key) {
        return getStringData(currentClass, key);
    }

    /**
     * 参数只有一个Int
     *
     * @return
     */
    public static String getStringData() {
        if (dataMap.get(currentClass) != null && dataMap.get(currentClass) instanceof String) {
            return dataMap.get(currentClass).toString();
        }
        return null;
    }

    public static String getStringData(Class activityClass, String key) {
        String result = null;
        try {
            result = getMapData(activityClass).get(key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取上个页面传递的 int 数据
     *
     * @param key 数据对应的key
     * @return 默认返回0
     */
    public static int getIntData(String key) {
        return getIntData(currentClass, key);
    }

    /**
     * 参数只有一个Int
     *
     * @return
     */
    public static int getIntData() {
        if (dataMap.get(currentClass) != null && dataMap.get(currentClass) instanceof Integer) {
            return (int) dataMap.get(currentClass);
        }
        return 0;
    }

    public static int getIntData(Class activityClass, String key) {
        Object obj = getMapData(activityClass).get(key);
        if (obj != null && obj instanceof Integer) {
            return (int) obj;
        } else {
            return 0;
        }
    }

    /**
     * 获取上个页面传递的 boolean 数据
     *
     * @param key 数据对应的key
     * @return 默认返回 false
     */
    public static boolean getBooleanData(String key) {
        return getBooleanData(currentClass, key);
    }

    public static boolean getBooleanData(Class activityClass, String key) {
        Object obj = getMapData(activityClass).get(key);
        if (obj != null && obj instanceof Boolean)
            return (boolean) obj;
        else
            return false;
    }

    /**
     * 跳转页面
     *
     * @param from   起始activity
     * @param to     转向的activity
     * @param result 回调
     */
    public static void startActivityWithResult(Activity from, Class<?> to, ResultBack result) {
        startActivityWithResult(from, to, null, enterAnima, exitAnima, result);
    }

    /**
     * 跳转页面
     *
     * @param from   起始activity
     * @param to     转向的activity
     * @param data   传递的数据
     * @param result 回调
     */
    public static void startActivityWithResult(Activity from, Class<?> to, Object data, ResultBack result) {
        startActivityWithResult(from, to, data, enterAnima, exitAnima, result);
    }


    /**
     * 跳转页面
     *
     * @param from       起始activity
     * @param to         转向的activity
     * @param data       传递的数据
     * @param enterAnima 进入动画id
     * @param exitAnima  退出动画id
     */
    public static void startActivityWithResult(Activity from, Class<?> to, Object data,
                                               int enterAnima, int exitAnima, ResultBack result) {
        backMap.put(from.getClass(), result);
        startActivity(from, to, data, enterAnima, exitAnima);
    }

    /**
     * 移除当前 activity
     *
     * @param cls remove的activity的class
     */
    public static void removeExceptCurrentActivity(Class cls) {
        if (activityList.size() == 1) {
            return;
        }
        currentClass = null;
        for (int i = 0; i > activityList.size() - 2; i++) {
            finish(activityList.get(i));
        }
        if (getLastActivty() != null) {
            currentClass = getLastActivty().getClass();
        }
    }

    /**
     * 移除当前 activity
     *
     * @param cls remove的activity的class
     */
    public static void removeActivity(Class cls) {
        currentClass = null;
        for (int i = activityList.size() - 1; i >= 0; i--) {
            if (activityList.get(i) != null && activityList.get(i).getClass().equals(cls)) {
                activityList.remove(i);
                break;
            }
        }
        if (getLastActivty() != null) {
            currentClass = getLastActivty().getClass();
        }
        if (dataMap.get(cls) != null) {
            dataMap.remove(cls);
        }
        if (backMap.get(cls) != null) {
            backMap.remove(cls);
        }
    }

    public static void addActivity(AppCompatActivity activity) {
        currentClass = activity.getClass();
        activityList.add(activity);
    }

    /**
     * finish 所有 activity
     */
    public static void finishActivitys() {
        for (Activity activity : activityList) {
            finish(activity);
        }
        activityList.clear();
        dataMap.clear();
        backMap.clear();
        activityList = null;
        dataMap = null;
        backMap = null;
    }

    /**
     * finish 到 cls
     */
    public static void finishToActivity(Class<?> cls) {
        if (isExit(cls)) {
            while (true) {
                if (getLastActivty() != null) {
                    Activity activity = getLastActivty();
                    if (activity.getClass().equals(cls)) {
                        break;
                    } else {
                        finish(activity);
                    }
                } else {
                    break;
                }
            }
        } else if (isExit(defaultClass)) {
            finishToActivity(defaultClass);
        } else {
            finishActivitys();
        }
    }


    /**
     * 结束回调并传递数据
     *
     * @param data 传递数据
     */
    public static void finishWithData(Object data) {
        if (getLastActivty() != null) {
            finish(getLastActivty());//结束当前页面
            if (getLastActivty() != null) {
                Class cls = getLastActivty().getClass();
                if (backMap.get(cls) != null) {
                    backMap.get(cls).onBack(data);
                }
            }
        }
    }

    /**
     * 结束回调并传递数据
     *
     * @param data 传递数据
     */
    public static void finishToWithData(Class to, Object data) {
        finishToActivity(to);
        if (getLastActivty() != null) {
            Class cls = getLastActivty().getClass();
            if (backMap.get(cls) != null) {
                backMap.get(cls).onBack(data);
            }
        }
    }

    /**
     * finish 当前activity
     */
    private static void finish(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }


    public interface ResultBack<T> {
        void onBack(T data);
    }
}
