package com.einyun.app.pms.plan.repository;

import com.einyun.app.pms.plan.respone.PlanOrderResLinesResponse;

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
public interface PlanOrderServiceApi {


    /**
     * 获取上次催缴时间
     */
    @GET()
    Flowable<PlanOrderResLinesResponse> getLastWorthTime(@Url String url);
}
