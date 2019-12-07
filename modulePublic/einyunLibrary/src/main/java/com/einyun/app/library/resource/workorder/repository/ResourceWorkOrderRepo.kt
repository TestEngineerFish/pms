package com.einyun.app.library.resource.workorder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.base.paging.bean.PageBean
import com.einyun.app.base.paging.bean.Query
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.ResourceWorkOrderServiceApi
import com.einyun.app.library.resource.workorder.net.request.*
import com.einyun.app.library.resource.workorder.net.response.DistributeListResponse
import com.einyun.app.library.uc.user.net.request.LoginRequest
import com.einyun.app.library.resource.workorder.net.response.PatrolDetialResponse

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
    override fun patrolDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        serviceApi?.patrolDetial(request)?.compose(RxSchedulers.inIo())
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

}
