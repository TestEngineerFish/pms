package com.einyun.app.library.resource.workorder.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DisttributeDetialModel :Serializable{


    var buttons : List<DistributeButtonModel>?=null
    var couldScore : Boolean?=null
    var data : DistributeData?=null

    var opinionList : List<DistributeOpinionModel> ?= null //待定
    var form : String?= null  //待定
    var formResult : String?= null //待定
    var permission : String?= null //待定
    var extensionApplication: ArrayList<ExtensionApplication>? = null

    /**
    var delayExtensionApplication: DelayExtensionApplication? = null
    var opinionList: List<DistributeOpinionModel>? = null //待定
    var form: String? = null  //待定
    var formResult: String? = null //待定
    var permission: String? = null //待定
     *
     */

    /**
     * 获取强制逼单或申请延期信息
     */
    fun getExtApplication(applyType:Int): ExtensionApplication? {
        if(extensionApplication==null||extensionApplication?.size==0){
            return null
        }
        for(ext in extensionApplication!!){
            if(applyType==ext.applyType){
                return ext
            }
        }
        return null
    }
}

class DistributeData :Serializable{
    @SerializedName("cshjpgdjm")
    var info:DisttributeMainModel?=null
}
class DistributeButtonModel :Serializable{
    var afterScript : String?=null
    var alias : String?=null
    var beforeScript : String?=null
    var groovyScript : String?=null
    var name : String?=null
    var supportScript : Boolean?=null
    var urlForm : String?=null
}

class DistributeOpinionModel :Serializable

class DisttributeMainModel :Serializable {

    @SerializedName("F_ACT_FINISH_TIME")
    var actFinishTime: String? = null //实际完成时间
    @SerializedName("F_CHECK_DATE")
    var checkDate: String? = null //验收日期
    @SerializedName("F_CREATE_TIME")
    var createTime: String? = null//工单创建时间
    @SerializedName("F_DEADLINE_TIME")
    var deadLineTime: String? = null //完成截止时间
    @SerializedName("F_PROC_DATE")
    var procDate: String? = null //处理时间
    @SerializedName("F_AFT_PIC")
    var aftPic: String? = null//处理后照片
    @SerializedName("F_BEF_PIC")
    var befPic: String? = null//处理前照片
    @SerializedName("F_CHECK_CONTENT")
    var checkContent: String? = null //验收意见
    @SerializedName("F_CHECK_ID")
    var checkID: String? = null//验收人id
    @SerializedName("F_CHECK_NAME")
    var checkName: String? = null//验收人名称
    @SerializedName("F_CHECK_RESULT")
    var checkResult: String? = null //验收结果
    @SerializedName("F_DESC")
    var desc: String? = null//问题描述
    @SerializedName("F_DIVIDE_ID")
    var divideID: String? = null//分期id
    @SerializedName("F_DIVIDE_NAME")
    var divideName: String? = null////分期名称
    @SerializedName("F_TYPE")
    var type: String? = null //工单类型 （一级）
    @SerializedName("F_TYPE_NAME")
    var typeName: String? = null //工单名称 （一级）
    @SerializedName("F_ENVIRMENT_TYPE2_CODE")
    var envirmentType2Code: String? = null
    @SerializedName("F_ENVIRMENT_TYPE2_NAME")
    var envirmentType2Name: String? = null
    @SerializedName("F_ENVIRMENT_TYPE3_CODE")
    var envirmentType3Code: String? = null
    @SerializedName("F_ENVIRMENT_TYPE3_NAME")
    var envirmentType3Name: String? = null
    @SerializedName("F_EVALUATION")
    var evaluation: String? = null//评价
    @SerializedName("F_EXECUTOR_NAME")
    var executorName: String? = null
    @SerializedName("F_EXT_STATUS")
    var extStatus: Int? = null//是否延期
    @SerializedName("F_HANG_STATUS")
    var hangStatus: Int? = null//是否挂起
    @SerializedName("F_LOCATION")
    var location: String? = null//位置
    @SerializedName("F_ORDER_NO")
    var orderNo: String? = null//工单编号
    @SerializedName("F_ORIGINAL_CODE")
    var originalCode: String? = null
    @SerializedName("F_ORIGINAL_ID")
    var originalID: String? = null
    @SerializedName("F_ORIGINAL_TYPE")
    var originalType: String? = null
    @SerializedName("F_OT_LEVEL")
    var otLevel: Int? = null//超时级别 1:普通,2:一般,3:严重
    @SerializedName("F_OT_STATUS")
    var otStatus: Int? = null//是否超时
    @SerializedName("F_PROC_CONTENT")
    var procContent: String? = null //处理内容
    @SerializedName("F_PROC_ID")
    var procID: String? = null //处理人id
    @SerializedName("F_PROC_NAME")
    var procName: String? = null //处理人名称
    @SerializedName("F_PROJECT_ID")
    var projectID: String? = null//项目id
    @SerializedName("F_PROJECT_NAME")
    var projectName: String? = null//项目名称
    @SerializedName("F_RES_ID")
    var resID: String? = null//资源id
    @SerializedName("F_RES_NAME")
    var resName: String? = null//资源名称
    @SerializedName("F_RETURN_REASON")
    var returnReason: String? = null//退回原因
    @SerializedName("F_SCORE")
    var score: String? = null
    @SerializedName("F_STATUS")
    var status: String? = null//1:待响应，2:处理中，3:验收中，4:已完成
    @SerializedName("F_TIT_ID")
    var titID: String? = null//岗位id
    @SerializedName("F_TIT_NAME")
    var titName: String? = null//岗位名称
    @SerializedName("F_TX_CODE")
    var txCode: String? = null//条线Code
    @SerializedName("F_TX_ID")
    var txID: String? = null //条线id
    @SerializedName("F_TX_NAME")
    var txName: String? = null ////条线名称
    @SerializedName("id_")
    var ID: String? = null//工单id
    @SerializedName("pgd_attachment")
    var pgdAttachment: String? = null//创建派工单时的图片
    @SerializedName("proc_inst_id_")
    var procInstID: String? = null //实例ID
    @SerializedName("ref_id_")
    var refID: String? = null
    @SerializedName("tenant_id")
    var tenantID: String? = null
    var close: String? = null
    var isReply:Int=0
}
