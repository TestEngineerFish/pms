package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.SerializedName

class ExtenDetialRequest {
    @SerializedName(value = "id")
    var id: String? = null
    var ID_:String?=null
    var bizId: String? = null
    @SerializedName(value = "instId")
    var instId: String? = null
    var applyInsId: String? = null
    var extension_days: String? = null
    var application_description:String?=null
    @SerializedName(value = "extensionDays")
    var extensionDays: String? = null
    @SerializedName(value = "applicationDescription")
    var applicationDescription: String? = null
    @SerializedName(value = "applyFiles")
    var applyFiles: String? = null
    var formData: formDataExten? = null
    var divideId: String? = null
    var divideName: String? = null
    var auditType: String? = null
}


class formDataExten{
    @SerializedName(value = "delay_number", alternate = ["delayNum"])
    var delay_number: String = "0"
    @SerializedName(value = "delay_sum_time")
    var delay_sum_time: String = "0"
    var sum :String = "0"
    @SerializedName(value = "delay_time", alternate = ["delayDayNum"])
    var delay_time: String? = null
    var apply_reason: String? = null
    var attachment: String? = null
}

