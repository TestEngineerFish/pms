package com.einyun.app.library.resource.workorder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.ResourceWorkOrderServiceApi
import com.einyun.app.library.resource.workorder.net.URLs
import com.einyun.app.library.resource.workorder.net.request.*
import com.einyun.app.library.resource.workorder.net.response.ApplyCloseResponse
import com.einyun.app.library.resource.workorder.net.response.DistributeListResponse
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse

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
    //巡查已办详情
    override fun patrolDoneDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        serviceApi?.patrolDoneDetial(request)?.compose(RxSchedulers.inIo())
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

    //巡查处理
    override fun patrolSubmit(request: PatrolSubmitRequest, callBack: CallBack<Boolean>) {
        serviceApi?.patrolSubmit(request)?.compose(RxSchedulers.inIo())
            ?.subscribe(
                {
                    callBack.call(it.isState)
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
        return liveData;       }
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
        return liveData;    }

    override fun exten(
        request: ExtenDetialRequest,
        callBack: CallBack<BaseResponse<Object>>
    ): LiveData<BaseResponse<Object>> {
        val liveData = MutableLiveData<BaseResponse<Object>>()
        serviceApi?.exten(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                    liveData.postValue(response)
                    callBack.call(response)
            }, { error -> {} })
        return liveData;
    }

    override fun distributeReply(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>) {
        serviceApi?.distribteReply(request)?.compose(RxSchedulers.inIo())
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
        serviceApi?.patrolPendingDetial(request)?.compose(RxSchedulers.inIo())
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
        serviceApi?.getOrderType()?.compose(RxSchedulers.inIo())
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
        id: String,
        callBack: CallBack<OrgnizationModel>
    ): LiveData<OrgnizationModel> {
        val liveData = MutableLiveData<OrgnizationModel>()
        var url = URLs.URL_SELECT_BY_ORGNIZATION + id
        serviceApi?.getOrgnization(url)?.compose(RxSchedulers.inIo())
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

    override fun getJob(request: GetJobRequest, callBack: CallBack<JobPage>): LiveData<JobPage> {
        val liveData = MutableLiveData<JobPage>()
        serviceApi?.getJob(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    Log.d("test","cuowu1")

                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error ->
                callBack.onFaild(error)
                Log.d("test", error.message)
            })
        return liveData
    }

    override fun applyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse> {
        val liveData = MutableLiveData<ApplyCloseResponse>()
        serviceApi?.closeOrder(request)?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                    callBack.call(response)
                    liveData.postValue(response)
            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

}
