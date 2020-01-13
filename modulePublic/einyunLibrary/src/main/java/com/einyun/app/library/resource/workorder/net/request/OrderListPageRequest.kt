package com.einyun.app.library.resource.workorder.net.request

import com.einyun.app.base.paging.bean.Query
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
class OrderListPageRequest : PageRquest(), Cloneable {
    fun resetConditions() {
        txId = null
        type = null
        fState = null
        envType2 = null
        envType3 = null
    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Any {
        return super.clone()
    }

    @SerializedName("F_DIVIDE_ID")
    var divideId: String? = null
    @SerializedName("F_TX_ID")
    var txId: String? = null
    @SerializedName("F_TYPE")
    var type: String? = null
    @SerializedName("F_STATUS")
    var fState: String? = null
    @SerializedName("F_CHECK_ID")
    var checkId: String? = null
    @SerializedName("F_ENVIRMENT_TYPE2_CODE")
    var envType2: String? = null
    @SerializedName("F_ENVIRMENT_TYPE3_CODE")
    var envType3: String? = null
    @SerializedName("F_OT_STATUS")
    var otStatus: String? = null
    var searchValue: String? = null
    var bx_dk_id: String? = null
    var bx_area_id: String? = null
    var bx_cate_lv1_id: String? = null
    var bx_cate_lv2_id: String? = null
    var state: String? = null
    var node_id_: String? = null
    var owner_id_: String? = null
    var bx_time: String? = null
    var DESC: String? = Query.SORT_DESC
    var tag: String? = null
    @SerializedName("F_is_time_out")
    var timeout:String?=null
    @SerializedName("F_grid_id")
    var gridId:String?=null
    @SerializedName("F_building_id")
    var buildingId:String?=null
    @SerializedName("F_unit_id")
    var unitId:String?=null
    @SerializedName("F_floor")
    var floor:String?=null
    @SerializedName("F_type_id")
    var typeId:String?=null
    var ts_time: String? = null
    var ts_dk_id: String? = null
    var F_ts_cate_id: String? = null
    var F_ts_property_id: String? = null
    @SerializedName("F_massif_id")
    var dividePatroId: String? = null
    @SerializedName("F_line_code")
    var txPatroId:String?=null
}
