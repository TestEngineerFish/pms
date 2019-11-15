package com.einyun.app.library.workorder.model

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
class Door {
    private var id: String? = null
    private var parentId: String? = null
    private var categoryId: String? = null
    private var dataName: String? = null
    private var dataKey: String? = null
    private var expand: Expand? = null
    private var sn: Int = 0
    private var children: List<Door>? = null
}

class Expand{
    private var line_posi_key: String? = null
    private var majorLine: MajorLine? = null
    private var line_posi_name: String? = null
    private var code_str: String? = null
    private var repair_area: RepairArea? = null
}

class MajorLine{
    private var name: String? = null
    private var key: String? = null
}

class RepairArea{
    private var name: List<String>? = null
    private var key: List<String>? = null
}