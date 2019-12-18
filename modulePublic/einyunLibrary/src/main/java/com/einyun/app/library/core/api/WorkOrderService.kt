package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
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
    fun typeAndLineList(callBack: CallBack<List<TypeAndLine>>):LiveData<List<TypeAndLine>>
    fun startEnquiry(request: CreateClientEnquiryOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun startComplain(request: CreateClientComplainOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun startRepair(request: CreateClientRepairOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun repairTypeList(callBack: CallBack<DoorResult>):LiveData<DoorResult>
}