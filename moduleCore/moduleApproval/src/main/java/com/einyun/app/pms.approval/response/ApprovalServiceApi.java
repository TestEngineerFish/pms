package com.einyun.app.pms.approval.response;



import com.einyun.app.library.workorder.net.URLs;
import com.einyun.app.pms.approval.module.ApprovalBean;
import com.einyun.app.pms.approval.module.ApprovalDetailInfoBean;
import com.einyun.app.pms.approval.net.URL;


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
public interface ApprovalServiceApi {
    /**
     * 获取审批状态分类
     * @param url
     * @return
     */
    @GET
    Flowable<GetKeyAuditStateResponse> getAuditState(@Url String url);

    /**
     * 获取审批类型分类
     * @param url
     * @return
     */
    @POST
    Flowable<GetKeyAuditTypeResponse> getAuditType(@Url String url);

    /**
     * 已审批列表
     * @param request
     * @return
     */
    @POST(URLs.URL_APPROVE_DONE_LIST)
    Flowable<ApprovalListResponse> query(@Body ApprovalBean request);
    /**
     * 待审批列表
     * @param request
     * @return
     */
    @POST(URLs.URL_APPROVE_TODO_LIST)
    Flowable<ApprovalListResponse> waitApproval(@Body ApprovalBean request);
    /**
     * 我发起的审批列表
     * @param request
     * @return
     */
    @POST(URLs.URL_APPROVE_INITIATED_LIST)
    Flowable<ApprovalListResponse> meSendApproval(@Body ApprovalBean request);
    /**
     * 获取审批详情页基本信息
     * @param
     * @return
     */
    @GET
    Flowable<GetApprovalBasicInfoResponse> getApprovalBasicInfo(@Url String url);
    /**
     * 获取审批详情页  审批详情信息
     * @param
     * @return
     */
    @GET
    Flowable<GetApprovalDetailInfoResponse> getApprovalDetailInfo(@Url String url);
}
