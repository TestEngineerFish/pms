package com.einyun.app.library.dashboard.repository

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.EinyunException
import com.einyun.app.library.core.api.EinyunService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.DashBoardServiceApi
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import io.reactivex.functions.Consumer

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.dashboard.repository
 * @ClassName:      DashBoardRepo
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/11/4 0004 上午 11:22
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/11/4 0004 上午 11:22
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class DashBoardRepo {
    var serviceApi: DashBoardServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(DashBoardServiceApi::class.java)
    }

    //工单处理情况总览
    fun workOrder(request: WorkOrderRequest, callBack: CallBack<WorkOrderData>) {
        serviceApi?.workOrder(request)?.compose(RxSchedulers.inIoMain())
                ?.subscribe(
                        Consumer { response ->
                            if (response.isState) {
                                callBack.call(response.data)
                            } else {
                                callBack.onFaild(EinyunHttpException(response))
                            }
                        },
                        Consumer {
                            callBack.onFaild(it)
                        }
                )
    }

}