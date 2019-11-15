package com.einyun.app.common.utils;

import android.content.Context;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.common.utils
 * @ClassName: FileProviderUtil
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/15 0015 上午 11:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/15 0015 上午 11:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FileProviderUtil {
    public final static String getFileProviderName(Context context){
        return context.getPackageName()+".fileprovider";
    }
}
