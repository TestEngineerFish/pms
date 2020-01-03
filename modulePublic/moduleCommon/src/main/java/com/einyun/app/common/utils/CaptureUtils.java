package com.einyun.app.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.service.RouterUtils;
import java.io.File;
import java.lang.ref.WeakReference;

public class CaptureUtils {
    public static File startCapture(Activity activity) {
        File imageFile=null;
        WeakReference<Activity> contextWeakReference=new WeakReference<>(activity);
        Activity weakContext=contextWeakReference.get();
        if(weakContext!=null){
           imageFile = new File(Environment.getExternalStorageDirectory(), DataConstants.PROVIDE_DCIM + System.currentTimeMillis() + DataConstants.PREX_JPP);
            if (!imageFile.getParentFile().exists()) imageFile.getParentFile().mkdirs();
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(weakContext, DataConstants.DATA_PROVIDER_NAME, imageFile));
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            weakContext.startActivityForResult(intent, RouterUtils.ACTIVITY_REQUEST_CAMERA_OK);
        }
        return imageFile;
    }
}
