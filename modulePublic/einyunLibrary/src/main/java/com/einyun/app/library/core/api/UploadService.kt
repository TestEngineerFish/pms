package com.einyun.app.library.core.api

import android.net.Uri
import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.upload.model.PicUrl
import com.einyun.app.library.upload.model.ResourceModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      UploadService
 * @Description:     上传服务
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 16:42
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 16:42
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface UploadService :EinyunService{
    fun uploadImage(filePath: String, callBack: CallBack<PicUrl>): LiveData<PicUrl>
//    fun uploadImage(uri: Uri, callBack: CallBack<ResourceModel>): LiveData<ResourceModel>

    fun uploadImageList(images: List<String>,callBack: CallBack<List<PicUrl>>):LiveData<List<PicUrl>>
//    fun uploadUriList(images: List<Uri>,callBack: CallBack<List<ResourceModel>>):LiveData<List<ResourceModel>>
}