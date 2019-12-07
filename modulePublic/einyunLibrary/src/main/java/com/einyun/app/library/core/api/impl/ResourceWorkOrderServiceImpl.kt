package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.core.api.proxy.ResourceWorkOrderServiceImplProxy
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.request.*

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
    override fun distributeWaitDetial(taskId: String, callBack: CallBack<DisttributeDetialModel>) {
       return proxy.distributeWaitDetial(taskId,callBack)
    }

    override fun distributeDoneDetial(
        request: DoneDetialRequest,
        callBack: CallBack<DisttributeDetialModel>
    ) {
        return proxy.distributeDoneDetial(request,callBack)
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

    override fun patrolDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        proxy.patrolDetial(request, callBack)
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
        return proxy.distributeWaitPage(request, callBack)
    }

    override fun getWaitCount(callBack: CallBack<WaitCount>): LiveData<WaitCount> {
        return proxy.getWaitCount(callBack)
    }

    var proxy: ResourceWorkOrderServiceImplProxy = ResourceWorkOrderServiceImplProxy()

    fun getName(): String {
        return this.javaClass.simpleName
    }
}
