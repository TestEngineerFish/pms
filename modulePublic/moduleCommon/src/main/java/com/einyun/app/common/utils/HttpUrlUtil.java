package com.einyun.app.common.utils;

import com.einyun.app.library.core.net.EinyunHttpService;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.common.utils
 * @ClassName: HttpUrlUtil
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/25 10:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/25 10:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HttpUrlUtil {
    private final static String MEDIA_PATH="/media";
    public static String getImageServerUrl(String path){
        String imageUrl=EinyunHttpService.getInstance().getBaseUrl() +MEDIA_PATH+ File.separator+path;
        Logger.d("imageUrl->"+imageUrl);
        return imageUrl;
    }
    public static String getImageLogoUrl(String path){
        String imageUrl=EinyunHttpService.getInstance().getBaseUrl() + File.separator+path;
        Logger.d("imageUrl->"+imageUrl);
        return imageUrl;
    }
}
