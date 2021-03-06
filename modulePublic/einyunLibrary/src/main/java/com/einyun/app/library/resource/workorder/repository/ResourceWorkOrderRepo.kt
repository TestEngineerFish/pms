package com.einyun.app.library.resource.workorder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.base.paging.bean.Query
import com.einyun.app.base.paging.bean.QueryBuilder
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.ResourceWorkOrderServiceApi
import com.einyun.app.library.resource.workorder.net.URLs
import com.einyun.app.library.resource.workorder.net.request.*
import com.einyun.app.library.resource.workorder.net.response.*
import com.einyun.app.library.resource.workorder.net.response.ApplyCloseResponse
import com.einyun.app.library.resource.workorder.net.response.DistributeListResponse
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse
import com.einyun.app.library.workorder.net.request.ComplainPageRequest
import com.einyun.app.library.workorder.net.request.RepairsPageRequest

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.repository
 * @ClassName:      ResourceWorkOrderRepo
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 10:23
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 10:23
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ResourceWorkOrderRepo : ResourceWorkOrderService {
    override fun getNodeId(
        request: GetNodeIdRequest,
        callBack: CallBack<GetNodeIdModel>
    ): LiveData<GetNodeIdModel> {
        val liveData = MutableLiveData<GetNodeIdModel>()
        serviceApi?.getNodeId(request)
            ?.compose(RxSchedulers.inIoMain<GetNodeIdResponse>())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun orderListComplain(
        request: OrderListPageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        val liveData = MutableLiveData<OrderListPage>()
        var queryBuilder = queryComplainBuilder(
            request
        )
        serviceApi?.orderListComplain(queryBuilder.build())
            ?.compose(RxSchedulers.inIoMain<OrderListResponse>())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun orderListAsk(
        request: OrderListPageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        val liveData = MutableLiveData<OrderListPage>()
        serviceApi?.orderListAsk(request)
            ?.compose(RxSchedulers.inIoMain<OrderListResponse>())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun orderListRepair(
        request: OrderListPageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        var queryBuilder = queryRepairBuilder(
            request
        )
        val liveData = MutableLiveData<OrderListPage>()
        serviceApi?.orderListRepair(queryBuilder.build())
            ?.compose(RxSchedulers.inIoMain<OrderListResponse>())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun orderListPatro(
        request: OrderListPageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        val liveData = MutableLiveData<OrderListPage>()
        serviceApi?.orderListPatro(request)
            ?.compose(RxSchedulers.inIoMain<OrderListResponse>())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun orderListPlan(
        request: OrderListPageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        val liveData = MutableLiveData<OrderListPage>()
        serviceApi?.orderListPlan(request)
            ?.compose(RxSchedulers.inIoMain<OrderListResponse>())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
//                        liveData.postValue(response.value)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    /**
     * 工单列表-派工单
     * */
    override fun orderListDistribute(
        request: OrderListPageRequest,
        callBack: CallBack<OrderListPage>
    ): LiveData<OrderListPage> {
        val liveData = MutableLiveData<OrderListPage>()
        serviceApi?.orderListDistribute(request)
            ?.compose(RxSchedulers.inIoMain<OrderListResponse>())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    override fun getOrderPreviewSelect(callBack: CallBack<List<PreviewSelectModel>>): LiveData<List<PreviewSelectModel>> {
        val liveData = MutableLiveData<List<PreviewSelectModel>>()
        serviceApi?.getOrderPreviewSelect()?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data.rows)
                    liveData.postValue(response.data.rows)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

    override fun postApplyDateInfo(
        request: ExtenDetialRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        serviceApi?.postApplyDateInfo(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.isState)
                    liveData.postValue(response.isState)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }

            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

    override fun getApplyDateInfo(
        id: String,
        callBack: CallBack<formDataExten>
    ): LiveData<formDataExten> {
        var builder = QueryBuilder()
        builder.addQueryItem(
            "parent_id",
            id,
            Query.OPERATION_EQUAL,
            Query.RELATION_AND
        )
        val liveData = MutableLiveData<formDataExten>()
        serviceApi?.getApplyDateInfo(builder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }

            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

    override fun forceClose(
        workOrderType: String,
        request: ApplyCloseRequest,
        callBack: CallBack<Boolean>
    ) {
        serviceApi?.forceClose(workOrderType, request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.isState)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    override fun postpone(
        workOrderType: String,
        request: ExtenDetialRequest,
        callBack: CallBack<Boolean>
    ) {
        serviceApi?.postpone(workOrderType, request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.isState)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    override fun isClosed(request: IsClosedRequest, callBack: CallBack<Boolean>) {
        serviceApi?.isClosed(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
//                    callBack.call(response.data)
                    if (response.isState) {
                        callBack.call(response.data)
                        Log.e("call", "111111111111111111111111111")
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                        Log.e("onFaild", "111111111111111111111111111")
                    }

                }, { callBack.onFaild(it)
                    Log.e("onFaildit", "111111111111111111111111111")
                }
            )
    }

    //巡查已办详情
    override fun patrolDoneDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        serviceApi?.patrolDoneDetial(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    override fun planDoneDetial(request: DoneDetialRequest, callBack: CallBack<PlanInfo>) {
        serviceApi?.planDoneDetial(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    override fun planApplyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        val liveData = MutableLiveData<ApplyCloseResponse>()
        serviceApi?.closeOrderPlan(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response)
                    liveData.postValue(response)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

    override fun planExten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>> {
        val liveData = MutableLiveData<BaseResponse<Object>>()
        serviceApi?.extenPlan(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                liveData.postValue(response)
                callBack.call(response)
            }, { error -> {} })
        return liveData;
    }

    override fun planOrderDetail(taskId: String, callBack: CallBack<PlanInfo>) {
        var request = PatrolDetialRequest()
        request.taskId = taskId
        serviceApi?.planOrderDetail(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    //计划工单待跟进
    override fun planWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<PlanWorkOrderPage>
    ): LiveData<PlanWorkOrderPage> {
        val liveData = MutableLiveData<PlanWorkOrderPage>()
        serviceApi?.planWaitPage(request)
            ?.compose(RxSchedulers.inIoMain<PlanListResponse>())
            ?.subscribe({ response: PlanListResponse ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.value)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    //计划工单已跟进
    override fun planClosedPage(
        request: DistributePageRequest,
        callBack: CallBack<PlanWorkOrderPage>
    ): LiveData<PlanWorkOrderPage> {
        val liveData = MutableLiveData<PlanWorkOrderPage>()
        serviceApi?.planDonePage(request)
            ?.compose(RxSchedulers.inIoMain<PlanListResponse>())
            ?.subscribe({ response: PlanListResponse ->
                if (response.isState) {
                    callBack.call(response.data)
//                        liveData.postValue(response.value)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    //巡查处理
    override fun patrolSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>) {
        serviceApi?.patrolSubmit(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                {
                    if (it.isState) {
                        callBack.call(it.isState)
                    } else {
                        callBack.onFaild(EinyunHttpException(it))
                    }
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    /**
     * 获取工作预览计划工单列表
     * */
    override fun getPatroPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage> {
        val liveData = MutableLiveData<OrderPreviewPage>()
        serviceApi?.getPatroPreviewOrders(request)
            ?.compose(RxSchedulers.inIoMain<OrderPreviewResponse>())
            ?.subscribe({ response: OrderPreviewResponse ->
                if (response.isState) {
                    callBack.call(response.data)
//                        liveData.postValue(response.value)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    /**
     * 获取工作预览计划工单列表
     * */
    override fun getPlanPreviewList(
        request: OrderPreviewRequest,
        callBack: CallBack<OrderPreviewPage>
    ): LiveData<OrderPreviewPage> {
        val liveData = MutableLiveData<OrderPreviewPage>()
        serviceApi?.getPlanPreviewOrders(request)
            ?.compose(RxSchedulers.inIoMain<OrderPreviewResponse>())
            ?.subscribe({ response: OrderPreviewResponse ->
                if (response.isState) {
                    callBack.call(response.data)
//                        liveData.postValue(response.value)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    //巡查处理
    override fun planSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>) {
        serviceApi?.planSubmit(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                {
                    if (it.isState) {
                        callBack.call(it.isState)
                    } else {
                        callBack.onFaild(EinyunHttpException(it))
                    }
                }, {
                    callBack.onFaild(it)
                }
            )
    }
    //checkQrCodeModel
    override fun checkQrCodeModel(request: String, callBack: CallBack<ForseScanCodeModel>) {
        serviceApi?.checkQrCodeModel(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                {
                    if (it.isState) {
                        callBack.call(it.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(it))
                    }
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    //获取历史流程
    override fun getHistroy(
        intstId: String,
        callBack: CallBack<List<HistoryModel>>
    ): LiveData<List<HistoryModel>> {
        val liveData = MutableLiveData<List<HistoryModel>>()
        var url = URLs.URL_HIDTROY + intstId
        serviceApi?.getHistroy(url)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    callBack.call(response.data)
                }, {
                    callBack.onFaild(it)
                }
            )
        return liveData; }

    //转派工单
    override fun resendOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse> {
        val liveData = MutableLiveData<ResendOrderResponse>()
        serviceApi?.resendOrder(request)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    callBack.call(response)
                }, {
                    callBack.onFaild(it)
                }
            )
        return liveData; }

    //三大客服转派工单
    override fun resendCusOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderResponse>
    ): LiveData<ResendOrderResponse> {
        val liveData = MutableLiveData<ResendOrderResponse>()
        serviceApi?.resendCusOrder(request)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    callBack.call(response)
                }, {
                    callBack.onFaild(it)
                }
            )
        return liveData; }

    override fun exten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>> {
        val liveData = MutableLiveData<BaseResponse<Object>>()
        serviceApi?.exten(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    liveData.postValue(response)
                    callBack.call(response)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }

            }, { error -> {} })
        return liveData;
    }

    override fun distributeReply(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>) {
        serviceApi?.distribteReply(request.taskId!!)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                {
                    callBack.call(it.isState)
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    override fun distributeCheck(request: DistributeCheckRequest, callBack: CallBack<Boolean>) {
        serviceApi?.distributeCheck(request)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                {
                    callBack.call(it.isState)
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    override fun distributeDetial(orderId: String, callBack: CallBack<DisttributeDetialModel>) {
        var url = URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_DETIAL_INFO + orderId
        serviceApi?.distributeWaitDetialInfo(url)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    override fun distributeSubmit(request: DistributeSubmitRequest, callBack: CallBack<Boolean>) {
        serviceApi?.distributeSumbmit(request)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                {
                    callBack.call(it.isState)
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    override fun distributeResponse(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>) {
        serviceApi?.distribteResponse(request)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                {
                    callBack.call(it.isState)
                }, {
                    callBack.onFaild(it)
                }
            )
    }


    override fun distributeWaitDetial(taskId: String, callBack: CallBack<DisttributeDetialModel>) {
        var url = URLs.URL_RESOURCE_WORKORDER_DISTRIBUTE_DETIAL + taskId
        serviceApi?.distributeWaitDetial(url)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    override fun distributeDoneDetial(
        request: DoneDetialRequest,
        callBack: CallBack<DisttributeDetialModel>
    ) {
        serviceApi?.distributeDoneDetial(request)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                {
                    if (it.isState) {
                        callBack.call(it.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(it))
                    }
                }, {
                    callBack.onFaild(it)
                }
            )
    }

    override fun getResourceInfos(
        massifId: String,
        resourceTypeCode: String,
        callBack: CallBack<List<ResourceTypeBean>>
    ): LiveData<List<ResourceTypeBean>> {
        val liveData = MutableLiveData<List<ResourceTypeBean>>()
        val request = ResourceTypeRequest()
        request.massifId = massifId
        request.resourceTypeCode = resourceTypeCode
        serviceApi?.getResourceInfos(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData;
    }

    override fun createSendOrder(
        request: CreateSendOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        serviceApi?.createSendOrder(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.isState)
                    liveData.postValue(response.isState)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData;
    }

    /**
     * 获取巡查待办列表
     */
    override fun patrolWaitPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        serviceApi?.patrolWaitPage(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
    }

    /**
     * 获取巡查待办已办
     */
    override fun patrolClosedPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        serviceApi?.patrolDonePage(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
    }

    /**
     * 巡查工单详情
     */
    override fun patrolPendingDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        serviceApi?.patrolPendingDetial(request)?.compose(RxSchedulers.inIoMain())?.doOnError({ error ->
            Log.e("error", error.toString())
        })?.subscribe(
            { response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { callBack.onFaild(it) }
        )
    }


    /*fun pageQuery(request:  DistributePageRequest, callback: CallBack<DistributeWorkOrderPage>) {
        serviceApi?.distributeWaitPage(request)?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    //                    if(response.isState()){
                    callback.call(response.getData())
                    //                    }
                }, { error ->
                    callback.onFaild(error)
                    error.printStackTrace()
                })
    }*/
    override fun getWaitCount(callBack: CallBack<WaitCount>): LiveData<WaitCount> {
        val liveData = MutableLiveData<WaitCount>()
        serviceApi?.getWaitCount()?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }


    var serviceApi: ResourceWorkOrderServiceApi? = null

    init {
        serviceApi =
            EinyunHttpService.getInstance().getServiceApi(ResourceWorkOrderServiceApi::class.java)
    }

    /**
     * 派工单代办列表
     * */
    override fun distributeWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage> {
        val liveData = MutableLiveData<DistributeWorkOrderPage>()
        serviceApi?.distributeWaitPage(request)
            ?.compose(RxSchedulers.inIoMain<DistributeListResponse>())
            ?.subscribe({ response: DistributeListResponse ->
                if (response.isState) {
                    callBack.call(response.data)
//                        liveData.postValue(response.value)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    /**
     * 派工单已跟进列表
     * */
    override fun distributeDonePage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage> {
        val liveData = MutableLiveData<DistributeWorkOrderPage>()
        serviceApi?.distributeDonePage(request)
            ?.compose(RxSchedulers.inIoMain<DistributeListResponse>())
            ?.subscribe({ response: DistributeListResponse ->
                if (response.isState) {
                    callBack.call(response.data)
//                        liveData.postValue(response.value)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }


    /**
     * 获取条线
     * */
    override fun getTiaoXian(callBack: CallBack<List<ResourceTypeBean>>): LiveData<List<ResourceTypeBean>> {
        val liveData = MutableLiveData<List<ResourceTypeBean>>()
        serviceApi?.getTiaoXian()?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    /**
     * 获取工单类型
     * */
    override fun getWorkOrderType(callBack: CallBack<List<WorkOrderTypeModel>>): LiveData<List<WorkOrderTypeModel>> {
        val liveData = MutableLiveData<List<WorkOrderTypeModel>>()
        var typeKey = "pgdlx"
        serviceApi?.getOrderType(typeKey)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData

    }

    /**
     * 获取组织结构
     * */
    override fun getOrgnization(
        request: GetOrgRequest,
        callBack: CallBack<List<OrgnizationModel>>
    ): LiveData<List<OrgnizationModel>> {
        val liveData = MutableLiveData<List<OrgnizationModel>>()
        serviceApi?.getOrgnization(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    /**
     * 获取审批角色
     * */

    override fun getJob(request: GetJobRequest, callBack: CallBack<List<JobModel>>): LiveData<List<JobModel>> {
        val liveData = MutableLiveData<List<JobModel>>()
        serviceApi?.getJob()?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {

                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

    override fun applyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        val liveData = MutableLiveData<ApplyCloseResponse>()
        serviceApi?.closeOrder(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response)
                    liveData.postValue(response)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

    override fun applyCustomerClose(
        request: ApplyCusCloseRequest,
        midUrl: String,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        val liveData = MutableLiveData<ApplyCloseResponse>()
        serviceApi?.closeCustomerOrder(midUrl, request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response)
                    liveData.postValue(response)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error ->
                callBack.onFaild(error)

            })
        return liveData
    }

    private fun queryRepairBuilder(
        request: OrderListPageRequest
    ): QueryBuilder {
        var builder = QueryBuilder()
        builder.addQueryItem(
            "bx_dk_id",
            request.bx_dk_id,
            Query.OPERATION_EQUAL,
            Query.RELATION_AND
        )
            .addQueryItem(
                "bx_area_id",
                request.bx_area_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem(
                "bx_cate_lv1_id",
                request.bx_cate_lv1_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem(
                "bx_cate_lv2_id",
                request.bx_cate_lv2_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem("state", request.state, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("node_id_", request.node_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("owner_id_", request.owner_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addSort("bx_time", request.DESC)
            .setPageBean(request.pageBean)
            .setParamsValue(request.searchValue)
        return builder
    }

    private fun queryComplainBuilder(
        request: OrderListPageRequest
    ): QueryBuilder {
        var builder = QueryBuilder()
        builder.addQueryItem(
            "F_ts_dk_id",
            request.ts_dk_id,
            Query.OPERATION_EQUAL,
            Query.RELATION_AND
        )
            .addQueryItem(
                "F_ts_property_id",
                request.F_ts_property_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem(
                "F_ts_cate_id",
                request.F_ts_cate_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem("state", request.state, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("node_id_", request.node_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("owner_id_", request.owner_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addSort("F_ts_time", request.DESC)
            .setPageBean(request.pageBean)
            .setParamsValue(request.searchValue)
        return builder
    }

}
