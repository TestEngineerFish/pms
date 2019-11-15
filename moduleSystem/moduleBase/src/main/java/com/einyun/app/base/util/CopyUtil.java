package com.einyun.app.base.util;

import android.content.Context;
import android.text.ClipboardManager;

/**
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
public class CopyUtil {
    /**
     * 复制到剪贴板
     *
     * @param context
     * @param text
     */
    public static void copy(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
    }
}
