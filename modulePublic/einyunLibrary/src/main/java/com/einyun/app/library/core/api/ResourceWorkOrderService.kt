package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.request.*
import com.einyun.app.library.resource.workorder.net.response.ApplyCloseResponse
import com.einyun.app.library.resource.workorder.net.response.HistroyResponse
import com.einyun.app.library.resource.workorder.net.response.OrderPreviewResponse
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse
import com.einyun.app.library.resource.workorder.net.response.*
import io.reactivex.Flowable
import retrofit2.http.Url
import java.util.*
import java.util.*

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      UCService
 * @Description:    UC服务接口，高级抽象，约束
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 14:46
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 14:46
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface ResourceWorkOrderService : EinyunService {
    //巡查工单代办
    fun planWaitPage(request: DistributePageRequest, callBack: CallBack<PlanWorkOrderPage>): LiveData<PlanWorkOrderPage>

    //巡查工单已办
    fun planClosedPage(request: DistributePageRequest, callBack: CallBack<PlanWorkOrderPage>): LiveData<PlanWorkOrderPage>

    fun getWaitCount(callBack: CallBack<WaitCount>): LiveData<WaitCount>

    fun distributeWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage>

    //派工单代办列表
    fun distributeDonePage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage>

    //巡查工单代办
    fun patrolWaitPage(request: PatrolPageRequest, callBack: CallBack<PatrolWorkOrderPage>)

    //巡查工单已办
    fun patrolClosedPage(request: PatrolPageRequest, callBack: CallBack<PatrolWorkOrderPage>)

    //巡查工单详情
    fun patrolPendingDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>)

    //巡查工单详情
    fun patrolDoneDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>)

    //巡查工单处理
    fun patrolSubmit(request: PatrolSubmitRequest,callBack: CallBack<Boolean>)

    //创建派工单
    fun createSendOrder(
        request: CreateSendOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    //获取派工单代办详情
    fun distributeWaitDetial(taskId: String, callBack: CallBack<DisttributeDetialModel>)

    //获取派工单已办详情
    fun distributeDetial(orderId: String, callBack: CallBack<DisttributeDetialModel>)

    //获取派工单已办详情
    fun distributeDoneDetial(request: DoneDetialRequest, callBack: CallBack<DisttributeDetialModel>)

    //派工单接单
    fun distributeResponse(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>)

    //派工单批复
    fun distributeReply(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>)

    //派工单验收
    fun distributeCheck(request: DistributeCheckRequest, callBack: CallBack<Boolean>)

    //派工单处理
    fun distributeSubmit(request: DistributeSubmitRequest, callBack: CallBack<Boolean>)

    //获取条线
    fun getTiaoXian(callBack: CallBack<List<ResourceTypeBean>>): LiveData<List<ResourceTypeBean>>

    //获取工单类型
    fun getWorkOrderType(callBack: CallBack<List<WorkOrderTypeModel>>): LiveData<List<WorkOrderTypeModel>>

    fun getResourceInfos(
        massifId: String,
        resourceTypeCode: String,
        callBack: CallBack<List<ResourceTypeBean>>
    ): LiveData<List<ResourceTypeBean>>

    //获取组织结构
    fun getOrgnization(id: String, callBack: CallBack<OrgnizationModel>): LiveData<OrgnizationModel>
    //获取审批角色

    fun getJob(request: GetJobRequest, callBack: CallBack<JobPage>): LiveData<JobPage>

    //转派工单
    fun resendOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse>
    //客服类转派工单
    fun resendCusOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse>

    //申请闭单
    fun applyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse>
    //申请闭单
    fun applyCustomerClose(
        request: ApplyCusCloseRequest,
        midUrl: String,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse>

    fun exten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>>

    //申请闭单
    fun planApplyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse>

    fun planExten(request: ExtenDetialRequest, callBack: CallBack<BaseResponse<Object>>): LiveData<BaseResponse<Object>>


    //历史流程
    fun getHistroy(
        intstId: String,
        callBack: CallBack<List<HistoryModel>>
    ): LiveData<List<HistoryModel>>

    //工作预览计划工单列表
    fun getPlanPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage>

    //工作预览计划工单列表
    fun getPatroPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage>

    //计划工单详情
    fun planOrderDetail(taskId:String,callBack: CallBack<PlanInfo>)

    //获取计划工单已办详情
    fun planDoneDetial(request: DoneDetialRequest, callBack: CallBack<PlanInfo>)

    fun planSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>)

    /**
     * 通用强制闭单
     */
    fun forceClose(workOrderType:String,request: ApplyCloseRequest,callBack: CallBack<Boolean>)

    /**
     * 通用申请延期
     */
    fun postpone(workOrderType:String,request: ExtenDetialRequest,callBack: CallBack<Boolean>)

    /**
     * 判断 当前的工单 是否可以申请闭单 或者 是否可以申请延期
     *返回true可以发起审批，返回false表示当前正在审批，不可再次申请
     */
    fun isClosed(request:IsClosedRequest,callBack: CallBack<Boolean>)

}
