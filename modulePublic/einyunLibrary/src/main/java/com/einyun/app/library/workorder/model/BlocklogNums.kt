package com.einyun.app.library.workorder.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.model
 * @ClassName:      BlocklogNums
 * @Description:     代表数量
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 11:24
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 11:24
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class BlocklogNums {
    @SerializedName("customer_repair_flow")
    var repairNum:String?=null
    @SerializedName("customer_enquiry_flow")
    var enquiryNum:String?=null
    @SerializedName("customer_complain_flow")
    var complainNum:String?=null
}