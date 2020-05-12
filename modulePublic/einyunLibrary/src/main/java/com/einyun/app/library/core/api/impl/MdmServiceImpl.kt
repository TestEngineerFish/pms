package com.einyun.app.library.core.api.impl

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.core.api.MdmService
import com.einyun.app.library.core.api.proxy.MdmServiceImplProxy
import com.einyun.app.library.mdm.model.GridModel
import com.einyun.app.library.mdm.model.NoticeModel
import com.einyun.app.library.mdm.net.request.AddReadingRequest
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest
import com.einyun.app.library.mdm.net.request.UpdateNoticeLikeBadRequest
import com.einyun.app.library.mdm.net.response.NoticeListPageResult

class MdmServiceImpl : MdmService {
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
