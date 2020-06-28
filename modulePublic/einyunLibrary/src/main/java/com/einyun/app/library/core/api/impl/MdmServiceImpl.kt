package com.einyun.app.library.core.api.impl

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.core.api.MdmService
import com.einyun.app.library.core.api.proxy.MdmServiceImplProxy
import com.einyun.app.library.mdm.model.FeedBackListModel
import com.einyun.app.library.mdm.model.GridModel
import com.einyun.app.library.mdm.model.NoticeModel
import com.einyun.app.library.mdm.model.SystemNoticeModel
import com.einyun.app.library.mdm.net.request.AddReadingRequest
import com.einyun.app.library.mdm.net.request.FeedBackAddRequest
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest
import com.einyun.app.library.mdm.net.request.UpdateNoticeLikeBadRequest
import com.einyun.app.library.mdm.net.response.NoticeListPageResult

class MdmServiceImpl : MdmService {
    override fun getFeedBackDetail(feedId: String, callBack: CallBack<FeedBackListModel>) {
        proxy.getFeedBackDetail(feedId,callBack)     }

    override fun getFeedBackList(
        userId: String,
        callBack: CallBack<List<FeedBackListModel>>
    ) {
        proxy.getFeedBackList(userId,callBack)    }

    override fun addFeedBack(request: FeedBackAddRequest, callBack: CallBack<Any>) {
        proxy.addFeedBack(request,callBack)    }

    override fun getSystemNotice(callBack: CallBack<SystemNoticeModel>) {
        proxy.getSystemNotice(callBack)
    }

    override fun getSystemNoticeDetail(id: String, callBack: CallBack<SystemNoticeModel>) {
        proxy.getSystemNoticeDetail(id, callBack)
    }

    override fun getNoticeTop(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    ) {
        proxy.getNoticeTop(getNoticeListPageRequest, callBack)
    }

    override fun gridPage(divideId: String, callBack: CallBack<PageResult<GridModel>>) {
        proxy.gridPage(divideId, callBack)
    }

    override fun addReading(addReadingRequest: AddReadingRequest, callBack: CallBack<Any>) {
        proxy.addReading(addReadingRequest, callBack)
    }

    override fun queryUpDown(id: String, memberId: String, callBack: CallBack<Int>) {
        proxy.queryUpDown(id, memberId, callBack)

    }

    override fun getNoticeList(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    ) {
        proxy.getNoticeList(getNoticeListPageRequest, callBack)
    }

    override fun getNoticeDetail(id: String, callBack: CallBack<NoticeModel>) {
        proxy.getNoticeDetail(id, callBack)
    }

    override fun updateNoticeLikeBad(request: UpdateNoticeLikeBadRequest, callBack: CallBack<Any>) {
        proxy.updateNoticeLikeBad(request, callBack)
    }

    var proxy = MdmServiceImplProxy()

}
