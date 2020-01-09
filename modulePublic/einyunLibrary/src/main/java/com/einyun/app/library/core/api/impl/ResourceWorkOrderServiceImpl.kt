package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.core.api.proxy.ResourceWorkOrderServiceImplProxy
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.request.*
import com.einyun.app.library.resource.workorder.net.response.ApplyCloseResponse
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api.impl
 * @ClassName:      UCServiceImpl
 * @Description:     UC服务提供者
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 15:35
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 15:35
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ResourceWorkOrderServiceImpl : ResourceWorkOrderService {
    override fun orderListRepair(
        request: DistributePageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        return proxy.orderListPatro(request, callBack)    }

    override fun orderListPatro(
        request: DistributePageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        return proxy.orderListPatro(request, callBack)     }

    override fun orderListPlan(
        request: DistributePageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        return proxy.orderListDistribute(request, callBack)      }

    override fun orderListDistribute(
        request: DistributePageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        return proxy.orderListDistribute(request, callBack)    }

    override fun getOrderPreviewSelect(callBack: CallBack<List<PreviewSelectModel>>): LiveData<List<PreviewSelectModel>> {
        return proxy.getOrderPreviewSelect(callBack)    }

    override fun postApplyDateInfo(
        request: ExtenDetialRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return proxy.postApplyDateInfo(request,callBack)
    }

    override fun getApplyDateInfo(
        id: String,
        callBack: CallBack<formDataExten>
    ): LiveData<formDataExten> {
        return proxy.getApplyDateInfo(id,callBack)
    }

    override fun forceClose(
        workOrderType: String,
        request: ApplyCloseRequest,
        callBack: CallBack<Boolean>
    ) {
       proxy.forceClose(workOrderType,request,callBack)
    }

    override fun postpone(
        workOrderType: String,
        request: ExtenDetialRequest,
        callBack: CallBack<Boolean>
    ) {
        proxy.postpone(workOrderType,request,callBack)
    }

    override fun isClosed(request: IsClosedRequest, callBack: CallBack<Boolean>) {
        proxy.isClosed(request,callBack)
    }


    /**
     *
     * 工作预览-巡查工单
     */
    override fun getPatroPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage> {
        return proxy.getPlanPreviewList(request, callBack)
    }

    /**
     *
     * 工作预览-计划工单
     */
    override fun getPlanPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage> {
        return proxy.getPlanPreviewList(request, callBack)
    }

    override fun planSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>) {
        return proxy.planSubmit(request, callBack)
    }

    override fun planDoneDetial(request: DoneDetialRequest, callBack: CallBack<PlanInfo>) {
        return proxy.planDoneDetial(request, callBack)
    }

    override fun planExten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>> {
        return proxy.planExten(request, callBack)
    }

    override fun planApplyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        return proxy.planApplyClose(request, callBack)
    }

    override fun planOrderDetail(id: String, callBack: CallBack<PlanInfo>) {
        proxy.planOrderDetail(id, callBack)
    }

    override fun planWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<PlanWorkOrderPage>
    ): LiveData<PlanWorkOrderPage> {
        return proxy.planWaitPage(request, callBack)
    }

    override fun planClosedPage(
        request: DistributePageRequest,
        callBack: CallBack<PlanWorkOrderPage>
    ): LiveData<PlanWorkOrderPage> {
        return proxy.planClosedPage(request, callBack)
    }

    override fun patrolDoneDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        proxy.patrolDoneDetial(request, callBack)
    }

    override fun patrolSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>) {
        proxy.patrolSubmit(request, callBack)
    }

    override fun getHistroy(
        intstId: String,
        callBack: CallBack<List<HistoryModel>>
    ): LiveData<List<HistoryModel>> {
        return proxy.getHistroy(intstId, callBack)
    }

    override fun resendOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse> {
        return proxy.resendOrder(request, callBack)
    }
    override fun resendCusOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse> {
        return proxy.resendCusOrder(request, callBack)
    }

    override fun applyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        return proxy.applyClose(request, callBack)
    }
    override fun applyCustomerClose(
        request: ApplyCusCloseRequest,
        midUrl: String,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        return proxy.applyCustomerClose(request,midUrl, callBack)
    }

    override fun exten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>> {
        return proxy.exten(request, callBack)
    }

    override fun distributeReply(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>) {
        proxy.distributeReply(request, callBack)
    }

    override fun distributeCheck(request: DistributeCheckRequest, callBack: CallBack<Boolean>) {
        proxy.distributeCheck(request, callBack)
    }

    override fun getJob(request: GetJobRequest, callBack: CallBack<JobPage>): LiveData<JobPage> {
        return proxy.getJob(request, callBack)
    }

    override fun getOrgnization(
        id: String,
        callBack: CallBack<OrgnizationModel>
    ): LiveData<OrgnizationModel> {
        return proxy.getOrgnization(id, callBack)
    }

    override fun distributeDetial(orderId: String, callBack: CallBack<DisttributeDetialModel>) {
        proxy.distributeDetial(orderId, callBack)
    }

    override fun distributeSubmit(request: DistributeSubmitRequest, callBack: CallBack<Boolean>) {
        proxy.distributeSubmit(request, callBack)
    }

    override fun distributeResponse(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>) {
        proxy.distributeResponse(request, callBack)
    }

    override fun distributeWaitDetial(taskId: String, callBack: CallBack<DisttributeDetialModel>) {
        return proxy.distributeWaitDetial(taskId, callBack)
    }

    override fun distributeDoneDetial(
        request: DoneDetialRequest,
        callBack: CallBack<DisttributeDetialModel>
    ) {
        return proxy.distributeDoneDetial(request, callBack)
    }

    override fun getResourceInfos(
        massifId: String,
        resourceTypeCode: String,
        callBack: CallBack<List<ResourceTypeBean>>
    ): LiveData<List<ResourceTypeBean>> {
        return proxy.getResourceInfos(massifId, resourceTypeCode, callBack)
    }

    override fun getWorkOrderType(callBack: CallBack<List<WorkOrderTypeModel>>): LiveData<List<WorkOrderTypeModel>> {
        return proxy.getWorkOrderType(callBack)
    }

    override fun getTiaoXian(callBack: CallBack<List<ResourceTypeBean>>): LiveData<List<ResourceTypeBean>> {
        return proxy.getTiaoXian(callBack)
    }

    override fun createSendOrder(
        request: CreateSendOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return proxy.createSendOrder(request, callBack)
    }

    override fun patrolPendingDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        proxy.patrolPendingDetial(request, callBack)
    }

    override fun patrolClosedPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        proxy.patrolClosedPage(request, callBack)
    }

    override fun patrolWaitPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        proxy.patrolWaitPage(request, callBack)
    }

    override fun distributeWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage> {
        return proxy.distributeWaitPage(request, callBack)
    }

    override fun distributeDonePage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage> {
        return proxy.distributeDonePage(request, callBack)
    }

    override fun getWaitCount(callBack: CallBack<WaitCount>): LiveData<WaitCount> {
        return proxy.getWaitCount(callBack)
    }

    var proxy: ResourceWorkOrderServiceImplProxy = ResourceWorkOrderServiceImplProxy()

    fun getName(): String {
        return this.javaClass.simpleName
    }
}
