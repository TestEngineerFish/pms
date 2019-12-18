package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.workorder.model.RepairsPage
import com.einyun.app.library.workorder.model.*
import com.einyun.app.library.workorder.net.request.RepairsPageRequest
import com.einyun.app.library.workorder.model.BlocklogNums
import com.einyun.app.library.workorder.model.DoorResult
import com.einyun.app.library.workorder.model.TypeAndLine
import com.einyun.app.library.workorder.net.request.CreateClientComplainOrderRequest
import com.einyun.app.library.workorder.net.request.CreateClientEnquiryOrderRequest
import com.einyun.app.library.workorder.net.request.CreateClientRepairOrderRequest
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse

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
interface WorkOrderService : EinyunService {
    fun getAuditCount(callBack: CallBack<Int>): LiveData<Int>
    fun getBlocklogNums(callBack: CallBack<BlocklogNums>): LiveData<BlocklogNums>
    fun getMappingByUserIds(
        request: List<String>,
        callBack: CallBack<Map<String, GetMappingByUserIdsResponse>>
    ): LiveData<Map<String, GetMappingByUserIdsResponse>>

    //获取报修代跟进列表
    fun getRepairWaitFollow(
        request: RepairsPageRequest, callBack: CallBack<RepairsPage>
    )

    //获取报修抢单进列表
    fun getRepairGrab(
        request: RepairsPageRequest, callBack: CallBack<RepairsPage>
    )

    //获取报修已跟进列表
    fun getRepaiAlreadyFollow(
        request: RepairsPageRequest, callBack: CallBack<AlreadyFollowPageResult>
    )
    //获取报修已办结列表
    fun getRepaiAlreadyDone(
        request: RepairsPageRequest, callBack: CallBack<AlreadyDonePageResult>
    )
    //获取报修抄送我
    fun getRepairCopyMe(
        request: RepairsPageRequest, callBack: CallBack<RepairCopyMePageResullt>
    )

    fun typeAndLineList(callBack: CallBack<List<TypeAndLine>>):LiveData<List<TypeAndLine>>
    fun startEnquiry(request: CreateClientEnquiryOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun startComplain(request: CreateClientComplainOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun startRepair(request: CreateClientRepairOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun repairTypeList(callBack: CallBack<DoorResult>):LiveData<DoorResult>
}