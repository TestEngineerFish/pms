package com.einyun.app.library.resource.workorder.net

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.portal.dictdata.net.URLS
import com.einyun.app.library.resource.workorder.model.ApplyCloseModel
import com.einyun.app.library.resource.workorder.model.PlanWorkOrder
import com.einyun.app.library.resource.workorder.net.request.*
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
    fun createSendOrder(@Body request: CreateSendOrderRequest): Flowable<BaseResponse<Any>>

    /**
     *  2.6待办统计-计划、巡查、派工单 GET
     */
    @GET(URLs.URL_RESOURCE_WORKORDER_WIAIT_COUNT)
    fun getWaitCount(): Flowable<WaitCountResponse>

    /**
     *   2.7计划工单-待办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_WAIT_PROCESS_LIST)
    fun planWaitPage(@Body request: DistributePageRequest): Flowable<PlanListResponse>

    /**
     * 2.14计划工单-已办
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PLAN_DONE)
    fun planDonePage(@Body request: DistributePageRequest): Flowable<PlanListResponse>

    /**
     * 计划工单详详情
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PLAN_LIST_DETIAL)
    fun planOrderDetail(@Body request: PatrolDetialRequest):Flowable<PlanDetialResponse>

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
    fun patrolPendingDetial(@Body request: PatrolDetialRequest): Flowable<PatrolDetialResponse>

    /**
     * 巡查已办工单详情
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PATROL_DONE_DETAIL)
    fun patrolDoneDetial(@Body request: PatrolDetialRequest): Flowable<PatrolDetialResponse>

    /**
     * 派工单处理
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PATROL_SUBMIT)
    fun patrolSubmit(@Body request: PatrolSubmitRequest):Flowable<BaseResponse<Any>>

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
     * 派工单待办详情
     */
    @GET
    fun distributeWaitDetial(@Url url: String): Flowable<DistributeWorkOrderResponse>

    /**
     * 派工单待办详情-info
     */
    @GET
    fun distributeWaitDetialInfo(@Url url: String): Flowable<DistributeWorkOrderResponse>

    /**
     * 派工单已办详情
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_DONE_DETAIL)
    fun distributeDoneDetial(@Body request: DoneDetialRequest): Flowable<DistributeWorkOrderResponse>

    /**
     * 计划工单已办详情
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_PLAN_DONE_DETAIL)
    fun planDoneDetial(@Body request: DoneDetialRequest): Flowable<PlanDetialResponse>

    /**
     * 派工单接单
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_RESPONSE)
    fun distribteResponse(@Body request: WorkOrderHanlerRequest): Flowable<BaseResponse<Any>>

    /**
     * 派工单批复
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_REPLY)
    fun distribteReply(@Body request: WorkOrderHanlerRequest): Flowable<BaseResponse<Any>>

    /**
     * 派工单处理
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_PROCESS)
    fun distributeSumbmit(@Body request: DistributeSubmitRequest): Flowable<BaseResponse<Any>>

    /**
     * 派工单验收
     */
    @POST(URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_APPLY)
    fun distributeCheck(@Body request: DistributeCheckRequest): Flowable<BaseResponse<Any>>

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

    /**
     *获取组织架构
     **/
    @GET
    fun getOrgnization(@Url url: String): Flowable<GetOrgnizationResponse>

    /**
     * 获取审批角色
     */
    @POST(URLs.URL_SELECT_BY_JOB)
    fun getJob(@Body request: GetJobRequest): Flowable<GetJobResponse>

    @POST(URLs.URL_EXTEN)
    fun exten(@Body request: ExtenDetialRequest): Flowable<BaseResponse<Object>>

    @POST(URLs.URL_PLAN_EXTEN)
    fun extenPlan(@Body request: ExtenDetialRequest): Flowable<BaseResponse<Object>>

    /**
     * 申请闭单
     * */
    @POST(URLs.URL_PLAN_CLOSE_ORDER)
    fun closeOrderPlan(@Body request:ApplyCloseRequest):Flowable<ApplyCloseResponse>

    /**
     * 转派
     */
    @POST(URLs.URL_RESEND_ORDER)
    fun resendOrder(@Body request:ResendOrderRequest):Flowable<ResendOrderResponse>

    /**
     * 申请闭单
     * */
    @POST(URLs.URL_CLOSE_ORDER)
    fun closeOrder(@Body request:ApplyCloseRequest):Flowable<ApplyCloseResponse>

    /**
     * 获取历史流程
     * */
    @GET
    fun getHistroy(@Url url: String):Flowable<HistroyResponse>

    /**
     * 工作预览-计划工单
     * */
    @POST(URLs.URL_WORK_PREVIEW_PLAN_ORDER)
    fun getPlanPreviewOrders(@Body request: OrderPreviewRequest):Flowable<OrderPreviewResponse>

    /**
     * 工作预览-巡查工单
     * */
    @POST(URLs.URL_WORK_PREVIEW_PATRO_ORDER)
    fun getPatroPreviewOrders(@Body request: OrderPreviewRequest):Flowable<OrderPreviewResponse>
}
