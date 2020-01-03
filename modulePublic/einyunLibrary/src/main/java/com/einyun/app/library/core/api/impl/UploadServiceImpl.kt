package com.einyun.app.library.core.api.impl

import android.net.Uri
import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UploadService
import com.einyun.app.library.core.api.proxy.UploadServiceImplProxy
import com.einyun.app.library.upload.model.PicUrl
import com.einyun.app.library.upload.model.ResourceModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api.impl
 * @ClassName:      UploadServiceImpl
 * @Description:    上传服务提供者
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 16:42
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 16:42
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UploadServiceImpl:UploadService{
    var proxy=UploadServiceImplProxy()

    /**
     * 上传图片
     */
    override fun uploadImage(filePath: String, callBack: CallBack<PicUrl>): LiveData<PicUrl> {
        return proxy.uploadImage(filePath,callBack)
    }


    override fun uploadImageList(images: List<String>, callBack: CallBack<List<PicUrl>>): LiveData<List<PicUrl>> {
        return proxy.uploadImageList(images,callBack)
    }

}