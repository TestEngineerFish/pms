package com.einyun.app.common.repository;


import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.common.viewmodel.DisqualifiedDetailResponse;
import com.einyun.app.common.viewmodel.GetApprovalBasicInfoResponse;


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
public interface MsgServiceApi {
    /**
     * 单个已读
     */
    @GET()
    Flowable<BaseResponse> singleRead(@Url String url);
    /**
     * 获取审批详情
     */
    @GET()
    Flowable<GetApprovalBasicInfoResponse> getApprovalBasicInfo(@Url String url);
    /**
     * 获取待跟进详情基本信息
     * @param
     * @return
     */
    @GET
    Flowable<DisqualifiedDetailResponse> getTODODetailInfo(@Url String url);
}
