package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.workorder.model.RepairsDetailModel

class RepairSendOrderRequest {

    var bizData: RepairsDetailModel.DataBean.CustomerRepairModelBean? = null
    var doNextParam: DoNextParamBean? = null

   constructor(
      bizData: RepairsDetailModel.DataBean.CustomerRepairModelBean?,
      doNextParam: DoNextParamBean?
   ) {
      this.bizData = bizData
      this.doNextParam = doNextParam
   }

    class DoNextParamBean {
        var taskId: String? = null

        constructor(taskId: String?) {
            this.taskId = taskId
        }
    }
}
