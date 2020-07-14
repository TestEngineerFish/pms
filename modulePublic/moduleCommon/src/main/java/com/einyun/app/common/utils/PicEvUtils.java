package com.einyun.app.common.utils;

public class PicEvUtils {
    public static String getBaseUrl(String type) {
        switch (type) {
            case "debug":
                return "\"https://testbms.einyun.com\"";
            case "uat":
                return "\"http://uatbms.einyun.com\"";
            case "release":
                return "\"https://bms.einyun.com\"";
            default:
                return "";
        }
    }

    public static String getBaseFeeUrl(String type) {
        switch (type) {
            case "debug":
                return "\"https://testbms.einyun.com\"";
            case "uat":
                return "\"http://uatbms.einyun.com\"";
            case "release":
                return "\"https://fee.einyun.com\"";
            default:
                return "";
        }
    }

    public static String getFeeMidUrl(String type) {
                return "\"/fee-center-api\"";
    }
}
