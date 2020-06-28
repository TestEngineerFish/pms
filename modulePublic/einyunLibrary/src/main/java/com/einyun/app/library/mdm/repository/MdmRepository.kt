package com.einyun.app.library.mdm.repository

import android.text.TextUtils
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.base.paging.bean.PageBean
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.base.paging.bean.Query
import com.einyun.app.base.paging.bean.QueryBuilder
import com.einyun.app.library.core.api.MdmService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.mdm.model.FeedBackListModel
import com.einyun.app.library.mdm.model.GridModel
import com.einyun.app.library.mdm.model.NoticeModel
import com.einyun.app.library.mdm.model.SystemNoticeModel
import com.einyun.app.library.mdm.net.MdmServiceApi
import com.einyun.app.library.mdm.net.URLs
import com.einyun.app.library.mdm.net.request.*
import com.einyun.app.library.mdm.net.response.NoticeListPageResult

class MdmRepository : MdmService {
    override fun getFeedBackDetail(feedId: String, callBack: CallBack<FeedBackListModel>) {
        serviceApi?.getFeedBackDetail(URLs.URL_FEED_BACK_DETAIL+feedId)
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })    }

    override fun getFeedBackList(
        userId: String,
        callBack: CallBack<List<FeedBackListModel>>
    ) {
        var queryBuilder = queryFeedBuilder(
            userId
        )
        serviceApi?.getFeedBackList(queryBuilder.build())
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data.rows)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })     }

    override fun addFeedBack(request: FeedBackAddRequest, callBack: CallBack<Any>) {
        serviceApi?.addFeedBack(request)
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({ response ->
                if (response.isState) {
                    callBack.call(response.data)
                } else {
                    callBack.onFaild(EinyunHttpException(response))
                }
            }, { error -> callBack.onFaild(error) })
    }

    override fun getSystemNotice(callBack: CallBack<SystemNoticeModel>) {
        serviceApi?.getSystemNotice(Any())
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    if (it.data.rows != null && it.data.rows.size != 0) {
                        callBack.call(it.data.rows.get(0))
                    } else {
                        callBack.call(null)
                    }
                } else {
                    callBack.call(null)
                }
            }, {
                callBack.onFaild(it)
            })
    }

    override fun getSystemNoticeDetail(id: String, callBack: CallBack<SystemNoticeModel>) {
        serviceApi?.getSystemNoticeDetail(URLs.URL_GET_SYSTEM_NOTICE_DETAIL + id)
            ?.compose(RxSchedulers.inIoMain())
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

    override fun getNoticeList(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    ) {
        serviceApi?.getNoticeList(queryNoticeBuilder(getNoticeListPageRequest).build())
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.data)
            }, {
                callBack.onFaild(it)
            })
    }

    override fun getNoticeTop(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    ) {
        serviceApi?.getNoticeTop(queryNoticeTopBuilder(getNoticeListPageRequest).build())
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                callBack.call(it.data)
            }, {
                callBack.onFaild(it)
            })
    }

    private fun queryNoticeTopBuilder(
        request: NoticeListPageRequest
    ): QueryBuilder {
        var builder = QueryBuilder()
//        builder.setPageBean(request.pageBean)

        builder.addQueryItem(
            "org_id", request.org_id, "IN",
            Query.RELATION_AND
        )
        if (!TextUtils.isEmpty(request.is_important)) {
            builder.addQueryItem(
                "is_important", request.is_important, Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
        }
        builder.addQueryItem(
            "end_time", request.end_time, "GREAT",
            Query.RELATION_AND
        )
        return builder
    }

    private fun queryNoticeBuilder(
        request: NoticeListPageRequest
    ): QueryBuilder {
        var builder = QueryBuilder()
        builder.setPageBean(request.pageBean)

        builder.addQueryItem(
            "org_id", request.org_id, "IN",
            Query.RELATION_AND
        )
        if (!TextUtils.isEmpty(request.is_important)) {
            builder.addQueryItem(
                "is_important", request.is_important, Query.OPERATION_EQUAL,
                Query.RELATION_AND
            )
        }
        if (!TextUtils.isEmpty(request.release_time)) {
            builder.addQueryItem(
                "release_time", request.release_time + "-01 00:00:00", "GREAT",
                Query.RELATION_AND
            )
            builder.addQueryItem(
                "release_time", request.release_time + "-30 23:59:59", "LESS",
                Query.RELATION_AND
            )
        }
        return builder
    }
    private fun queryFeedBuilder(
        userId: String
    ): QueryBuilder {
        var builder = QueryBuilder()

        builder.addQueryItem(
            "feedback_id", userId, "IN",
            Query.RELATION_AND
        )
        return builder
    }

    var serviceApi: MdmServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(MdmServiceApi::class.java)
    }

    override fun updateNoticeLikeBad(request: UpdateNoticeLikeBadRequest, callBack: CallBack<Any>) {
        serviceApi?.updateNoticeLikeBad(request)
            ?.compose(RxSchedulers.inIoMain())
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

    override fun addReading(addReadingRequest: AddReadingRequest, callBack: CallBack<Any>) {
        serviceApi?.addReadingNotice(addReadingRequest)
            ?.compose(RxSchedulers.inIoMain())
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

    override fun getNoticeDetail(id: String, callBack: CallBack<NoticeModel>) {
        serviceApi?.getNoticeDetail(URLs.URL_NOTICE_DETAIL + id)
            ?.compose(RxSchedulers.inIoMain())
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

    override fun queryUpDown(id: String, memberId: String, callBack: CallBack<Int>) {
        var request = QueryUpDownRequest()
        request.communityAnnouncementId = id
        request.memberId = memberId
        serviceApi?.queryUpDown(request)
            ?.compose(RxSchedulers.inIoMain())
            ?.subscribe({
                if (it.isState) {
                    if (it.data != null) {
                        callBack.call(it.data)
                    }
                } else {
                    callBack.onFaild(EinyunHttpException(it))
                }
            }, {
                callBack.onFaild(it)
            })
    }

    /**
     * 获取分期下面的网格信息
     */
    override fun gridPage(divideId: String, callBack: CallBack<PageResult<GridModel>>) {
        var queryBuilder = QueryBuilder()
            .setPageBean(PageBean())
            .addQueryItem("divideId", divideId)
            .addQueryItem("gridType", "building_grid")
            .addQueryItem("isDeleted", 0)
            .addQueryItem("enabledFlag", 1)
        serviceApi?.gridPage(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
            ?.subscribe(
                {
                    if (it.isState) {
                        callBack.call(it.data)
                    } else {
                        callBack.onFaild(EinyunHttpException(it))
                    }
                }, {
                    callBack.onFaild(it)
                }
            )
    }

}
