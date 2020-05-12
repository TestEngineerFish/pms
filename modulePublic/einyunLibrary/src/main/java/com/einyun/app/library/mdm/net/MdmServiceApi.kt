package com.einyun.app.library.mdm.net

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.Query
import com.einyun.app.library.mdm.model.NoticeModel
import com.einyun.app.library.mdm.net.request.AddReadingRequest
import com.einyun.app.library.mdm.net.request.QueryUpDownRequest
import com.einyun.app.library.mdm.net.request.UpdateNoticeLikeBadRequest
import com.einyun.app.library.mdm.net.response.GridResponse
import com.einyun.app.library.mdm.net.response.NoticeListResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface MdmServiceApi {

    @POST(URLs.URL_MDM_GRID_INFO)
    fun gridPage(@Body request: Query): Flowable<GridResponse>

    /**
     *通知首页弹窗
     */
    @POST(URLs.URL_NOTICE_LIST)
    fun getNoticeTop(@Body request: Query): Flowable<NoticeListResponse>


    /**
     *公告列表
     */
    @POST(URLs.URL_NOTICE_LIST)
    fun getNoticeList(@Body request: Query): Flowable<NoticeListResponse>

    @GET
    fun getNoticeDetail(@Url url: String): Flowable<BaseResponse<NoticeModel>>

    @POST(URLs.URL_NOTICE_UPDATE_LIKE_BAD)
    fun updateNoticeLikeBad(@Body request: UpdateNoticeLikeBadRequest): Flowable<BaseResponse<BaseResponse<Any>>>

   @POST(URLs.URL_NOTICE_ADD_READING)
    fun addReadingNotice(@Body request: AddReadingRequest): Flowable<BaseResponse<Any>>

    @POST(URLs.URL_NOTICE_QUERY_UP_DOWN)
    fun queryUpDown(@Body request: QueryUpDownRequest): Flowable<BaseResponse<Int>>
}
