package com.einyun.app.library.core.api

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.mdm.model.FeedBackListModel
import com.einyun.app.library.mdm.model.GridModel
import com.einyun.app.library.mdm.model.NoticeModel
import com.einyun.app.library.mdm.model.SystemNoticeModel
import com.einyun.app.library.mdm.net.request.AddReadingRequest
import com.einyun.app.library.mdm.net.request.FeedBackAddRequest
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest
import com.einyun.app.library.mdm.net.request.UpdateNoticeLikeBadRequest
import com.einyun.app.library.mdm.net.response.NoticeListPageResult

interface MdmService :EinyunService{
    fun gridPage(divideId :String,callBack: CallBack<PageResult<GridModel>>)
    fun addReading(addReadingRequest: AddReadingRequest, callBack: CallBack<Any>)
    fun getNoticeDetail(id: String, callBack: CallBack<NoticeModel>)
    fun updateNoticeLikeBad(request: UpdateNoticeLikeBadRequest, callBack: CallBack<Any>)
    fun queryUpDown(id: String, memberId: String, callBack: CallBack<Int>)
    fun getNoticeList(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    )
    fun getNoticeTop(
        getNoticeListPageRequest: NoticeListPageRequest,
        callBack: CallBack<NoticeListPageResult>
    )

    fun getSystemNotice(callBack: CallBack<SystemNoticeModel>)

    fun getSystemNoticeDetail(id: String, callBack: CallBack<SystemNoticeModel>)

    //新增意见反馈
    fun addFeedBack(request: FeedBackAddRequest, callBack: CallBack<Any>)

    //意见反馈列表
    fun getFeedBackList(userId:String, callBack: CallBack<List<FeedBackListModel>>)

    //意见反馈详情
    fun getFeedBackDetail(feedId:String, callBack: CallBack<FeedBackListModel>)
}
