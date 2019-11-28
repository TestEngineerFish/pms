package com.einyun.app.library.resource.workorder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.resource.workorder.model.WaitCount
import com.einyun.app.library.resource.workorder.net.ResourceWorkOrderServiceApi

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


}