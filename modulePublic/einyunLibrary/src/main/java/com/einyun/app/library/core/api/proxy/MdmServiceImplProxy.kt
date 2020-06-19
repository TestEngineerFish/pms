package com.einyun.app.library.core.api.proxy

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.core.api.MdmService
import com.einyun.app.library.mdm.model.FeedBackListModel
import com.einyun.app.library.mdm.model.GridModel
import com.einyun.app.library.mdm.model.NoticeModel
import com.einyun.app.library.mdm.model.SystemNoticeModel
import com.einyun.app.library.mdm.net.request.AddReadingRequest
import com.einyun.app.library.mdm.net.request.FeedBackAddRequest
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest
import com.einyun.app.library.mdm.net.request.UpdateNoticeLikeBadRequest
import com.einyun.app.library.mdm.net.response.NoticeListPageResult
import com.einyun.app.library.mdm.repository.MdmRepository

class MdmServiceImplProxy : MdmService {
    override fun getFeedBackDetail(feedId: String, callBack: CallBack<FeedBackListModel>) {
        repo.getFeedBackDetail(feedId,callBack)     }

    override fun getFeedBackList(userId: String, callBack: CallBack<List<FeedBackListModel>>) {
        repo.getFeedBackList(userId,callBack)    }

    override fun addFeedBack(request: FeedBackAddRequest, callBack: CallBack<Any>) {
        repo.addFeedBack(request,callBack)    }

    override fun getSystemNotice(callBack: CallBack<SystemNoticeModel>) {
        repo.getSystemNotice(callBack)
    }

    override fun getSystemNoticeDetail(id: String, callBack: CallBack<SystemNoticeModel>) {
        repo.getSystemNoticeDetail(id, callBack)
    }

    override fun getNoticeTop(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    ) {
        repo.getNoticeTop(getNoticeListPageRequest, callBack)
    }

    override fun gridPage(divideId: String, callBack: CallBack<PageResult<GridModel>>) {
        repo.gridPage(divideId, callBack)
    }

    var repo = MdmRepository()

    override fun queryUpDown(id: String, memberId: String, callBack: CallBack<Int>) {
        repo.queryUpDown(id, memberId, callBack)
    }

    override fun addReading(addReadingRequest: AddReadingRequest, callBack: CallBack<Any>) {
        repo.addReading(addReadingRequest, callBack)
    }

    override fun getNoticeList(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    ) {
        repo.getNoticeList(getNoticeListPageRequest, callBack)
    }

    override fun getNoticeDetail(id: String, callBack: CallBack<NoticeModel>) {
        repo.getNoticeDetail(id, callBack)
    }

    override fun updateNoticeLikeBad(request: UpdateNoticeLikeBadRequest, callBack: CallBack<Any>) {
        repo.updateNoticeLikeBad(request, callBack)
    }
}
