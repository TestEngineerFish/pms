package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.SerializedName

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.net.request
 * @ClassName:      PatrolPageRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 10:48
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 10:48
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class PatrolPageRequest :PageRquest(){
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
    @SerializedName("F_massif_id")
    var divideId: String? = null
    var searchValue: String? = null
}
