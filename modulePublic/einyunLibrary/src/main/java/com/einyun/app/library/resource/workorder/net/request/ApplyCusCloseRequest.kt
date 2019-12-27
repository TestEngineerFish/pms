package com.einyun.app.library.resource.workorder.net.request

import com.google.gson.annotations.SerializedName

class ApplyCusCloseRequest(
    /**
     * bizData : {"fclose_apply_attach":"sss","fclose_apply_reason":"闭单原因 *:问询"}
     * doNextParam : {"taskId":"66464581324659716"}
     */

    var bizData: BizDataBean?, var doNextParam: DoNextParamBean?
) {


    class DoNextParamBean {
        constructor()

        /**
         * taskId : 66464581324659716
         */

        var taskId: String? = null
    }

    class BizDataBean {
        constructor()

        /**
         * fclose_apply_attach : sss
         * fclose_apply_reason : 闭单原因 *:问询
         */
        @SerializedName(value = "fclose_apply_attach")
        var fclose_apply_attach: String? = null
        var fclose_apply_reason: String? = null
        var F_fclose_apply_reason: String? = null
        var F_fclose_apply_attach: String? = null
        var F_invalid_reason_cate: String? = null
        var F_fclose_apply_invalid: String? = "0"
        var F_invalid_reason_cate_id: String? = null
    }
}

