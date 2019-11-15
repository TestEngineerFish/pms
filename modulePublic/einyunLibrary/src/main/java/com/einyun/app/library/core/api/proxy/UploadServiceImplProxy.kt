package com.einyun.app.library.core.api.proxy

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.UploadService
import com.einyun.app.library.upload.model.ResourceModel
import com.einyun.app.library.upload.repository.FileResRepository

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api.proxy
 * @ClassName:      UploadServiceImplProxy
 * @Description:     上传服务代理类
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 16:43
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 16:43
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UploadServiceImplProxy :UploadService{
    var instance:UploadService?=null
    constructor(){
        instance=FileResRepository()
    }
    override fun uploadImage(filePath: String, callBack: CallBack<ResourceModel>): LiveData<ResourceModel> {
       return instance?.uploadImage(filePath,callBack)!!
    }
}