package com.einyun.app.library.upload.model

import com.google.gson.annotations.Expose

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.upload.model
 * @ClassName: ResourceModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/11 15:20
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/11 15:20
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class ResourceModel{
    var fileId:String?=null
    var fileName:String?=null
    var filePath:String?=null
    var size:Long?=null
    var success:Boolean?=null
    @Expose
    var key:String?=null
}
