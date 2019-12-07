package com.einyun.app.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.einyun.app.common.application.CommonApplication;

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
    public final static String EXTERNAL_CACHE="/mq_external_cache";
    public final static String getFileProviderName(Context context){
        return "com.einyun.app.pms.fileprovider";
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }
        else {
            res = contentUri.getPath();
        }
        return res;
    }

    /**
     * 获取图片上传路径
     * @param uri
     * @return
     */
    public static String getUploadImagePath(Uri uri){
        String filePath = "";
        if (uri.toString().contains(getFileProviderName(CommonApplication.getInstance()))){
            filePath = uri.getPath().replace(EXTERNAL_CACHE , "");
        }else{
            filePath = getRealPathFromURI(CommonApplication.getInstance() , uri);
        }
        return filePath;
    }
}
