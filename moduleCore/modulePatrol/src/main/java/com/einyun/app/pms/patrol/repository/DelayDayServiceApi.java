package com.einyun.app.pms.patrol.repository;


import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.pms.patrol.model.DelayDayResponse;


import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
public interface DelayDayServiceApi {
    /**
     * 获取反馈信息
     * @param
     * @return
     */
    @GET
    Flowable<DelayDayResponse> getDealyInfo(@Url String url);
}
