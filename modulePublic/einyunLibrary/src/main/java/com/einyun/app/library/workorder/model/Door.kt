package com.einyun.app.library.workorder.model

import java.io.Serializable

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.model
 * @ClassName:      Outdoor
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 17:31
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 17:31
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
 class Door : Serializable {
    var id: String? = null
    var parentId: String? = null
    var categoryId: String? = null
    var dataName: String? = null
    var dataKey: String? = null
    var expand: Expand? = null
    var enabledFlag: String? = null
    var sn: Int? = 0
    var children: List<Door>? = null
}

class Expand {
    var line_posi_key: String? = null
    var majorLine: MajorLine? = null
    var line_posi_name: String? = null
    var code_str: String? = null
    var repair_area: RepairArea? = null
}

class MajorLine {
    var name: String? = null
    var key: String? = null
}

class RepairArea {
    var name: List<String>? = null
    var key: List<String>? = null
}