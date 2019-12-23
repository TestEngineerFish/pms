package com.einyun.app.library.workorder.net

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.Query
import com.einyun.app.library.resource.workorder.net.response.RepairsResponse
import com.einyun.app.library.resource.workorder.net.response.TiaoXianResponse
import com.einyun.app.library.workorder.model.ComplainPage
import com.einyun.app.library.workorder.model.Door
import com.einyun.app.library.workorder.model.DoorResult
import com.einyun.app.library.workorder.model.RepairsDetailModel
import com.einyun.app.library.workorder.net.request.*
import com.einyun.app.library.workorder.net.response.*
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.net
 * @ClassName:      WorkOrderServiceApi
 * @Description:     WorkOrder net api
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 10:56
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 10:56
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface WorkOrderServiceApi {
    /**
     * 获取抢单数量
     */
    @GET(URLs.URL_GRAP_ORDER_NUMS)
    fun getGrapCount(): Flowable<GrapCountResponse>

    /**
     * 获取待审批数量
     */
    @GET(URLs.URL_APPROVAL_PENDING_NUMS)
    fun getAuditCount(): Flowable<AuditCountResponse>

    /**
     * 获取待办数量（客户报修，客户询问，客户投诉）
     */
    @POST(URLs.URL_BACKLOG_NUMS)
    fun getBlockLogNums(@Body request: Query): Flowable<BlocklogNumsResponse>

    /**
     *  客户投诉列表 待跟进
     */
    @POST(URLs.URL_COMPLAIN_FLOW_PENDING)
    fun complainPendingPage(@Body request: Query): Flowable<ComplainListResponse>

    /**
     *  客户投诉列表 待反馈
     */
    @POST(URLs.URL_COMPLAIN_FEEDBACK_PENDING)
    fun complainFeedbackPendingPage(@Body request: Query): Flowable<ComplainListResponse>

    /**
     *  客户投诉列表 已跟进
     */
    @POST(URLs.URL_COMPLAIN_FLOWED)
    fun complainFlowedPage(@Body request: Query): Flowable<ComplainListResponse>

    /**
     *  客户投诉列表 已办结
     */
    @POST(URLs.URL_COMPLAIN_COMPLETE)
    fun complainCompletedPage(@Body request: Query): Flowable<ComplainListResponse>

    /**
     *  客户投诉列表 抄送我的
     */
    @POST(URLs.URL_COMPLAIN_COPY)
    fun complainCopy2MePage(@Body request: Query): Flowable<ComplainListResponse>

    /**
     *  客户投诉列表
     */
    @POST(URLs.URL_COMPLAIN_LIST)
    fun complainCustomerFlowPage(@Body request: Query): Flowable<ComplainFlowListResponse>

    /**
     *   根据参数（如：手机号）查询处理中的投诉列表
     */
    @POST(URLs.URL_COMPLAIN_WORK_LIST)
    fun complainWorkListdPage(@Body request: Query): Flowable<ComplainListResponse>

    /**
     *  获取投诉、问询工单类别与条线
     */
    @GET(URLs.URL_BIZDATA_BASE_LIST)
    fun typeAndLineList(): Flowable<TypeAndLineListResponse>

    /**
     * 追加投诉
     */
    @POST(URLs.URL_COMPLAIN_SUBMIT)
    fun appendComplain(@Body request: ComplainAppendRequest): Flowable<BaseResponse<Any>>

    /**
     * 启动投诉
     */
    @POST(URLs.URL_TASK_RUN_START)
    fun startComplain(@Body request: CreateClientComplainOrderRequest): Flowable<BaseResponse<Any>>

    /**
     * 获取报修类别与条线
     */
    @GET(URLs.URL_REPAIR_TYPE_MAP_LIST)
    fun repairTypeList(): Flowable<BaseResponse<Door>>

    /**
     * 启动报修
     */
    @POST(URLs.URL_CUSTOMER_REPAIR_SUBMIT)
    fun startRepair(@Body request: CreateClientRepairOrderRequest): Flowable<BaseResponse<Any>>

    /**
     * 启动问询
     */
    @POST(URLs.URL_CUSTOMER_ENQUIRY_SUBMIT)
    fun startEnquiry(@Body request: CreateClientEnquiryOrderRequest): Flowable<BaseResponse<Any>>

    /**
     *  审批列表 待审批
     */
    @POST(URLs.URL_APPROVE_TODO_LIST)
    fun approvePendingList(@Body request: Query): Flowable<ApproveListResponse>

    /**
     *  审批列表 已审批
     */
    @POST(URLs.URL_APPROVE_DONE_LIST)
    fun approveDoneList(@Body request: Query): Flowable<ApproveListResponse>

    /**
     *  审批列表 我发起的
     */
    @POST(URLs.URL_APPROVE_INITIATED_LIST)
    fun approveMyList(@Body request: Query): Flowable<ApproveListResponse>

    /**
     *  通过UserId批量查询待处理工单
     */
    @POST(URLs.URL_GET_MAPPING_BY_USERIDS)
    fun getMappingByUserIds(@Body request: List<String>): Flowable<BaseResponse<Map<String, GetMappingByUserIdsResponse>>>
    /**
     * 客户报修-待跟进
     * */
    @POST(URLs.URL_REPORT_REPAIRS_WAIT_FOLLOW)
    fun getRepairsWaitFollow(@Body request: Query):Flowable<RepairsResponse>
    /**
     * 客户报修-抢单
     * */
    @POST(URLs.URL_REPORT_REPAIRS_GRAB)
    fun getRepairsGrab(@Body request: Query):Flowable<RepairsResponse>

    /**
     * 客户报修-已跟进
     * */
    @POST(URLs.URL_REPORT_REPAIRS_ALREADY_FOLLOW)
    fun getRepairsAlreadyFollow(@Body request: Query):Flowable<RepairsResponse>

    /**
     * 客户报修-已办结
     * */
    @POST(URLs.URL_REPORT_REPAIRS_ALREADY_DONE)
    fun getRepairAlreadyDone(@Body request: Query):Flowable<RepairsResponse>
    /**
     * 客户报修-抄送我
     * */
    @POST(URLs.URL_REPORT_REPAIRS_COPY_ME)
    fun getRepairCopyMe(@Body request: Query):Flowable<RepairsResponse>
    /**
     * 客户报修-抄送我
     * */
    @POST(URLs.URL_REPORT_REPAIRS_WAIT_FEED)
    fun getRepairWaitFeed(@Body request: Query):Flowable<RepairsResponse>

    /**
     * 客户报修-抢单动作
     * */
    @GET
    fun grabRepair(@Url url: String):Flowable<BaseResponse<Any>>

    /**
     * 客户报修-查看详情
     * */
    @GET
    fun getRepairDetail(@Url url: String):Flowable<RepairDetailResponse>

    /**
     * 客户报修-派单
     * */
    @POST(URLs.URL_REPAIR_SEND)
    fun repairSend(@Body request: RepairSendOrderRequest):Flowable<BaseResponse<Any>>

    /**
     * 客户报修-处理保存
     * */
    @POST(URLs.URL_REPAIR_HANDLE_SAVE)
    fun repairHandleSave(@Body request:SaveHandleRequest ):Flowable<BaseResponse<Any>>

    @POST(URLs.URL_INITIATE_COMMUNICATION)
    fun postCommunication(@Body request:PostCommunicationRequest):Flowable<BaseResponse<Any>>

    /**
     * 客户报修-待跟进
     * */
    @POST(URLs.URL_REPORT_COMPLAIN_WAIT_FOLLOW)
    fun getComplainWaitFollow(@Body request: Query):Flowable<BaseResponse<ComplainPage>>
    /**
     * 客户报修-待反馈
     * */
    @POST(URLs.URL_COMPLAIN_FEEDBACK_PENDING)
    fun getComplainWaitFeed(@Body request: Query):Flowable<BaseResponse<ComplainPage>>
    /**
     * 客户报修-已跟进
     * */
    @POST(URLs.URL_REPORT_COMPLAIN_ALREADY_FOLLOW)
    fun getComplainAlreadyFollow(@Body request: Query):Flowable<BaseResponse<ComplainPage>>

    /**
     * 客户报修-已办结
     * */
    @POST(URLs.URL_REPORT_COMPLAIN_ALREADY_DONE)
    fun getComplainAlreadyDone(@Body request: Query):Flowable<BaseResponse<ComplainPage>>
    /**
     * 客户报修-抄送我
     * */
    @POST(URLs.URL_REPORT_COMPLAIN_COPY_ME)
    fun getComplainCopyMe(@Body request: Query):Flowable<BaseResponse<ComplainPage>>

    /**
     * 客户报修-筛选
     * */
    @GET(URLs.URL_REPAIR_SELECT)
    fun getAreaType():Flowable<AreaResponse>

    @GET(URLs.URL_CLIENT_DETAIL)
    fun getClientOrderDetail(@retrofit2.http.Query("procInstId") procInstId: String, @retrofit2.http.Query("taskId") taskId: String): Flowable<BaseResponse<RepairsDetailModel>>

    @POST(URLs.URL_COMPLAIN_DETAIL_COMPLETE)
    fun complainDetailComplete(@Body request: ComplainDetailCompleteRequest):Flowable<BaseResponse<Any>>

    @POST(URLs.URL_COMPLAIN_DETAIL_SAVE)
    fun complainDetailSave(@Body request: ComplainDetailCompleteRequest):Flowable<BaseResponse<Any>>
}