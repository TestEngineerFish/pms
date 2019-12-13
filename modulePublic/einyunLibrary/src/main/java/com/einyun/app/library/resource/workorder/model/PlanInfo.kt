package com.einyun.app.library.resource.workorder.model

import com.google.gson.annotations.SerializedName
import java.util.Date

class PlanInfo {
    val buttons: List<Buttons>? = null
    val data: Data? = null
    var extensionApplication: ArrayList<ExtensionApplication>? = null
}

class Buttons {

    val name: String? = null
    val alias: String? = null
    val beforeScript: String? = null
    val afterScript: String? = null
    val groovyScript: String? = null
    val urlForm: String? = null
    val supportScript: Boolean? = null
}

class Sub_jhgdgzjdb {

    var tenant_id: String? = null
    var ref_id_: String? = null
    var F_WK_CONTENT: String? = null
    var F_WK_ID: String? = null
    var id_: String? = null
    var F_WK_NODE: String? = null
    var F_WK_RESULT: String? = null
    var proc_inst_id_: String? = null

}

class Sub_jhgdzyb {

    var F_RES_CODE: String? = null
    var tenant_id: String? = null
    var id_: String? = null
    var F_REMARK: String? = null
    var F_WG_NAME: String? = null
    var F_RES_NAME: String? = null
    var F_RES_COUNT: String? = null
    var proc_inst_id_: String? = null
    var ref_id_: String? = null
    var F_SP_TYPE: String? = null
    var F_PG_ID: String? = null
    var F_RES_LOCATION: String? = null
    var F_RES_TYPE: String? = null

}


/**
 * Auto-generated: 2019-12-13 18:39:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
class Jhgdgzjdb {

    var F_WK_CONTENT: String? = null
    var F_WK_ID: String? = null
    var F_WK_NODE: String? = null
    var F_WK_RESULT: String? = null

}


class Jhgdwlb {

    var F_MAT_REMARK: String? = null
    var F_MAT_NAME: String? = null
    var F_MAT_UNIT_PRICE: String? = null
    var F_MAT_COUNT: String? = null
    var F_MAT_UNIT: String? = null

}

class Jhgdzyb {

    var F_RES_CODE: String? = null
    var F_SP_TYPE: String? = null
    var F_PG_ID: String? = null
    var F_REMARK: String? = null
    var F_RES_LOCATION: String? = null
    var F_WG_NAME: String? = null
    var F_RES_NAME: String? = null
    var F_RES_COUNT: String? = null
    var F_RES_TYPE: String? = null

}

class Data {

    var zyjhgd: Zyjhgd? = null

}

class Zyjhgd {

    var tenant_id: String? = null
    var F_OWNER_NAME: String? = null
    var F_DEADLINE_TIME: String? = null
    var F_WP_ID: String? = null
    var F_OT_STATUS: Int = 0
    var F_PROJECT_ID: String? = null
    var proc_inst_id_: String? = null
    var F_WG_ID: String? = null
    var F_PROJECT_NAME: String? = null
    var F_FILES: String? = null
    var F_OT_HOURS: Int = 0
    var F_OPER_CONTENT: String? = null
    var F_TX_CODE: String? = null
    var reF_ID_: String? = null
    var F_CREATE_TIME: String? = null
    var F_DIVIDE_ID: String? = null
    var F_TIT_NAME: String? = null
    var F_ACT_FINISH_TIME: String? = null
    var F_WP_NAME: String? = null
    var F_PROCESS_NAME: String? = null
    var F_LOCATION: String? = null
    var F_PROCESS_ID: String? = null
    var F_OWNER_ID: String? = null
    var F_TX_ID: String? = null
    var id_: String? = null
    var F_STATUS: String? = null
    var F_CONTENT: String? = null
    var F_RES_NAME: String? = null
    var F_WG_NAME: String? = null
    var F_RES_ID: String? = null
    var F_HANG_STATUS: String? = null
    var F_DIVIDE_NAME: String? = null
    var F_TIT_ID: String? = null
    var F_TX_NAME: String? = null
    var F_ORDER_NO: String? = null
    var F_EXT_STATUS: Int = 0
    var sub_jhgdgzjdb: List<Sub_jhgdgzjdb>? = null
    var sub_jhgdwlb: List<String>? = null
    var sub_jhgdzyb: List<Sub_jhgdzyb>? = null
    var initData: InitDataCopy? = null

}

class InitDataCopy {
    var jhgdgzjdb: Jhgdgzjdb? = null
    var jhgdwlb: Jhgdwlb? = null
    var jhgdzyb: Jhgdzyb? = null

}