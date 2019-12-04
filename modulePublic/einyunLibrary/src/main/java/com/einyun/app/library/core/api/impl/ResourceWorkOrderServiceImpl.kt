package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.DashBoardService
import com.einyun.app.library.core.api.ResourceWorkOrderService
import com.einyun.app.library.core.api.UCService
import com.einyun.app.library.core.api.WorkOrderService
import com.einyun.app.library.core.api.proxy.DashBoardServiceImplProxy
import com.einyun.app.library.core.api.proxy.ResourceWorkOrderServiceImplProxy
import com.einyun.app.library.core.api.proxy.UCSericeImplProxy
import com.einyun.app.library.core.api.proxy.WorkOrderServiceImplProxy
import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.UserMenuData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest

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
    override fun patrolDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>) {
        proxy.patrolDetial(request,callBack)
    }

    override fun patrolClosedPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        proxy.patrolClosedPage(request,callBack)
    }

    override fun patrolWaitPage(
        request: PatrolPageRequest,
        callBack: CallBack<PatrolWorkOrderPage>
    ) {
        proxy.patrolWaitPage(request,callBack)
    }

    override fun distributeWaitPage(request: DistributePageRequest, callBack: CallBack<DistributeWorkOrderPage>): LiveData<DistributeWorkOrderPage> {
        return proxy.distributeWaitPage(request,callBack)
    }

    override fun getWaitCount(callBack: CallBack<WaitCount>): LiveData<WaitCount> {
        return proxy.getWaitCount(callBack)
    }

    var proxy: ResourceWorkOrderServiceImplProxy = ResourceWorkOrderServiceImplProxy()

    fun getName(): String {
        return this.javaClass.simpleName
    }
}
