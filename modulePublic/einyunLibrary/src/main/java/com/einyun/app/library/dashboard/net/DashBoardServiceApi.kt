package com.einyun.app.library.dashboard.net

import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.dashboard.net.response.WorkOrderResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.dashboard.net
 * @ClassName:      DashBoardServiceApi
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/11/4 0004 上午 11:23
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/11/4 0004 上午 11:23
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
interface DashBoardServiceApi {
    @POST(URLs.URL_DASHBOARD_WORK_ORDER_DATA)
    fun workOrder(@Body request: WorkOrderRequest):Flowable<WorkOrderResponse>
}