package com.einyun.app.library.finance.net

import com.einyun.app.library.finance.net.request.FinanceRequest
import com.einyun.app.library.finance.net.response.FinanceListResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.finance.net
 * @ClassName:      FinanceServiceApi
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/11/5 0005 上午 10:25
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/11/5 0005 上午 10:25
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
interface FinanceServiceApi {
    //经营详情洞察
    @POST(URLs.URL_FINANCE_DETIAL_DAY)
    fun financeList(@Body request:FinanceRequest):Flowable<FinanceListResponse>
}