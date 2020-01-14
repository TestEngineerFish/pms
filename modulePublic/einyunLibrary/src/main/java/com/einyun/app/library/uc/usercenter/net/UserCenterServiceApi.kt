package com.einyun.app.library.uc.usercenter.net

import androidx.work.WorkRequest
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.portal.dictdata.net.URLS
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.usercenter.model.HouseModel
import com.einyun.app.library.uc.usercenter.model.OrgModel
import com.einyun.app.library.uc.usercenter.net.request.OrgRequest
import com.einyun.app.library.uc.usercenter.net.request.SearchUserRequest
import com.einyun.app.library.uc.usercenter.net.response.OrgListResponse
import com.einyun.app.library.uc.usercenter.net.response.WorkStatusResponse
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse
import io.reactivex.Flowable
import retrofit2.http.*

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
    fun listOrChildByOrgId(@Body request: OrgRequest): Flowable<OrgListResponse>

    @GET
    fun userCenterUserList(@Url url: String): Flowable<OrgListResponse>

    @GET(URLs.URL_GET_WORK_STATUS)
    fun getWorkStatus(@Query("userId") userId: String): Flowable<WorkStatusResponse>

    @GET(URLs.URL_UPDATE_WORK_STATUS)
    fun updateWorkStatus(@Query("userId") userId: String, @Query("account") account: String, @Query("status") status: String): Flowable<WorkStatusResponse>

    @GET(URLs.URL_GET_DISPOSE_PERSON)
    fun getDisposePerson(@Query("orgId") orgId: String, @Query("dimCode") dimCode: String): Flowable<BaseResponse<List<OrgModel>>>

    @GET(URLs.URL_GET_CHECKED_PERSON)
    fun getCheckedPerson(@Query("orgId") orgId: String): Flowable<BaseResponse<List<OrgModel>>>
    @POST(URLs.URL_SEARCH_USER_BY_CONDITION)
    fun searchUserByCondition(@Body request: SearchUserRequest):Flowable<BaseResponse<PageResult<GetMappingByUserIdsResponse>>>

    @GET(URLs.URL_GET_HOUSE_BY_CONDITION)
    fun getHouseByCondition(@Query("divideId") divide: String,@Query("id") id: String):Flowable<BaseResponse<List<HouseModel>>>

    @GET(URLs.URL_GET_HOUSE_BY_CONDITION)
    fun getHouseByCondition(@Query("divideId") divide: String):Flowable<BaseResponse<List<HouseModel>>>

}