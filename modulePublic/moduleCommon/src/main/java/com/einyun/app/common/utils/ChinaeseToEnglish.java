package com.einyun.app.common.utils;

import android.text.TextUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

public class ChinaeseToEnglish {
    /**
     * 根据字符串获取当前首字母
     *
     * @param name
     * @return
     */
    public static String getLetter(String name) {
        String DefaultLetter = "#";
        if (TextUtils.isEmpty(name)) {
            return DefaultLetter;
        }
        char char0 = name.toLowerCase().charAt(0);
        if (Character.isDigit(char0)) {
            return String.valueOf(char0);
        }
        ArrayList<HanziToPinyin.Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
        if (l != null && l.size() > 0 && l.get(0).target.length() > 0) {
            HanziToPinyin.Token token = l.get(0);
            // toLowerCase()返回小写， toUpperCase()返回大写
            String letter = token.target.substring(0, 1).toLowerCase();
            char c = letter.charAt(0);
            // 这里的 'a' 和 'z' 要和letter的大小写保持一直。
            if (c < 'a' || c > 'z') {
                return DefaultLetter;
            }
            return letter;
        }
        return DefaultLetter;
    }

}


