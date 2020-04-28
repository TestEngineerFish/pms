package com.einyun.app.common.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * desc
 * author   zhangmeng
 * time     2017/3/29 20:41
 */

public class FormatUtil {
    /**
     * 手机号星显
     *
     * @param phone
     * @return
     */
    public static String formatPhoneWithStar(String phone) {
        if (phone.startsWith("+86")) {
            phone = phone.substring(3, phone.length());
        }
        if (phone.length() == 11) {
            return phone.substring(0, 3) + " **** " + phone.substring(7, 11);
        }
        return phone;
    }
    /**
     * 判断时间是不是今天
     * @param date
     * @return
     */
    public static boolean isToday(Date date){
        Date now = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        String nowDay = sim.format(now);
        String day = sim.format(date);
        return day.equals(nowDay);

    }
    /**
     * 将日期转化为标准格式
     *
     * @param time
     * @return
     */
    public static String formatDate(Long time) {
        Date date = new Date(time);

        String formatDate = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            formatDate = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
        /**
         * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
         *
         * @param dateDate
         * @return
         */
        public static String dateToStrLong (Date dateDate){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(dateDate);
            return dateString;
        }

        /**
         * @param card 银行卡号
         * @return 加密的银行卡号
         */
        public static String formatCardWithStar (String card){
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(card)) {
                for (int i = 0; i < card.length(); i++) {
                    char c = card.charAt(i);
                    if (i > 3 && i <= (card.length() - 4)) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }
            }
            return sb.toString();
        }

        public static String formatAmount (String amount){
            try {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                return decimalFormat.format(Double.parseDouble(amount));
            } catch (Exception e) {
                Log.w("formatAmount", "金额转换出错");
                return amount;
            }
        }

        public static String formatAmountInt (String amount){
            try {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                return decimalFormat.format(Integer.parseInt(amount));
            } catch (Exception e) {
                Log.w("formatAmount", "金额转换出错");
                return amount;
            }
        }

        public static String formatAmountInt ( double amount){
            try {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                return decimalFormat.format(amount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return String.valueOf(amount);
            }
        }

        public static String formatAmount ( double amount){
            try {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                return decimalFormat.format(amount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return String.valueOf(amount);
            }
        }

        /**
         * 保留小数点后两位
         *
         * @return
         */
        public static String formatPointTwo ( double number){
            try {
                DecimalFormat decimalFormat = new DecimalFormat("###0.00");
                return decimalFormat.format(number);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return String.valueOf(number);
            }
        }
    }
