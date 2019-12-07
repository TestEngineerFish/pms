package com.einyun.app.library.resource.workorder.net

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.resource.workorder.net.request.*
import androidx.room.FtsOptions
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest
import com.einyun.app.library.resource.workorder.net.request.PageRquest
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest
import com.einyun.app.library.resource.workorder.net.response.*
import io.reactivex.Flowable
import retrofit2.http.*
import java.util.*

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.resource
 * @ClassName: ResourceWorkOrderServiceApi
 * @Description: 资源工单接口
 *              请参考《资源工单接口文档》
 * @Author: chumingjun
 * @CreateDate: 2019/10/18 09:45
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/18 09:45
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
interface ResourceWorkOrderServiceApi {

    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_NEW)
    fun createSendOrder(@Body request: CreateSendOrderRequest): Flowable<BaseResponse<Object>>

    /**
     *  2.6待办统计-计划、巡查、派工单 GET
     */
    @GET(URLs.URL_RESOURCE_WORKORDER_WIAIT_COUNT)
    fun getWaitCount(): Flowable<WaitCountResponse>

    /**
     *   2.7计划工单-待办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_WAIT_PROCESS_LIST)
    fun planWaitPage(@Body request: PageRquest): Flowable<PlanListResponse>

    /**
     * 2.14计划工单-已办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PLAN_DONE)
    fun planDonePage(@Body request: PageRquest): Flowable<PlanListResponse>

    /**
     * 2.54巡查工单-待办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PATROL_WAIT)
    fun patrolWaitPage(@Body request: PatrolPageRequest): Flowable<PatrolListResponse>

    /**
     * 2.55巡查工单-已办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PATROL_DONE)
    fun patrolDonePage(@Body request: PatrolPageRequest): Flowable<PatrolListResponse>

    /**
     * 巡查工单详情
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PATROL_WAIT_DETAIL)
    fun patrolDetial(@Body request: PatrolDetialRequest): Flowable<PatrolDetialResponse>

    /**
     * 2.31派工单-待办列表
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_WAIT)
    fun distributeWaitPage(@Body request: DistributePageRequest): Flowable<DistributeListResponse>

    /**
     * 2.31派工单-已办列表
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_DONE)
    fun distributeDonePage(@Body request: DistributePageRequest): Flowable<DistributeListResponse>

    /**
     * 2.30派工单-列表（根据地块id、条线编码）
     */

    /**
     *获取条线
     **/
    @GET(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_TIAOXIAN)
    fun getTiaoXian(): Flowable<TiaoXianResponse>

    /**
     *获取工单类型
     **/
    @GET(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_ORDER_TYPE)
    fun getOrderType(): Flowable<OrderTypeResponse>

    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_DISPATCH)
    fun getResourceInfos(@Body request: ResourceTypeRequest): Flowable<BaseResponse<List<ResourceTypeBean>>>
}
