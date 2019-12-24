package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.workorder.model.RepairsDetailModel

class SaveHandleRequest {
    var ID_: String? = null
    var bizData: RepairsDetailModel.DataBean.CustomerRepairModelBean? = null

    constructor(ID_: String?, bizData: RepairsDetailModel.DataBean.CustomerRepairModelBean?) {
        this.ID_ = ID_
        this.bizData = bizData
    }
}
