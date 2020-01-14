package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.DashBoardService
import com.einyun.app.library.core.api.UCService
import com.einyun.app.library.core.api.proxy.DashBoardServiceImplProxy
import com.einyun.app.library.core.api.proxy.UCSericeImplProxy
import com.einyun.app.library.dashboard.model.*
import com.einyun.app.library.dashboard.net.request.AllChargedRequest
import com.einyun.app.library.dashboard.net.request.OperateInRequest
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
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
class DashBoardServiceImpl : DashBoardService {
    override fun operatePercentIn(
        request: OperateInRequest,
        callBack: CallBack<OperateInModel>
    ): LiveData<OperateInModel> {
        return proxy.operatePercentIn(request,callBack)    }

    override fun allChargedProject(
        request: AllChargedRequest,
        callBack: CallBack<AllChargedModel>
    ): LiveData<AllChargedModel> {
        return proxy.allChargedProject(request,callBack)
    }

    override fun userMenuData(menuType: Int, callBack: CallBack<String>): LiveData<String> {
        return proxy.userMenuData(menuType, callBack)
    }

    override fun operateCaptureData(orgCodes: List<String>, callBack: CallBack<OperateCaptureData>): LiveData<OperateCaptureData> {
        return proxy.operateCaptureData(orgCodes, callBack)
    }

    override fun workOrderData(request: WorkOrderRequest, callBack: CallBack<WorkOrderData>): LiveData<WorkOrderData> {
        return proxy.workOrderData(request, callBack)
    }

    var proxy: DashBoardServiceImplProxy = DashBoardServiceImplProxy()

    fun getName(): String {
        return this.javaClass.simpleName
    }
}