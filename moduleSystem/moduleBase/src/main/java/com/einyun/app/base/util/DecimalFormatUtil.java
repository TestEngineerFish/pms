package com.einyun.app.base.util;

import java.text.DecimalFormat;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.util
 * @ClassName: DecimalFormatUtil
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/4 0004 上午 11:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/4 0004 上午 11:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DecimalFormatUtil {
    public static DecimalFormat format = new DecimalFormat(",##0");//千分位保留2位小数
    public static DecimalFormat format_2xs = new DecimalFormat(",##0.00");//千分位保留2位小数

    public static String format(Object src){
        return format.format(src);
    }

    public static String format2xs(Object src){
        return format_2xs.format(src);
    }
}
