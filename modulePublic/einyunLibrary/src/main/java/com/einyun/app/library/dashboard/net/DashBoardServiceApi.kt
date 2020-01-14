package com.einyun.app.library.dashboard.net

import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.AllChargedRequest
import com.einyun.app.library.dashboard.net.request.RateRequest
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.dashboard.net.response.AllChargedResponse
import com.einyun.app.library.dashboard.net.response.OperateCaptureResponse
import com.einyun.app.library.dashboard.net.response.UserMenuResponse
import com.einyun.app.library.dashboard.net.response.WorkOrderResponse
import com.einyun.app.library.portal.dictdata.net.URLS
import com.einyun.app.library.portal.dictdata.net.response.DictListResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @POST(URLs.URL_DASHBOARD_OPERATE_CAPTURE_RATE)
    fun operateCapture(@Body orgCodes: RateRequest):Flowable<OperateCaptureResponse>

    /**
     * 根据分类key获取字典
     * @param typeKey
     * @return
     */
    @GET(URLs.URL_DASHBOARD_USER_MENU)
    fun userMenu(@Query("menuType") menuType: Int): Flowable<UserMenuResponse>

    @POST(URLs.URL_ALL_CHARGED_PROJECT)
    fun getAllCharged(@Body request: AllChargedRequest):Flowable<AllChargedResponse>
}