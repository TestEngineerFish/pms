package com.einyun.app.pms.disqualified.repository;


import com.einyun.app.pms.disqualified.db.CreateUnQualityRequest;
import com.einyun.app.pms.disqualified.db.UnQualityFeedBackRequest;
import com.einyun.app.pms.disqualified.db.UnQualityVerificationRequest;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.pms.disqualified.constants.URLS;
import com.einyun.app.pms.disqualified.net.request.DisqualifiedListRequest;
import com.einyun.app.pms.disqualified.net.response.DisqualifiedDetailResponse;
import com.einyun.app.pms.disqualified.net.response.DisqualifiedListResponse;
import com.einyun.app.pms.disqualified.net.response.GetDisqualifiedTypesResponse;
import com.einyun.app.pms.disqualified.net.response.GetOrderCodeResponse;


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
public interface DisqualifiedServiceApi {

    /**
     * 获取问询类型
     * @param url
     * @return
     */
    @GET
    Flowable<GetDisqualifiedTypesResponse> getTypes(@Url String url);
    /**
     * 获取不合格单号
     * @param url
     * @return
     */
    @GET
    Flowable<GetOrderCodeResponse> getOrderCode(@Url String url);

    /**
     * 处理提交
     * @param
     * @return
     */
    @POST(URLS.URL_CREATE_ORDER)
    Flowable<BaseResponse> dealSubmit(@Body CreateUnQualityRequest bean);
    /**
     * 处理反馈
     * @param
     * @return
     */
    @POST(URLS.URL_FEED_BACH)
    Flowable<BaseResponse> dealFeedBack(@Body UnQualityFeedBackRequest bean);
    /**
     * 处理验证
     * @param
     * @return
     */
    @POST(URLS.URL_VERIFICATION)
    Flowable<BaseResponse> dealValidate(@Body UnQualityVerificationRequest bean);
    /**
     * 不合格单列表
     * @param request
     * @return
     */
    @POST
    Flowable<DisqualifiedListResponse> getDisqualifiedList(@Url String url, @Body DisqualifiedListRequest request);
    /**
     * 获取待跟进详情基本信息
     * @param
     * @return
     */
    @GET
    Flowable<DisqualifiedDetailResponse> getTODODetailInfo(@Url String url);
//    /**
//     * 获取问询详情基本信息
//     * @param
//     * @return
//     */
//    @GET
//    Flowable<InquiriesDetailResponse> getInquiriesDetailInfo(@Url String url);
//    /**
//     * 处理提交
//     * @param
//     * @return
//     */
//    @POST(URLS.URL_GET_INQUIRIES_DEAL)
//    Flowable<BaseResponse> dealSubmit(@Body DealRequest bean);
//
//    /**
//     * 处理保存
//     * @param
//     * @return
//     */
//    @POST(URLS.URL_GET_INQUIRIES_DEAL_SAVE)
//    Flowable<BaseResponse> dealSave(@Body DealSaveRequest bean);
//
//    /**
//     * 处理保存
//     * @param
//     * @return
//     */
//    @POST(URLS.URL_GET_INQUIRIES_DEAL_EVALUATION)
//    Flowable<BaseResponse> Evaluation(@Body EvaluationRequest bean);    /**
//     * 是否可以申请
//     * @param
//     * @return
//     */
//    @GET
//    Flowable<BaseResponse> isCanApply(@Url String url);
//    /**
//     * 获取反馈信息
//     * @param
//     * @return
//     */
//    @GET
//    Flowable<FeedBackResponse> getFeedbackInfo(@Url String url);
//    /**
//     * 反馈提交
//     * @param
//     * @return
//     */
//    @POST(URLS.URL_GET_FEEDBACK_SUBMIT)
//    Flowable<BaseResponse> feedbacksubmit(@Body FeedBackRequest bean);
//    /**
//     * 获取order信息
//     * @param
//     * @return
//     */
//    @GET
//    Flowable<OrderResponse> getOrderInfo(@Url String url);
}
