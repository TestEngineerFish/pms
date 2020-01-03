package com.einyun.app.library.resource.net

import com.einyun.app.library.resource.net.response.LineTypeResponse
import io.reactivex.Flowable
import retrofit2.http.GET

interface ResourceServiceApi {
    /**
     *  获取条线分类
     */
    @GET(URLs.URL_RESOURCE_GET_LINE_AND_TYPE)
    fun getLineAndLine(): Flowable<LineTypeResponse>
}
