package com.einyun.app.library.workorder.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.base.paging.bean.*
import com.einyun.app.library.core.api.WorkOrderService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean
import com.einyun.app.library.resource.workorder.net.response.RepairsResponse
import com.einyun.app.library.workorder.model.RepairsPage
import com.einyun.app.library.workorder.model.*
import com.einyun.app.library.workorder.net.URLs
import com.einyun.app.library.workorder.net.WorkOrderServiceApi
import com.einyun.app.library.workorder.net.request.*
import com.einyun.app.library.workorder.net.request.ComplainAppendRequest
import com.einyun.app.library.workorder.net.request.CreateClientEnquiryOrderRequest
import com.einyun.app.library.workorder.net.request.*
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse
import retrofit2.http.Url

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
    override fun getUserInfoByHouseId(
        houseId: String,
        callBack: CallBack<List<UserInfoByHouseIdModel>>
    ): LiveData<List<UserInfoByHouseIdModel>> {
        val liveData = MutableLiveData<List<UserInfoByHouseIdModel>>()
        serviceApi?.getUserInfoByHouseId(houseId)?.compose(RxSchedulers.inIoMain())
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

    //客户报修-筛选数据
    override fun getAreaType(callBack: CallBack<AreaModel>): LiveData<AreaModel> {
        val liveData = MutableLiveData<AreaModel>()
        serviceApi?.getAreaType()?.compose(RxSchedulers.inIo())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                    liveData.postValue(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
        return liveData    }


    //报修-处理保存
    override fun saveHandler(
        request: SaveHandleRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.repairHandleSave(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
                liveData.postValue(it.isState)
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }
    override fun complainDetailComplete(
        request: ComplainDetailCompleteRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.complainDetailComplete(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState){
                    callBack.call(it.isState)
                    liveData.postValue(it.isState)
                }else{
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    override fun complainDetailSave(
        request: ComplainDetailCompleteRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.complainDetailSave(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState){
                    callBack.call(it.isState)
                    liveData.postValue(it.isState)
                }else{
                 callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    //报修-派单
    override fun repaireSend(
        request: RepairSendOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.repairSend(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
                liveData.postValue(it.isState)
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }


    override fun postCommunication(
        request: PostCommunicationRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.postCommunication(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    callBack.call(it.isState)
                    liveData.postValue(it.isState)
                } else {
                    callBack.onFaild(EinyunHttpException(it))
                }

            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    override fun startRepair(
        request: CreateClientRepairOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.startRepair(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
                liveData.postValue(it.isState)
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }


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
    override fun complainWorkListdPage(
        pageBean: PageBean,
        mobile: String,
        callBack: CallBack<ComplainModelPageResult>
    ): LiveData<ComplainModelPageResult> {
        var builder = QueryBuilder()
        builder.addQueryItem("F_ts_mobile", mobile)
        builder.addSort("F_ts_time", Query.SORT_DESC)
        var liveData = MutableLiveData<ComplainModelPageResult>()
        serviceApi?.complainWorkListdPage(builder.build())?.compose(RxSchedulers.inIoMain())
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
        return liveData;
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
    override fun appendComplain(request: ComplainAppendRequest, callBack: CallBack<Boolean>):LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.appendComplain(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState){
                    callBack.call(it.isState)
                    liveData.postValue(it.isState)
                }else{
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    /**
     * 启动投诉流程
     */
    override fun startComplain(
        request: CreateClientComplainOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        serviceApi?.startComplain(request)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
                liveData.postValue(it.isState)
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    /**
     * 获取报修类别与条线
     */
    override fun repairTypeList(callBack: CallBack<Door>): LiveData<Door> {
        var liveData = MutableLiveData<Door>()
        serviceApi?.repairTypeList()?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    callBack.call(it.data)
                    liveData.postValue(it.data)
                }
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    /**
     * 启动问询流程
     */
    override fun startEnquiry(
        request: CreateClientEnquiryOrderRequest,
        callBack: CallBack<Boolean>
    ): LiveData<Boolean> {
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

    /**
     * 报修待跟进
     */
    override fun getRepairWaitFollow(
        request: RepairsPageRequest,
        callBack: CallBack<RepairsPage>
    ) {
        var queryBuilder = queryRepairBuilder(
            request
        )
        serviceApi?.getRepairsWaitFollow(queryBuilder.build())?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     * 报修抢单
     */
    override fun getRepairGrab(request: RepairsPageRequest, callBack: CallBack<RepairsPage>) {
        var queryBuilder = queryRepairBuilder(
            request
        )
        serviceApi?.getRepairsGrab(queryBuilder.build())?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *报修-已跟进
     * */
    override fun getRepaiAlreadyFollow(
        request: RepairsPageRequest,
        callBack: CallBack<RepairsPage>
    ) {
        var queryBuilder = queryRepairBuilder(
            request
        )
        serviceApi?.getRepairsAlreadyFollow(queryBuilder.build())?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *报修-已办结
     * */
    override fun getRepaiAlreadyDone(
        request: RepairsPageRequest,
        callBack: CallBack<RepairsPage>
    ) {
        var queryBuilder = queryRepairBuilder(
            request
        )
        serviceApi?.getRepairAlreadyDone(queryBuilder.build())?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *报修-待反馈
     * */
    override fun getRepairWaitFeed(request: RepairsPageRequest, callBack: CallBack<RepairsPage>) {
        var queryBuilder = queryRepairBuilder(
            request
        )
        serviceApi?.getRepairWaitFeed(queryBuilder.build())?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *报修-抄送我
     * */

    override fun getRepairCopyMe(
        request: RepairsPageRequest,
        callBack: CallBack<RepairsPage>
    ) {
        var queryBuilder = queryRepairBuilder(
            request
        )
        serviceApi?.getRepairCopyMe(queryBuilder.build())?.compose(RxSchedulers.inIo())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     * 投诉待跟进
     */
    fun getComplainWaitFollow(
        request: ComplainPageRequest,
        callBack: CallBack<ComplainPage>
    ) {
        var queryBuilder = queryComplainBuilder(
            request
        )
        serviceApi?.getComplainWaitFollow(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *投诉-已跟进
     * */
    fun getComplainAlreadyFollow(
        request: ComplainPageRequest,
        callBack: CallBack<ComplainPage>
    ) {
        var queryBuilder = queryComplainBuilder(
            request
        )
        serviceApi?.getComplainAlreadyFollow(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *投诉-已办结
     * */
    fun getComplainAlreadyDone(
        request: ComplainPageRequest,
        callBack: CallBack<ComplainPage>
    ) {
        var queryBuilder = queryComplainBuilder(
            request
        )
        serviceApi?.getComplainAlreadyDone(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *投诉-抄送我
     * */
    fun getComplainCopyMe(
        request: ComplainPageRequest,
        callBack: CallBack<ComplainPage>
    ) {
        var queryBuilder = queryComplainBuilder(
            request
        )
        serviceApi?.getComplainCopyMe(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     *投诉-待反馈
     * */
    fun getComplainWaitFeed(
        request: ComplainPageRequest,
        callBack: CallBack<ComplainPage>
    ) {
        var queryBuilder = queryComplainBuilder(
            request
        )
        serviceApi?.getComplainWaitFeed(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                { response ->
                    if (response.isState) {
                        callBack.call(response.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, { callBack.onFaild(it) }
            )
    }

    /**
     * 抢单
     * */
    override fun grabRepair(taskId: String, callBack: CallBack<Boolean>): MutableLiveData<Boolean> {
        var liveData = MutableLiveData<Boolean>()
        var url = URLs.URL_REPAIR_GRAB + taskId
        serviceApi?.grabRepair(url)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.isState)
                liveData.postValue(it.isState)
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    override fun getClientOrderDetail(instId:String, taskId:String, callBack: CallBack<RepairsDetailModel>):LiveData<RepairsDetailModel>{
        var liveData = MutableLiveData<RepairsDetailModel>()
        serviceApi?.getClientOrderDetail(instId,taskId)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState){
                    liveData.postValue(it.data)
                    callBack.call(it.data)
                }else{
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
        return liveData
    }

    /**
     * 查看报修详情
     * */
    override fun getRepairDetail(
        instId: String,
        callBack: CallBack<RepairsDetailModel>
    ): LiveData<RepairsDetailModel> {
        var liveData = MutableLiveData<RepairsDetailModel>()
        var url = URLs.URL_REPAIR_DETAIL + instId
        serviceApi?.getRepairDetail(url)?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { callBack.onFaild(it) }
            )
        return liveData
    }

    private fun queryRepairBuilder(
        request: RepairsPageRequest
    ): QueryBuilder {
        var builder = QueryBuilder()
        builder.addQueryItem(
            "bx_dk_id",
            request.bx_dk_id,
            Query.OPERATION_EQUAL,
            Query.RELATION_AND
        )
            .addQueryItem(
                "bx_area_id",
                request.bx_area_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem(
                "bx_cate_lv1_id",
                request.bx_cate_lv1_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem(
                "bx_cate_lv2_id",
                request.bx_cate_lv2_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem("state", request.state, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("node_id_", request.node_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("owner_id_", request.owner_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addSort("bx_time", request.DESC)
            .setPageBean(request.pageBean)
        return builder
    }

    private fun queryComplainBuilder(
        request: ComplainPageRequest
    ): QueryBuilder {
        var builder = QueryBuilder()
        builder.addQueryItem(
            "ts_dk_id",
            request.ts_dk_id,
            Query.OPERATION_EQUAL,
            Query.RELATION_AND
        )
            .addQueryItem(
                "ts_area_id",
                request.ts_area_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem(
                "ts_cate_lv1_id",
                request.ts_cate_lv1_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem(
                "ts_cate_lv2_id",
                request.ts_cate_lv2_id,
                Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
            .addQueryItem("state", request.state, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("node_id_", request.node_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addQueryItem("owner_id_", request.owner_id_, Query.OPERATION_EQUAL, Query.RELATION_AND)
            .addSort("F_ts_time", request.DESC)
            .setPageBean(request.pageBean)
        return builder
    }

}