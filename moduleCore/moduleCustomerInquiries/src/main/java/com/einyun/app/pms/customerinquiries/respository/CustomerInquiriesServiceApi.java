package com.einyun.app.pms.customerinquiries.respository;


import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.pms.customerinquiries.constants.URLS;
import com.einyun.app.pms.customerinquiries.model.DealRequest;
import com.einyun.app.pms.customerinquiries.model.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.model.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.model.FeedBackRequest;
import com.einyun.app.pms.customerinquiries.model.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.net.response.FeedBackResponse;
import com.einyun.app.pms.customerinquiries.net.response.GetInquiriesTypesResponse;
import com.einyun.app.pms.customerinquiries.net.response.InquiriesDetailResponse;
import com.einyun.app.pms.customerinquiries.net.response.InquiriesListResponse;
import com.einyun.app.pms.customerinquiries.net.response.OrderResponse;

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
public interface CustomerInquiriesServiceApi {

    /**
     * 获取问询类型
     * @param url
     * @return
     */
    @GET
    Flowable<GetInquiriesTypesResponse> getTypes(@Url String url);

    /**
     * 列表
     * @param request
     * @return
     */
    @POST
    Flowable<InquiriesListResponse> getInquiriesList(@Url String url,@Body InquiriesRequestBean request);
    /**
     * 获取问询详情基本信息
     * @param
     * @return
     */
    @GET
    Flowable<InquiriesDetailResponse> getInquiriesDetailInfo(@Url String url);
    /**
     * 处理提交
     * @param
     * @return
     */
    @POST(URLS.URL_GET_INQUIRIES_DEAL)
    Flowable<BaseResponse> dealSubmit(@Body DealRequest bean);

    /**
     * 处理保存
     * @param
     * @return
     */
    @POST(URLS.URL_GET_INQUIRIES_DEAL_SAVE)
    Flowable<BaseResponse> dealSave(@Body DealSaveRequest bean);

    /**
     * 处理保存
     * @param
     * @return
     */
    @POST(URLS.URL_GET_INQUIRIES_DEAL_EVALUATION)
    Flowable<BaseResponse> Evaluation(@Body EvaluationRequest bean);    /**
     * 是否可以申请
     * @param
     * @return
     */
    @GET
    Flowable<BaseResponse> isCanApply(@Url String url);
    /**
     * 获取反馈信息
     * @param
     * @return
     */
    @GET
    Flowable<FeedBackResponse> getFeedbackInfo(@Url String url);
    /**
     * 反馈提交
     * @param
     * @return
     */
    @POST(URLS.URL_GET_FEEDBACK_SUBMIT)
    Flowable<BaseResponse> feedbacksubmit(@Body FeedBackRequest bean);
    /**
     * 获取order信息
     * @param
     * @return
     */
    @GET
    Flowable<OrderResponse> getOrderInfo(@Url String url);
}
