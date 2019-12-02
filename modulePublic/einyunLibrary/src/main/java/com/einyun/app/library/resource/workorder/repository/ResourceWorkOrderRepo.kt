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
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrderPage
import com.einyun.app.library.resource.workorder.model.WaitCount
import com.einyun.app.library.resource.workorder.net.ResourceWorkOrderServiceApi
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest
import com.einyun.app.library.resource.workorder.net.response.DistributeListResponse
import com.einyun.app.library.uc.user.net.request.LoginRequest
import com.einyun.app.library.resource.workorder.net.request.PageQueryRequest
import com.einyun.app.library.resource.workorder.net.request.PageRquest
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest

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
    /**
     * 获取巡查待办列表
     */
    override fun patrolWaitPage(request: PatrolPageRequest, callBack: CallBack<PatrolWorkOrderPage>) {
        serviceApi?.patrolWaitPage(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({response->
                if(response.isState){
                    callBack.call(response.data)
                }else{
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
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
        serviceApi?.getWaitCount()?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        callBack.call(response.value)
                        liveData.postValue(response.value)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { error -> callBack.onFaild(error) })
        return liveData
    }


    var serviceApi: ResourceWorkOrderServiceApi?= null
    init {
        serviceApi=EinyunHttpService.getInstance().getServiceApi(ResourceWorkOrderServiceApi::class.java)
    }
    /**
     * 派工单代办列表
     * */
    override fun distributeWaitPage(request: DistributePageRequest, callBack: CallBack<DistributeWorkOrderPage>): LiveData<DistributeWorkOrderPage> {
        val liveData = MutableLiveData<DistributeWorkOrderPage>()
        serviceApi?.distributeWaitPage(request)?.compose(RxSchedulers.inIoMain<DistributeListResponse>())
                ?.subscribe({ response:DistributeListResponse ->
                    if (response.isState) {
                        callBack.call(response.data)
//                        liveData.postValue(response.value)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { error -> callBack.onFaild(error) })
        return liveData    }

}
