package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.Expose
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
class DistributePageRequest :PageRquest(){
    fun resetConditions() {
        divideId=""
        type=null
        fState=null
        envType2=null
        envType3=null
    }

    @SerializedName("F_DIVIDE_ID")
    var divideId:String?=""
    @SerializedName("F_TX_ID")
    var txId:String?=null
    @SerializedName("F_TYPE")
    var type:String?=null
    @SerializedName("F_STATUS")
    var fState:String?=null
    @SerializedName("F_CHECK_ID")
    var checkId:String?=null
    @SerializedName("F_ENVIRMENT_TYPE2_CODE")
    var envType2:String?=null
    @SerializedName("F_ENVIRMENT_TYPE3_CODE")
    var envType3:String?=null
    @SerializedName("F_OT_STATUS")
    var otStatus: String? = null
    @Expose
    var typeRe: String? = null
    var showTotal: Int? = null

}
