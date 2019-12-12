package com.einyun.app.pms.mine.response;


import com.einyun.app.base.http.BaseResponse;

import com.einyun.app.pms.mine.constants.URLS;
import com.einyun.app.pms.mine.module.FeedBackBean;
import com.einyun.app.pms.mine.module.GetUserByccountBean;
import com.einyun.app.pms.mine.module.UserStarsBean;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.net
 * @ClassName: CheckPointServiceApi
 * @Description: java类作用描述
 * @CreateDate: 2019/10/08 15:23
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 15:23
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface FeedBackServiceApi {

    /**
     * 提交审批
     * @param
     * @return
     */
    @POST(URLS.URL_SUMIT_FEED_BACK)
    Flowable<BaseResponse> sumitFeedBack(@Body FeedBackBean bean);
    /**
     * 获取个人信息
     * @param url
     * @return
     */
    @GET
    Flowable<UserInfoResponse> getUserInfo(@Url String url);
    /**
     * 获取个人签名
     * @param url
     * @return
     */
    @GET
    Flowable<String> getSignText(@Url String url);
    /**
     * 获取个人星星
     * @param
     * @return
     */
    @POST(URLS.URL_GET_USER_STARS)
    Flowable<UserStarsResponse> getStars(@Body UserStarsBean bean);
    /**
     * 更新个人头像
     */
    @PUT(URLS.URL_GET_UPLOAD_PIC)
    Flowable<BaseResponse> upload(@Body GetUserByccountBean bean);
}
