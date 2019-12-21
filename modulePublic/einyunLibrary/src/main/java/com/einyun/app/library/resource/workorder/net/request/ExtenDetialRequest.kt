package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.SerializedName

class ExtenDetialRequest {
    @SerializedName(value = "id",alternate = ["ID_"])
    var id:String?=null
    @SerializedName(value = "instId")
    var instId:String?=null
    @SerializedName(value = "extensionDays",alternate = ["extension_days"])
    var extensionDays:String?=null
    @SerializedName(value = "applicationDescription",alternate = ["application_description"])
    var applicationDescription:String?=null
    @SerializedName(value = "applyFiles")
    var applyFiles:String?=null
}
