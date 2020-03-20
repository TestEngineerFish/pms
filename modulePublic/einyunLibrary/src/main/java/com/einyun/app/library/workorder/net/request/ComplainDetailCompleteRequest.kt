package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.workorder.model.ComplainAppendBean
import com.einyun.app.library.workorder.model.CustomerComplainModelBean

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.net.request
 * @ClassName:      ComplainAppendRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 16:46
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 16:46
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ComplainDetailCompleteRequest {
    /**
     * bizData : {"F_ts_property":"一般投诉","F_ts_property_id":"common_complain","F_ts_cate":"环境绿化类","F_ts_cate_id":"environment_virescence","F_line_key":"environmental_classiFication","F_line_name":"环境","F_response_result":"响应时沟通结果"}
     * doNextParam : {"opinion":"此处填写信息会显示在流程历史中的意见一栏","taskId":"66004573147367428"}
     */
    var ID_: String? = null
    var bizData: BizDataBean? = BizDataBean()
    var doNextParam: DoNextParamBean? = DoNextParamBean()

    class BizDataBean {
        var F_pd_assignor_id: String? = null
        /**
         * F_ts_property : 一般投诉
         * F_ts_property_id : common_complain
         * F_ts_cate : 环境绿化类
         * F_ts_cate_id : environment_virescence
         * F_line_key : environmental_classiFication
         * F_line_name : 环境
         * F_response_result : 响应时沟通结果
         */

        var F_ts_property: String? = null
        var F_ts_property_id: String? = null
        var F_ts_cate: String? = null
        var F_ts_cate_id: String? = null
        var F_line_key: String? = null
        var F_line_name: String? = null
        var F_response_result: String? = null
        var F_handle_result: String? = null
        var F_pd_remark: String? = null
        var sub_complain_append: List<ComplainAppendBean>? =
            null
        var service_quality_content: String? = null
        var service_quality_score: Int ?=null
        var F_return_score: Int?=null
        var service_attitude_content: String? = null
        var c_is_solve: Int ?=null
        var F_return_result: String? = null
        var F_pd_assignor: String? = null

    }

    class DoNextParamBean {
        /**
         * opinion : 此处填写信息会显示在流程历史中的意见一栏
         * taskId : 66004573147367428
         */

        var opinion: String? = null
        var taskId: String? = null
    }
}