package com.einyun.app.library.uc.usercenter.net

import com.einyun.app.library.uc.usercenter.net.request.OrgRequest
import com.einyun.app.library.uc.usercenter.net.response.OrgListResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.usercenter.net
 * @ClassName:      UserCenterServiceApi
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/14 18:01
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/14 18:01
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface UserCenterServiceApi {

    @POST(URLs.URL_USER_CENTER_CHILD_BY_USER_ID)
    fun listOrChildByOrgId(@Body request:OrgRequest):Flowable<OrgListResponse>
}