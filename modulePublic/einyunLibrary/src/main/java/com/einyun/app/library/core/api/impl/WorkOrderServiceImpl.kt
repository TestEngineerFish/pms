package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.DashBoardService
import com.einyun.app.library.core.api.UCService
import com.einyun.app.library.core.api.WorkOrderService
import com.einyun.app.library.core.api.proxy.DashBoardServiceImplProxy
import com.einyun.app.library.core.api.proxy.UCSericeImplProxy
import com.einyun.app.library.core.api.proxy.WorkOrderServiceImplProxy
import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.UserMenuData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest
import com.einyun.app.library.workorder.model.BlocklogNums
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse

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
class WorkOrderServiceImpl : WorkOrderService {
    override fun getMappingByUserIds(
        request: List<String>,
        callBack: CallBack<Map<String, GetMappingByUserIdsResponse>>
    ): LiveData<Map<String, GetMappingByUserIdsResponse>> {
        return proxy.getMappingByUserIds(request, callBack)
    }

    override fun getBlocklogNums(callBack: CallBack<BlocklogNums>): LiveData<BlocklogNums> {
        return proxy.getBlocklogNums(callBack)
    }

    override fun getAuditCount(callBack: CallBack<Int>): LiveData<Int> {
        return proxy.getAuditCount(callBack)
    }

    var proxy: WorkOrderServiceImplProxy = WorkOrderServiceImplProxy()

    fun getName(): String {
        return this.javaClass.simpleName
    }
}