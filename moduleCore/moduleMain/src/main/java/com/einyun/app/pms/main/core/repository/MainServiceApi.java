package com.einyun.app.pms.main.core.repository;


import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.pms.main.core.model.UserStarsBean;
import com.einyun.app.pms.main.core.respone.HasReadResponse;
import com.einyun.app.pms.main.core.respone.UserStarsResponse;


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
public interface MainServiceApi {
    /**
     * 获取个人星星
     * @param
     * @return
     */
    @POST(URLS.URL_GET_USER_STARS)
    Flowable<UserStarsResponse> getStars(@Body UserStarsBean bean);
    /**
     * 是否已读
     * @param
     * @return
     */
    @GET(URLS.URL_GET_HAS_READ)
    Flowable<HasReadResponse> hasRead();

}
