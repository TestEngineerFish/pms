package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.PageBean
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean
import com.einyun.app.library.workorder.model.RepairsPage
import com.einyun.app.library.workorder.model.*
import com.einyun.app.library.workorder.model.BlocklogNums
import com.einyun.app.library.workorder.model.DoorResult
import com.einyun.app.library.workorder.model.TypeAndLine
import com.einyun.app.library.workorder.net.request.*
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse
import io.reactivex.Flowable
import retrofit2.http.Body

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
        request: RepairsPageRequest, callBack: CallBack<RepairsPage>
    )

    //获取报修已办结列表
    fun getRepaiAlreadyDone(
        request: RepairsPageRequest, callBack: CallBack<RepairsPage>
    )

    //获取报修待反馈
    fun getRepairWaitFeed(
        request: RepairsPageRequest, callBack: CallBack<RepairsPage>
    )

    //获取报修抄送我
    fun getRepairCopyMe(
        request: RepairsPageRequest, callBack: CallBack<RepairsPage>
    )

    //抢单
    fun grabRepair(
        taskId: String, callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    //获取报修详情接口
    fun getRepairDetail(
        instId: String, callBack: CallBack<RepairsDetailModel>
    ): LiveData<RepairsDetailModel>

    //获取报修详情接口
    fun saveHandler(
        request: SaveHandleRequest, callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    //报修-派单
    fun repaireSend(request: RepairSendOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean>

    fun typeAndLineList(callBack: CallBack<List<TypeAndLine>>): LiveData<List<TypeAndLine>>
    fun startEnquiry(
        request: CreateClientEnquiryOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    fun startComplain(
        request: CreateClientComplainOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    fun startRepair(
        request: CreateClientRepairOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    fun repairTypeList(callBack: CallBack<Door>): LiveData<Door>
    fun postCommunication(
        request: PostCommunicationRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>

    fun complainWorkListdPage(
        pageBean: PageBean,
        mobile: String,
        callBack: CallBack<ComplainModelPageResult>
    ): LiveData<ComplainModelPageResult>

    //获取报修详情接口
    fun getClientOrderDetail(
        instId: String,taskId:String, callBack: CallBack<RepairsDetailModel>
    ): LiveData<RepairsDetailModel>
    //客户报修-筛选
    fun getAreaType(callBack: CallBack<AreaModel>):LiveData<AreaModel>
    fun complainDetailComplete(request:ComplainDetailCompleteRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun complainDetailSave(request:ComplainDetailCompleteRequest, callBack: CallBack<Boolean>): LiveData<Boolean>
    fun getUserInfoByHouseId(houseId:String, callBack: CallBack<List<UserInfoByHouseIdModel>>):LiveData<List<UserInfoByHouseIdModel>>
    fun appendComplain(
        request: ComplainAppendRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean>
}