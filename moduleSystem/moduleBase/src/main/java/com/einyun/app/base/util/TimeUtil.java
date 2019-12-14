package com.einyun.app.base.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arvin on 2016/2/2 16:40.
 * 时间转换相关方法
 */
@SuppressWarnings("ALL")
public class TimeUtil {
    public static final long minute = 60 * 1000; //分钟
    public static final long hour = 60 * minute; //小时
    public static final long day = 24 * hour;    //天
    public static final long week = 7 * day;     //周
    public static final long month = 31 * day;   //月
    public static final long year = 12 * month;  //年

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String Now() {
        return getAllTime(getCurrentTime());
    }

    /**
     * 将time转换为 1970-1-1 00:00:00 格式的时间
     */
    public static String getAllTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }


    /**
     * 将time转换为 1970-1-1 00:00:00 格式的时间
     *
     * @param time    时间戳
     * @param is_msec 时间戳 是否是到毫秒
     * @return
     */
    public static String getAllTime(long time, boolean is_msec) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(is_msec ? time : time * 1000);
        return format.format(now);
    }

    /**
     * 将time转换为 1970-1-1 00:00:00 格式的时间
     */
    public static String getAllTimeNoSecond(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 根据时间获取毫秒数
     *
     * @param time    时间字符串
     * @param formate 时间格式
     * @return
     */
    public static long getTime(String time, String formate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formate);
            Date d = sdf.parse(time);
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long ymdToLong(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYMdTime(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYMdTime(long time, boolean is_msec) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(is_msec ? time : time * 1000);
        return format.format(now);
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYMdTimeDot(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1-1 格式的时间
     */
    public static String getMDTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00 格式的时间
     */
    public static String getHmTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00:00 格式的时间
     */
    public static String getHmsTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00:00 格式的时间
     */
    public static String getMsTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }


    /**
     * 获取年纪
     */
    public static int getAge(long time) {
        if (time <= 0) {
            return 0;
        }
        Date birthday = new Date();
        birthday.setTime(time);
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 获取音视频通话时间格式
     *
     * @param time 秒
     * @return
     */
    public static String getVoiceTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }


    /**
     * 比较广告开始时间
     *
     * @param beginTime
     * @return
     */
    public static boolean isAdTiemBegin(String beginTime) {
        try {
            long begin_time = getTime(beginTime, "yyyy-MM-dd HH:mm:ss");//2018-05-29 00:00:00
            long time = System.currentTimeMillis();
            if (time > begin_time) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 比较广告结束时间
     *
     * @param endTime
     * @return
     */
    public static boolean isAdTiemEnd(String endTime) {
        try {
            long end_time = getTime(endTime, "yyyy-MM-dd HH:mm:ss");//2018-05-29 00:00:00
            long time = System.currentTimeMillis();
            if (time < end_time) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 把字符串型时间戳 转化为long型
     *
     * @param time
     * @return
     */
    public static long getStrToLong(String time) {
        try {
            return Long.valueOf(time).longValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间戳
     *
     * @param is_ms 是否到毫秒
     * @return
     */
    public static long currentTimeMillis(boolean is_ms) {
        if (is_ms) {
            return System.currentTimeMillis();
        } else {
            return System.currentTimeMillis() / 1000;
        }
    }

    public static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (Exception e) {
            Log.e("shmshmshm", "e = " + e);
        }
        return returnMillis;
    }

    public static final long l = 60 * 60 * 1000 * 24;
    public static final long l2 = 60 * 60 * 1000;
    public static final long l3 = 60 * 1000;

    public static String getTimeExpend(String startTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = format.format(new Date());
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        if (longExpend < 0) {
            longExpend = 0;
        }

        long longDay = longExpend / l;
        long long1 = longExpend - longDay * l;
        long longHours = long1 / l2; //根据时间差来计算小时数
        long long2 = long1 - longHours * l2;
        long longMinutes = long2 / l3;   //根据时间差来计算分钟数
        long longSecond = (long2 - longMinutes * l3) / 1000;
        return longDay + " 天 " + longHours + " 时 " + longMinutes + " 分" + longSecond + "秒";
    }

    public static String getTimeExpend(String startTime,String endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        if (longExpend < 0) {
            longExpend = 0;
        }

        long longDay = longExpend / l;
        long long1 = longExpend - longDay * l;
        long longHours = long1 / l2; //根据时间差来计算小时数
        long long2 = long1 - longHours * l2;
        long longMinutes = long2 / l3;   //根据时间差来计算分钟数
        long longSecond = (long2 - longMinutes * l3) / 1000;
        return longDay + " 天 " + longHours + " 时 " + longMinutes + " 分" + longSecond + "秒";
    }
}
