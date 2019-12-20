package com.einyun.app.library.mdm.net

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.Query
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface MdmServiceApi {

    @POST(URLs.URL_MDM_GRID_INFO)
    fun createSendOrder(@Body request: Query): Flowable<BaseResponse<Any>>
}
