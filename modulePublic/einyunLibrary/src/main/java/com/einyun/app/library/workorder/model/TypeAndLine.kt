package com.einyun.app.library.workorder.model

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.model
 * @ClassName:      TypeAndLine
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 16:35
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 16:35
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class TypeAndLine {
    var dataKey: String? = null
    var line_posi_key: String? = null
    var line_posi_name: String? = null
    var code_str: String? = null
    var dataName: String? = null
    var id: String? = null
    var majorLine: MajorLineBean? = null
    var repair_area: RepairAreaBean? = null
    var flow_type: FlowTypeBean? = null
}

class RepairAreaBean {
    var name: List<String>? = null
    var key: List<String>? = null
}

class FlowTypeBean {
    var name: List<String>? = null
    var key: List<String>? = null
}

class MajorLineBean {
    /**
     * name : 工程
     * key : engineering_classification
     */

    var name: String? = null
    var key: String? = null
}