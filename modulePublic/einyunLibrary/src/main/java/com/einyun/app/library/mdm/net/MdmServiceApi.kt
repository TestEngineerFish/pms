package com.einyun.app.library.mdm.net

import com.einyun.app.base.paging.bean.Query
import com.einyun.app.library.mdm.net.response.GridResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface MdmServiceApi {

    @POST(URLs.URL_MDM_GRID_INFO)
    fun gridPage(@Body request: Query): Flowable<GridResponse>
}
