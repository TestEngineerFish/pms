package com.einyun.app.base.util;

import android.util.Base64;

public class Base64Util {
    public static String encodeBase64(String origin) {
        return Base64.encodeToString(origin.getBytes(), Base64.DEFAULT);
    }
}
