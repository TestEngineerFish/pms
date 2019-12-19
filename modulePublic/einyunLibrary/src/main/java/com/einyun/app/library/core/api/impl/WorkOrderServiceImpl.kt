package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.WorkOrderService
import com.einyun.app.library.core.api.proxy.WorkOrderServiceImplProxy
import com.einyun.app.library.workorder.model.RepairsPage
import com.einyun.app.library.workorder.model.*
import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.UserMenuData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest
import com.einyun.app.library.workorder.model.BlocklogNums
import com.einyun.app.library.workorder.model.DoorResult
import com.einyun.app.library.workorder.model.TypeAndLine
import com.einyun.app.library.workorder.net.request.*
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
    override fun postCommunication(
        request: PostCommunicationRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return proxy.postCommunication(
            request, callBack
        )
    }

    /**
     *客户报修-抄送我
     * */
    override fun getRepairCopyMe(
        request: RepairsPageRequest,
        callBack: CallBack<RepairCopyMePageResullt>
    ) {
        return proxy.getRepairCopyMe(
            request, callBack
        )         }

    /**
     *客户报修-已办结
     * */
    override fun getRepaiAlreadyDone(
        request: RepairsPageRequest,
        callBack: CallBack<AlreadyDonePageResult>
    ) {
        return proxy.getRepaiAlreadyDone(
            request, callBack
        )       }

    /**
     *客户报修-已跟进
    * */
    override fun getRepaiAlreadyFollow(
        request: RepairsPageRequest,
        callBack: CallBack<AlreadyFollowPageResult>
    ) {
        return proxy.getRepaiAlreadyFollow(
            request, callBack
        )    }

    /**
     * 客户报修-抢单
     * */
    override fun getRepairGrab(request: RepairsPageRequest, callBack: CallBack<RepairsPage>) {
        return proxy.getRepairGrab(
            request, callBack
        )    }

    /**
     * 客户报修-待跟进
     * */
    override fun getRepairWaitFollow(
        request:RepairsPageRequest, callBack: CallBack<RepairsPage>
    ) {
        return proxy.getRepairWaitFollow(
            request, callBack
        )
    }

    override fun startRepair(
        request: CreateClientRepairOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return proxy.startRepair(request, callBack)
    }

    override fun repairTypeList(callBack: CallBack<Door>): LiveData<Door> {
        return proxy.repairTypeList(callBack)
    }

    override fun startComplain(
        request: CreateClientComplainOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return proxy.startComplain(request, callBack)
    }

    override fun startEnquiry(
        request: CreateClientEnquiryOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return proxy.startEnquiry(request, callBack)
    }

    override fun typeAndLineList(callBack: CallBack<List<TypeAndLine>>): LiveData<List<TypeAndLine>> {
        return proxy.typeAndLineList(callBack)
    }

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