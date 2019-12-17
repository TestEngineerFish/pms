package com.einyun.app.library.workorder.net.request

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
class EnquiryOrderData {
    @SerializedName("wx_dk_id")
    var divideId: String? = null
    @SerializedName("wx_dk")
    var divideName: String? = null
    @SerializedName("state")
    var state: String? = "added"
    @SerializedName("wx_mobile")
    var mobile: String? = null
    @SerializedName("wx_user")
    var userName: String? = null
    @SerializedName("wx_way_id")
    var wayId: String? = null
    @SerializedName("wx_cate_id")
    var cateId: String? = null
    @SerializedName("line_key")
    var lineKey: String? = null
    @SerializedName("line_name")
    var lineName: String? = null
    @SerializedName("wx_build_id")
    var buildId: String? = null
    @SerializedName("wx_house_id")
    var houseId: String? = null
    @SerializedName("wx_unit_id")
    var unitId: String? = null
    @SerializedName("wx_house")
    var house: String? = null
    @SerializedName("wx_content")
    var content: String? = null
    @SerializedName("wx_way")
    var way: String? = null
    @SerializedName("wx_cate")
    var cate: String? = null
    @SerializedName("wx_attachment")
    var imageList: String? = null
}

class vars {
    @SerializedName("stagingCode")
    var divideCode: String? = null
}

class startFlowParamObject {
    var vars: vars? =
        vars()
    var flowKey: String? = "customer_enquiry_flow"
}

class CreateClientEnquiryOrderRequest {
    var withModelKey: Boolean? = false
    var bizData: EnquiryOrderData? =
        EnquiryOrderData()
    var startFlowParamObject: startFlowParamObject? =
        startFlowParamObject()
}

class CreateClientComplainOrderRequest {
    var withModelKey: Boolean? = false
    var bizData: ComplainOrderData? =
        ComplainOrderData()
    var startFlowParamObject: startFlowParamObject? =
        startFlowParamObject()
}

class ComplainOrderData{
    @SerializedName("F_ts_dk_id")
    var divideId: String? = null
    @SerializedName("F_ts_dk")
    var divideName: String? = null
    @SerializedName("F_state")
    var state: String? = "added"
    @SerializedName("F_ts_mobile")
    var mobile: String? = null
    @SerializedName("F_ts_user")
    var userName: String? = null
    @SerializedName("F_ts_way_id")
    var wayId: String? = null
    @SerializedName("F_ts_cate_id")
    var cateId: String? = null
    @SerializedName("F_line_key")
    var lineKey: String? = null
    @SerializedName("F_line_name")
    var lineName: String? = null
    @SerializedName("F_ts_build_id")
    var buildId: String? = null
    @SerializedName("F_ts_house_id")
    var houseId: String? = null
    @SerializedName("F_ts_unit_id")
    var unitId: String? = null
    @SerializedName("F_ts_house")
    var house: String? = null
    @SerializedName("F_ts_content")
    var content: String? = null
    @SerializedName("F_ts_way")
    var way: String? = null
    @SerializedName("F_ts_cate")
    var cate: String? = null
    @SerializedName("F_ts_attachment")
    var imageList: String? = null
    @SerializedName("F_ts_property_id")
    var propertyId: String? = null
    @SerializedName("F_ts_property")
    var propertyName: String? = null
}