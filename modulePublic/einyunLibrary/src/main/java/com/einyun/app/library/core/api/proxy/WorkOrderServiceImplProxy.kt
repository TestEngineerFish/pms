package com.einyun.app.library.core.api.proxy

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.paging.bean.PageBean
import com.einyun.app.library.core.api.WorkOrderService
import com.einyun.app.library.workorder.model.RepairsPage
import com.einyun.app.library.workorder.model.*
import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.UserMenuData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.dashboard.repository.DashBoardRepo
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest
import com.einyun.app.library.uc.user.repository.UserRepository
import com.einyun.app.library.workorder.model.BlocklogNums
import com.einyun.app.library.workorder.model.DoorResult
import com.einyun.app.library.workorder.model.TypeAndLine
import com.einyun.app.library.workorder.net.request.*
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse
import com.einyun.app.library.workorder.repository.WorkOrderRepository

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.core.api.proxy
 * @ClassName: UCSericeImplProxy
 * @Description: UC服务代理类
 *                单一原则，开闭原则，李氏置换原则
 * @Author: chumingjun
 * @CreateDate: 2019/09/26 16:04
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/26 16:04
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class WorkOrderServiceImplProxy : WorkOrderService {
    override fun appendComplain(
        request: ComplainAppendRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.appendComplain(
            request,
            callBack
        )!!
    }

    override fun getUserInfoByHouseId(
        houseId: String,
        callBack: CallBack<List<UserInfoByHouseIdModel>>
    ): LiveData<List<UserInfoByHouseIdModel>> {
        return instance?.getUserInfoByHouseId(
            houseId,
            callBack
        )!!
    }

    /**
     * 获取报修筛选数据
     * */
    override fun getAreaType(callBack: CallBack<AreaModel>): LiveData<AreaModel> {
        return instance?.getAreaType(
            callBack
        )!!    }

    override fun getRepairWaitFeed(request: RepairsPageRequest, callBack: CallBack<RepairsPage>) {
        return instance?.getRepairWaitFollow(
            request,
            callBack
        )!!
       }

    override fun complainDetailSave(
        request: ComplainDetailCompleteRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.complainDetailSave(
            request, callBack
        )!!
    }

    override fun complainDetailComplete(
        request: ComplainDetailCompleteRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.complainDetailComplete(
            request, callBack
        )!!
    }

    override fun getClientOrderDetail(
        instId: String,
        taskId: String,
        callBack: CallBack<RepairsDetailModel>
    ): LiveData<RepairsDetailModel> {
        return instance?.getClientOrderDetail(
            instId, taskId, callBack
        )!!
    }

    override fun complainWorkListdPage(
        pageBean: PageBean,
        mobile: String,
        callBack: CallBack<ComplainModelPageResult>
    ): LiveData<ComplainModelPageResult> {
        return instance?.complainWorkListdPage(
            pageBean,mobile,
            callBack
        )!!
    }

    override fun saveHandler(
        request: SaveHandleRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.saveHandler(
            request,
            callBack
        )!!       }

    override fun repaireSend(
        request: RepairSendOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.repaireSend(
            request,
            callBack
        )!!       }

    override fun getRepairDetail(instId: String, callBack: CallBack<RepairsDetailModel>): LiveData<RepairsDetailModel> {
        return instance?.getRepairDetail(
            instId,
            callBack
        )!!    }

    override fun grabRepair(taskId: String, callBack: CallBack<Boolean>): LiveData<Boolean> {
        return instance?.grabRepair(
            taskId,
            callBack
        )!!
    }

    override fun postCommunication(
        request: PostCommunicationRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.postCommunication(
            request,
            callBack
        )!!
    }

    override fun getRepairCopyMe(
        request: RepairsPageRequest,
        callBack: CallBack<RepairsPage>
    ) {
        return instance?.getRepairCopyMe(
            request,
            callBack
        )!!
    }

    override fun getRepaiAlreadyDone(
        request: RepairsPageRequest,
        callBack: CallBack<RepairsPage>
    ) {
        return instance?.getRepaiAlreadyDone(
            request,
            callBack
        )!!
    }

    override fun getRepaiAlreadyFollow(
        request: RepairsPageRequest,
        callBack: CallBack<RepairsPage>
    ) {
        return instance?.getRepaiAlreadyFollow(
            request,
            callBack
        )!!
    }

    override fun getRepairGrab(request: RepairsPageRequest, callBack: CallBack<RepairsPage>) {
        return instance?.getRepairGrab(
            request,
            callBack
        )!!
    }

    override fun getRepairWaitFollow(request: RepairsPageRequest, callBack: CallBack<RepairsPage>) {
        return instance?.getRepairWaitFollow(
            request,
            callBack
        )!!
    }

    override fun startRepair(
        request: CreateClientRepairOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.startRepair(request, callBack)!!
    }

    override fun repairTypeList(callBack: CallBack<Door>): LiveData<Door> {
        return instance?.repairTypeList(callBack)!!
    }

    override fun startComplain(
        request: CreateClientComplainOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.startComplain(request, callBack)!!
    }

    override fun typeAndLineList(callBack: CallBack<List<TypeAndLine>>): LiveData<List<TypeAndLine>> {
        return instance?.typeAndLineList(callBack)!!
    }

    override fun startEnquiry(
        request: CreateClientEnquiryOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        return instance?.startEnquiry(request, callBack)!!
    }

    override fun getMappingByUserIds(
        request: List<String>,
        callBack: CallBack<Map<String, GetMappingByUserIdsResponse>>
    ): LiveData<Map<String, GetMappingByUserIdsResponse>> {
        return instance?.getMappingByUserIds(request, callBack)!!
    }

    override fun getBlocklogNums(callBack: CallBack<BlocklogNums>): LiveData<BlocklogNums> {
        return instance?.getBlocklogNums(callBack)!!
    }

    override fun getAuditCount(callBack: CallBack<Int>): LiveData<Int> {
        return instance?.getAuditCount(callBack)!!
    }


    private var instance: WorkOrderService? = null

    constructor() {
        //数据代理，灵活更换实现
        instance = WorkOrderRepository()//真实实现，可替换
    }

    fun getName(): String {
        return this.javaClass.simpleName
    }

}
