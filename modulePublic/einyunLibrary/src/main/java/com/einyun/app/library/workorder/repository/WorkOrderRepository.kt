package com.einyun.app.library.workorder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.base.paging.bean.*
import com.einyun.app.library.core.api.WorkOrderService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.workorder.model.*
import com.einyun.app.library.workorder.net.WorkOrderServiceApi
import com.einyun.app.library.workorder.net.request.ComplainAppendRequest
import com.einyun.app.library.workorder.net.request.ComplainStartRequest
import com.einyun.app.library.workorder.net.request.CreateClientEnquiryOrderRequest
import com.einyun.app.library.workorder.net.request.RepairStartRequest
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.workorder.repository
 * @ClassName:      WorkOrderRepository
 * @Description:     WorkOrder网络数据处理
 * @Author:         chumingjun
 * @CreateDate:     2019/10/17 11:03
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/17 11:03
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class WorkOrderRepository : WorkOrderService {
    override fun getMappingByUserIds(
        request: List<String>,
        callBack: CallBack<Map<String, GetMappingByUserIdsResponse>>
    ): LiveData<Map<String, GetMappingByUserIdsResponse>> {
        val liveData = MutableLiveData<Map<String, GetMappingByUserIdsResponse>>()
        serviceApi?.getMappingByUserIds(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                }

            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    var serviceApi: WorkOrderServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(WorkOrderServiceApi::class.java)
    }

    /**
     * 获取抢单数量
     */
    fun getGrapCount(callBack: CallBack<Int>) {
        serviceApi?.getGrapCount()?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
    }

    /**
     * 获取审批数量
     */
    override fun getAuditCount(callBack: CallBack<Int>): LiveData<Int> {
        val liveData = MutableLiveData<Int>()
        serviceApi?.getAuditCount()?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData
    }

    /**
     * 获取待办数量（客户报修，客户询问，客户投诉）
     */
    override fun getBlocklogNums(callBack: CallBack<BlocklogNums>): LiveData<BlocklogNums> {
        val liveData = MutableLiveData<BlocklogNums>()
        var queryFilter = Query()
        var queryItem = QueryItem<Any>()
        var querys = ArrayList<QueryItem<*>>()
        querys.add(queryItem)
        queryItem.operation = "LIKE"
        queryItem.relation = "AND"
        queryFilter.querys = querys
        serviceApi?.getBlockLogNums(queryFilter)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error ->
                callBack.onFaild(error)
            })
        return liveData
    }

    /**
     *  客户投诉列表 待跟进
     */
    fun complainPendingPage(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String,
        callBack: CallBack<ComplainModelPageResult>
    ) {
        var queryBuilder = queryComplianBuilder(pageBean, divideId, property, type, state)
        serviceApi?.complainPendingPage(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  客户投诉列表 待反馈
     */
    fun complainFeedbackPendingPage(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String,
        callBack: CallBack<ComplainModelPageResult>
    ) {
        var queryBuilder = queryComplianBuilder(pageBean, divideId, property, type, state)
        serviceApi?.complainFeedbackPendingPage(queryBuilder.build())
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  客户投诉列表 已跟进
     */
    fun complainFlowedPage(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String,
        callBack: CallBack<ComplainModelPageResult>
    ) {
        var queryFilter = queryComplianBuilder(pageBean, divideId, property, type, state)
        serviceApi?.complainFlowedPage(queryFilter.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  客户投诉列表 已办结
     */
    fun complainCompletedPage(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String,
        callBack: CallBack<ComplainModelPageResult>
    ) {
        var queryFilter = queryComplianBuilder(pageBean, divideId, property, type, state)
        serviceApi?.complainCompletedPage(queryFilter.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  客户投诉列表 抄送我的
     */
    fun complainCopy2MePage(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String,
        callBack: CallBack<ComplainModelPageResult>
    ) {
        var queryFilter = queryComplianBuilder(pageBean, divideId, property, type, state)
        serviceApi?.complainCopy2MePage(queryFilter.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  客户投诉列表
     */
    fun complainCustomerFlowPage(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String,
        userId: String,
        callBack: CallBack<ComplainFlowPageResult>
    ) {
        var queryBuilder = queryFilter(pageBean, divideId, property, type, state, userId)
        serviceApi?.complainCustomerFlowPage(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     * 根据参数（如：手机号）查询处理中的投诉列表
     */
    fun complainWorkListdPage(
        pageBean: PageBean,
        mobile: String,
        callBack: CallBack<ComplainModelPageResult>
    ) {
        var builder = QueryBuilder()
        builder.addQueryItem("F_ts_mobile", mobile)
        builder.addSort("F_ts_time", Query.SORT_DESC)
        serviceApi?.complainWorkListdPage(builder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  获取投诉、问询工单类别与条线
     */
    override fun typeAndLineList(callBack: CallBack<List<TypeAndLine>>): LiveData<List<TypeAndLine>> {
        var liveData = MutableLiveData<List<TypeAndLine>>()
        serviceApi?.typeAndLineList()?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    /**
     * 追加投诉
     */
    fun appendComplain(request: ComplainAppendRequest, callBack: CallBack<Boolean>) {
        serviceApi?.appendComplain(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     * 启动投诉流程
     */
    fun startComplain(request: ComplainStartRequest, callBack: CallBack<Boolean>) {
        serviceApi?.startComplain(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     * 获取报修类别与条线
     */
    fun repairTypeList(callBack: CallBack<DoorResult>) {
        serviceApi?.repairTypeList()?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    callBack.call(it.data)
                } else {
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     * 启动报修流程
     */
    fun startRepair(request: RepairStartRequest, callBack: CallBack<Boolean>) {
        serviceApi?.startRepair(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     * 启动问询流程
     */
    override fun startEnquiry(request: CreateClientEnquiryOrderRequest, callBack: CallBack<Boolean>): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.startEnquiry(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
                liveData.postValue(it.isState)
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    /**
     *  审批列表 待审批
     */
    fun approvePendingList(
        pageBean: PageBean,
        divideId: String,
        divideName: String,
        auditType: String,
        auditSubType: String,
        status: String,
        callBack: CallBack<List<Approve>>
    ) {
        var queryBuilder =
            queryApproveBuilder(pageBean, divideId, divideName, auditType, auditSubType, status)
        serviceApi?.approvePendingList(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    callBack.call(it.data)
                } else {
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  审批列表 已审批
     */
    fun approveDoneList(
        pageBean: PageBean,
        divideId: String,
        divideName: String,
        auditType: String,
        auditSubType: String,
        status: String,
        callBack: CallBack<List<Approve>>
    ) {
        var queryBuilder =
            queryApproveBuilder(pageBean, divideId, divideName, auditType, auditSubType, status)
        serviceApi?.approveDoneList(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    callBack.call(it.data)
                } else {
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     *  审批列表 我发起的
     */
    fun approveMyList(
        pageBean: PageBean,
        divideId: String,
        divideName: String,
        auditType: String,
        auditSubType: String,
        status: String,
        callBack: CallBack<List<Approve>>
    ) {
        var queryBuilder =
            queryApproveBuilder(pageBean, divideId, divideName, auditType, auditSubType, status)
        serviceApi?.approveMyList(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    callBack.call(it.data)
                } else {
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    private fun queryApproveBuilder(
        pageBean: PageBean,
        divideId: String,
        divideName: String,
        auditType: String,
        auditSubType: String,
        status: String
    ): QueryBuilder {
        var queryBuilder = QueryBuilder()
        queryBuilder.setPageBean(pageBean)
            .addQueryItem("divide_id", divideId, Query.RELATION_AND)
            .addQueryItem("divide_name", divideName, Query.RELATION_AND)
            .addQueryItem("audit_type", auditType, Query.RELATION_AND)
            .addQueryItem("audit_sub_type", auditSubType, Query.RELATION_AND)
            .addQueryItem("vo.status", status, Query.RELATION_AND)
        return queryBuilder
    }


    private fun queryFilter(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String,
        userId: String
    ): QueryBuilder {
        var builder = queryComplianBuilder(pageBean, divideId, property, type, state)
        builder.addQueryItem("F_ts_recorder_id", userId, Query.RELATION_AND)
        return builder

    }

    private fun queryComplianBuilder(
        pageBean: PageBean,
        divideId: String,
        property: String,
        type: String,
        state: String
    ): QueryBuilder {
        var builder = QueryBuilder()

        builder.addQueryItem("F_ts_dk_id", divideId, Query.RELATION_AND)
            .addQueryItem("F_ts_property_id", property, Query.RELATION_AND)
            .addQueryItem("F_ts_cate_id", type, property, Query.RELATION_AND)
            .addQueryItem("F_state", state, Query.RELATION_AND)
            .addSort("F_ts_time", Query.SORT_DESC)
            .setPageBean(pageBean)

        return builder
    }


}