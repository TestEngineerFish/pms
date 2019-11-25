package com.einyun.app.pms.pointcheck.net;

import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.pms.pointcheck.net.request.CreatePointCheckRequest;
import com.einyun.app.pms.pointcheck.net.request.PageQueryRequest;
import com.einyun.app.pms.pointcheck.net.response.CheckPointDetialResponse;
import com.einyun.app.pms.pointcheck.net.response.CheckPointListResponse;
import com.einyun.app.pms.pointcheck.net.response.ProjectContentResponse;
import com.einyun.app.pms.pointcheck.net.response.ProjectResponse;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.net
 * @ClassName: CheckPointServiceApi
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 15:23
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 15:23
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface CheckPointServiceApi {
    /**
     * 获取点检事项集
     */
    @GET(URLs.URL_POINT_CHECK_PROJECTS)
    Flowable<ProjectResponse> projects(@Query("ids") String ids);

    /**
     * 根据点检设置获取该点检设置下面的点检内容
     * @param url
     * @return
     */
    @GET
    Flowable<ProjectContentResponse> projectContent(@Url String url);

    /**
     * 获取点检详细
     * @param url
     * @return
     */
    @GET
    Flowable<CheckPointDetialResponse> detial(@Url String url);

    /**
     * 新增点检
     * @param request
     * @return
     */
    @POST(URLs.URL_POINT_CHECK_CREATE)
    Flowable<BaseResponse> create(@Body CreatePointCheckRequest request);

    /**
     * 点检列表，分页查询
     * @param request
     * @return
     */
    @POST(URLs.URL_POINT_CHECK_LIST)
    Flowable<CheckPointListResponse> query(@Body PageQueryRequest request);
}
