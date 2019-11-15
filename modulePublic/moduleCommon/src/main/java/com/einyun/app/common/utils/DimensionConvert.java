package com.einyun.app.common.utils;

import android.content.Context;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.component.pageRecyeler
 * @ClassName: DimensionConvert
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/1 0001 上午 11:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/1 0001 上午 11:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DimensionConvert {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue 要转换的dp值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue 要转换的px值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
