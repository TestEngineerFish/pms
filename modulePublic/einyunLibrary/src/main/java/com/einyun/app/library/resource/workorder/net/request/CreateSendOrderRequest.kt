package com.einyun.app.library.resource.workorder.net.request

import com.einyun.app.base.paging.bean.PageBean
import com.google.gson.annotations.SerializedName

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.net.request
 * @ClassName:      DistributePageRequest
 * @Description:     派工单
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 11:03
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 11:03
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CreateSendOrderRequest {
    var id: String? = null
    var orderNo: String? = null
    var F_ORIGINAL_CODE: String? = null
    @SerializedName("F_DIVIDE_ID")
    var divideId: String? = null
    @SerializedName("F_DIVIDE_CODE")
    var divideCode: String? = null
    @SerializedName("F_DIVIDE_NAME")
    var divideName: String? = null
    @SerializedName("F_PROC_ID")
    var procId: String? = null
    @SerializedName("F_PROC_NAME")
    var procName: String? = null
    @SerializedName("F_TX_ID")
    var txId: String? = null
    @SerializedName("F_TX_NAME")
    var txName: String? = null
    @SerializedName("F_TX_CODE")
    var txCode: String? = null
    @SerializedName("F_RES_ID")
    var resId: String? = null
    @SerializedName("F_RES_NAME")
    var resName: String? = null
    @SerializedName("F_TYPE")
    var type: String? = null
    @SerializedName("F_TYPE_NAME")
    var typeName: String? = null
    @SerializedName("F_ENVIRMENT_TYPE2_CODE")
    var envType2Code: String? = null
    @SerializedName("F_ENVIRMENT_TYPE2_NAME")
    var envType2Name: String? = null
    @SerializedName("F_ENVIRMENT_TYPE3_CODE")
    var envType3Code: String? = null
    @SerializedName("F_ENVIRMENT_TYPE3_NAME")
    var envType3Name: String? = null
    @SerializedName("F_OT_LEVEL")
    var otLevel: String? = null
    @SerializedName("F_DESC")
    var desc: String? = null
    @SerializedName("F_LOCATION")
    var location: String? = null
    @SerializedName("F_PROJECT_ID")
    var projectId: String? = null
    @SerializedName("F_PROJECT_NAME")
    var projectName: String? = null
    @SerializedName("pgd_attachment")
    var imageList: String? = null

}