package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.resource.workorder.model.*
import com.einyun.app.library.resource.workorder.net.request.*
import com.einyun.app.library.resource.workorder.net.response.ApplyCloseResponse
import java.util.*

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

    fun distributeWaitPage(
        request: DistributePageRequest,
        callBack: CallBack<DistributeWorkOrderPage>
    ): LiveData<DistributeWorkOrderPage>

    //派工单代办列表
    fun distributeDonePage(
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

    //获取派工单代办详情
    fun distributeWaitDetial(taskId: String, callBack: CallBack<DisttributeDetialModel>)

    //获取派工单已办详情
    fun distributeDetial(orderId: String, callBack: CallBack<DisttributeDetialModel>)

    //获取派工单已办详情
    fun distributeDoneDetial(request: DoneDetialRequest, callBack: CallBack<DisttributeDetialModel>)

    //派工单接单
    fun distributeResponse(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>)

    //派工单批复
    fun distributeReply(request: WorkOrderHanlerRequest, callBack: CallBack<Boolean>)

    //派工单验收
    fun distributeCheck(request: DistributeCheckRequest, callBack: CallBack<Boolean>)

    //派工单处理
    fun distributeSubmit(request: DistributeSubmitRequest, callBack: CallBack<Boolean>)

    //获取条线
    fun getTiaoXian(callBack: CallBack<List<ResourceTypeBean>>): LiveData<List<ResourceTypeBean>>

    //获取工单类型
    fun getWorkOrderType(callBack: CallBack<List<WorkOrderTypeModel>>): LiveData<List<WorkOrderTypeModel>>

    fun getResourceInfos(
        massifId: String,
        resourceTypeCode: String,
        callBack: CallBack<List<ResourceTypeBean>>
    ): LiveData<List<ResourceTypeBean>>

    //获取组织结构
    fun getOrgnization(id: String, callBack: CallBack<OrgnizationModel>): LiveData<OrgnizationModel>
    //获取审批角色

    fun getJob(request: GetJobRequest, callBack: CallBack<JobPage>): LiveData<JobPage>

    //转派工单
    fun resendOrder(
        request: ResendOrderRequest,
        callBack: CallBack<ResendOrderModel>
    ): LiveData<ResendOrderModel>

    //申请闭单
    fun applyClose(
        request: ApplyCloseRequest,
        callBack: CallBack<ApplyCloseResponse>
    ): LiveData<ApplyCloseResponse>

    fun exten(request: ExtenDetialRequest, callBack: CallBack<BaseResponse<Object>>): LiveData<BaseResponse<Object>>
}
