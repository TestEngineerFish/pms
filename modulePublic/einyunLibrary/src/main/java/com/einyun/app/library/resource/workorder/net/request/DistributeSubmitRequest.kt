package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.SerializedName

class DistributeSubmitRequest {
    var id:String?=null
    var taskId:String?=null
    @SerializedName("F_AFT_PIC")
    var afterPic:String?=null
    @SerializedName("F_PROC_DATE")
    var procTime:String?=null
    @SerializedName("F_PROC_CONTENT")
    var procConeten:String?=null
    @SerializedName("joint_processor")
    var joint_processor:String?=null
}
