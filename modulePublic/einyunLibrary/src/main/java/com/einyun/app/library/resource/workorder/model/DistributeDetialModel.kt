package com.einyun.app.library.resource.workorder.model

import com.einyun.app.library.upload.model.ResourceModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DisttributeDetialModel : Serializable {

    /**
    var buttons: List<DistributeButtonModel>? = null
    var couldScore: Boolean? = null
     **/
    var data: DisttributeMainModel? = null
    var extensionApplication: List<ExtensionApplication>? = null

    /**
    var delayExtensionApplication: DelayExtensionApplication? = null
    var opinionList: List<DistributeOpinionModel>? = null //待定
    var form: String? = null  //待定
    var formResult: String? = null //待定
    var permission: String? = null //待定
     *
     */
}

class DistributeOpinionModel

class DisttributeMainModel : Serializable {

    val id: String? = null
    val refId: String? = null
    val fEnvirmentType2Code: String? = null
    val fEnvirmentType2Name: String? = null
    val fEnvirmentType3Code: String? = null
    val fEnvirmentType3Name: String? = null
    val fDeadlineTime: String? = null
    val fActFinishTime: String? = null
    val fProcDate: String? = null
    val fCheckDate: String? = null
    val fCreateTime: String? = null
    val pgdAttachment: String? = null
    val fOriginalId: String? = null
    val fOriginalType: String? = null
    val fOriginalCode: String? = null
    val fScore: String? = null
    val fExecutorName: String? = null
    val ftitName: String? = null
    val fresId: String? = null
    val fresName: String? = null
    val flocation: String? = null
    val factFinishTime: String? = null
    val fotStatus: Int = 0
    val fextStatus: Int = 0
    val fprocId: String? = null
    val fprocDate: String? = null
    val fprocContent: String? = null
    val fcheckId: String? = null
    val fcheckName: String? = null
    val fcheckDate: String? = null
    val fcheckContent: String? = null
    val fcheckResult: String? = null
    val fbefPic: String? = null
    val faftPic: String? = null
    val fevaluation: String? = null
    val freturnReason: String? = null
    val fhangStatus: Int = 0
    val fcreateTime: Long = 0
    val ftxCode: String? = null
    val fotLevel: Int = 0
    val ftypeName: String? = null
    val fclose: String? = null
    val fprocName: String? = null
    val fdivideId: String? = null
    val fdivideName: String? = null
    val forderNo: String? = null
    val ftxName: String? = null
    val fdeadlineTime: Long = 0
    val fdesc: String? = null
    val fstatus: String? = null
    val fprojectName: String? = null
    val fprojectId: String? = null
    val ftxId: String? = null
    val ftype: String? = null
    val ftitId: String? = null
    var isReply:Int=0
}
