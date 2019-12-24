package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.SerializedName

class ExtenDetialRequest {
    @SerializedName(value = "id", alternate = ["ID_", "bizId"])
    var id: String? = null
    @SerializedName(value = "instId", alternate = ["applyInsId"])
    var instId: String? = null
    @SerializedName(value = "extensionDays", alternate = ["extension_days"])
    var extensionDays: String? = null
    @SerializedName(value = "applicationDescription", alternate = ["application_description"])
    var applicationDescription: String? = null
    @SerializedName(value = "applyFiles")
    var applyFiles: String? = null
    var formData: formDataExten? = formDataExten()
    var divideId: String? = null
    var divideName: String? = null
    var auditType: String? = null
}

class formDataExten() {
    @SerializedName(value = "delay_number", alternate = ["delayNum"])
    var delay_number: String = "0"
    @SerializedName(value = "delay_sum_time", alternate = ["sum"])
    var delay_sum_time: String = "0"
    @SerializedName(value = "delay_time",alternate = ["delayDayNum"])
    var delay_time: String? = null
    var apply_reason: String? = null
    var attachment: String? = null
}
