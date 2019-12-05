package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.Query
import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.UserMenuData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.dashboard.net.response.WorkOrderResponse
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.request.CreateSendOrderRequest
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest
import com.einyun.app.library.uc.usercenter.model.OrgModel
import com.einyun.app.library.workorder.model.BlocklogNums

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      UCService
 * @Description:    UC服务接口，高级抽象，约束
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 14:46
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 14:46
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface ResourceWorkOrderService : EinyunService {
    fun getWaitCount(callBack: CallBack<WaitCount>): LiveData<WaitCount>
    //派工单代办列表
    fun distributeWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage>

    //巡查工单代办
    fun patrolWaitPage(request: PatrolPageRequest, callBack: CallBack<PatrolWorkOrderPage>)

    //巡查工单已办
    fun patrolClosedPage(request: PatrolPageRequest, callBack: CallBack<PatrolWorkOrderPage>)

    //巡查工单详情
    fun patrolDetial(request: PatrolDetialRequest, callBack: CallBack<PatrolInfo>)

    //创建派工单
    fun createSendOrder(
        request: CreateSendOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>
}
