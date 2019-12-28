package com.einyun.app.library.core.api.proxy

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.request.*
import com.einyun.app.library.resource.workorder.net.response.ApplyCloseResponse
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.core.api.proxy
 * @ClassName: UCSericeImplProxy
 * @Description: UC服务代理类
 *                单一原则，开闭原则，李氏置换原则
 * @Author: chumingjun
 * @CreateDate: 2019/09/26 16:04
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/26 16:04
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class ResourceWorkOrderServiceImplProxy : ResourceWorkOrderService {
    override fun getOrderPreviewSelect(callBack: CallBack<List<PreviewSelectModel>>): LiveData<List<PreviewSelectModel>> {
        return instance?.getOrderPreviewSelect(callBack)!!    }

    override fun getApplyDateInfo(
        id: String,
        callBack: CallBack<formDataExten>
    ): LiveData<formDataExten> {
        return instance?.getApplyDateInfo(id,callBack)!!
    }

    override fun postApplyDateInfo(
        request: ExtenDetialRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
       return  instance?.postApplyDateInfo(request,callBack)!!
    }

    override fun forceClose(
        workOrderType: String,
        request: ApplyCloseRequest,
        callBack: CallBack<Boolean>
    ) {
        instance?.forceClose(workOrderType,request,callBack)
    }

    override fun postpone(
        workOrderType: String,
        request: ExtenDetialRequest,
        callBack: CallBack<Boolean>
    ) {
        instance?.postpone(workOrderType,request,callBack)
    }

    override fun isClosed(request: IsClosedRequest, callBack: CallBack<Boolean>) {
        instance?.isClosed(request,callBack)
    }

    override fun getPatroPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage> {
        return instance?.getPlanPreviewList(request, callBack)!!
    }

    override fun getPlanPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage> {
        return instance?.getPlanPreviewList(request, callBack)!!
    }

    override fun planSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>) {
        return instance?.planSubmit(request, callBack)!!
    }

    override fun planDoneDetial(request: DoneDetialRequest, callBack: CallBack<PlanInfo>) {
        return instance?.planDoneDetial(request, callBack)!!
    }

    override fun planExten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>> {
        return instance?.planExten(request, callBack)!!
    }

    override fun planApplyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        return instance?.planApplyClose(request, callBack)!!
    }

    override fun planOrderDetail(id: String, callBack: CallBack<PlanInfo>) {
        return instance?.planOrderDetail(id, callBack)!!
    }

    override fun planWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<PlanWorkOrderPage>
    ): LiveData<PlanWorkOrderPage> {
        return instance?.planWaitPage(request, callBack)!!
    }

    override fun planClosedPage(
        request: DistributePageRequest,
        callBack: CallBack<PlanWorkOrderPage>
    ): LiveData<PlanWorkOrderPage> {
        return instance?.planClosedPage(request, callBack)!!
    }

    override fun patrolDoneDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        instance?.patrolDoneDetial(request, callBack)
    }

    override fun patrolSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>) {
        instance?.patrolSubmit(request, callBack)
    }

    override fun getHistroy(
        intstId: String,
        callBack: CallBack<List<HistoryModel>>
    ): LiveData<List<HistoryModel>> {
        return instance?.getHistroy(intstId, callBack)!!
    }

    override fun resendOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse> {
        return instance?.resendOrder(request, callBack)!!
    }
    override fun resendCusOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse> {
        return instance?.resendCusOrder(request, callBack)!!
    }

    override fun applyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        return instance?.applyClose(request, callBack)!!
    }
    override fun applyCustomerClose(
        request: ApplyCusCloseRequest,
        midUrl: String,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        return instance?.applyCustomerClose(request,midUrl, callBack)!!
    }

    override fun exten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>> {
        return instance?.exten(request, callBack)!!
    }

    override fun distributeReply(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>) {
        return instance?.distributeReply(request, callBack)!!
    }

    override fun distributeCheck(request: DistributeCheckRequest, callBack: CallBack<Boolean>) {
        return instance?.distributeCheck(request, callBack)!!
    }

    override fun getJob(request: GetJobRequest, callBack: CallBack<JobPage>): LiveData<JobPage> {
        return instance?.getJob(request, callBack)!!
    }

    override fun getOrgnization(
        id: String,
        callBack: CallBack<OrgnizationModel>
    ): LiveData<OrgnizationModel> {
        return instance?.getOrgnization(id, callBack)!!
    }

    override fun distributeDetial(orderId: String, callBack: CallBack<DisttributeDetialModel>) {
        instance?.distributeDetial(orderId, callBack)
    }

    override fun distributeSubmit(request: DistributeSubmitRequest, callBack: CallBack<Boolean>) {
        instance?.distributeSubmit(request, callBack)
    }

    override fun distributeResponse(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>) {
        instance?.distributeResponse(request, callBack)
    }

    override fun distributeWaitDetial(taskId: String, callBack: CallBack<DisttributeDetialModel>) {
        instance?.distributeWaitDetial(taskId, callBack)
    }

    override fun distributeDoneDetial(
        request: DoneDetialRequest,
        callBack: CallBack<DisttributeDetialModel>
    ) {
        instance?.distributeDoneDetial(request, callBack)
    }

    override fun getResourceInfos(
        massifId: String,
        resourceTypeCode: String,
        callBack: CallBack<List<ResourceTypeBean>>
    ): LiveData<List<ResourceTypeBean>> {
        return instance?.getResourceInfos(massifId, resourceTypeCode, callBack)!!
    }


    override fun createSendOrder(
        request: CreateSendOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.createSendOrder(request, callBack)!!
    }

    override fun patrolPendingDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        instance?.patrolPendingDetial(request, callBack)
    }

    override fun patrolClosedPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        instance?.patrolClosedPage(request, callBack)
    }

    override fun patrolWaitPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        instance?.patrolWaitPage(request, callBack)
    }

    /**
     * 派工单代办列表
     * */
    override fun distributeWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage> {
        return instance?.distributeWaitPage(request, callBack)!!
    }

    /**
     * 派工单已办列表
     * */
    override fun distributeDonePage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage> {
        return instance?.distributeDonePage(request, callBack)!!
    }

    override fun getWaitCount(callBack: CallBack<WaitCount>): LiveData<WaitCount> {
        return instance?.getWaitCount(callBack)!!
    }

    override fun getTiaoXian(callBack: CallBack<List<ResourceTypeBean>>): LiveData<List<ResourceTypeBean>> {
        return instance?.getTiaoXian(callBack)!!
    }

    override fun getWorkOrderType(callBack: CallBack<List<WorkOrderTypeModel>>): LiveData<List<WorkOrderTypeModel>> {
        return instance?.getWorkOrderType(callBack)!!
    }

    private var instance: ResourceWorkOrderService? = null

    constructor() {
        //数据代理，灵活更换实现
        instance = ResourceWorkOrderRepo()//真实实现，可替换
    }

    fun getName(): String {
        return this.javaClass.simpleName
    }

}
