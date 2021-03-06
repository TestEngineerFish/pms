package com.einyun.app.pms.toll.respository;


import com.alibaba.fastjson.JSON;
import com.einyun.app.base.http.BaseResponse;

import com.einyun.app.pms.toll.constants.URLS;
import com.einyun.app.pms.toll.model.AddHouserRequset;
import com.einyun.app.pms.toll.model.CreateOrderModel;
import com.einyun.app.pms.toll.model.CreateOrderRequest;
import com.einyun.app.pms.toll.model.DivideIdModel;
import com.einyun.app.pms.toll.model.DivideNameModel;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.model.GetNameRequset;
import com.einyun.app.pms.toll.model.JumpAdvanceRequset;
import com.einyun.app.pms.toll.model.JumpRequest;
import com.einyun.app.pms.toll.model.QrCodeRequest;
import com.einyun.app.pms.toll.model.QueryFeedDetailsInfoRequest;
import com.einyun.app.pms.toll.model.QueryOrderStateRequest;
import com.einyun.app.pms.toll.net.response.AddHouserResponse;
import com.einyun.app.pms.toll.net.response.DicRelationResponse;
import com.einyun.app.pms.toll.net.response.DivideIdNameResponse;
import com.einyun.app.pms.toll.net.response.DivideIdResponse;
import com.einyun.app.pms.toll.net.response.FeeDetailResponse;
import com.einyun.app.pms.toll.net.response.FeeResponse;
import com.einyun.app.pms.toll.net.response.FeeSucResponse;
import com.einyun.app.pms.toll.net.response.GetHadSignResponse;
import com.einyun.app.pms.toll.net.response.GetLogoResponse;
import com.einyun.app.pms.toll.net.response.GetNameResponse;
import com.einyun.app.pms.toll.net.response.JumpAdvanceVerityResponse;
import com.einyun.app.pms.toll.net.response.JumpVerityResponse;
import com.einyun.app.pms.toll.net.response.LackListResponse;
import com.einyun.app.pms.toll.net.response.LastWorthTimeResponse;
import com.einyun.app.pms.toll.net.response.PaymentAdvanceResponse;
import com.einyun.app.pms.toll.net.response.QueryStateResponse;
import com.einyun.app.pms.toll.net.response.SetSignResponse;
import com.einyun.app.pms.toll.net.response.WorthResponse;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.function.BinaryOperator;

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
public interface TollServiceApi {

    /**
     * 获取欠费信息
     */
    @POST(URLS.URL_GET_FEE_INFO)
    Flowable<FeeResponse> getFeeInfo(@Body FeeRequset bean);
    /**
     * 一键催缴
     */
    @POST(URLS.URL_GET_FEE_ALL_WORTH)
    Flowable<WorthResponse> allWorth(@Body FeeRequset bean);
    /**
     * 获取房产欠费详情
     */
    @POST(URLS.URL_GET_FEE_DETAIL_INFO)
    Flowable<FeeDetailResponse> getFeeDetailInfo(@Body FeeDetailRequset bean);
    /**
     * 获取已有标签
     */
    @POST(URLS.URL_GET_SIGN)
    Flowable<GetHadSignResponse> getSign(@Body FeeDetailRequset bean);
    /**
     * 打标签
     */
    @POST(URLS.URL_SET_SIGN)
    Flowable<SetSignResponse> setSign(@Body FeeDetailRequset bean);
    /**
     * 获取预交费列表
     */
    @POST(URLS.URL_GET_FEE_ADVANCE)
    Flowable<PaymentAdvanceResponse> getPaymentAdvanceList(@Body FeeDetailRequset bean);
    /**
     * 跳缴验证
     */
    @POST(URLS.URL_GET_FEE_JUMP_VERIFY)
    Flowable<JumpVerityResponse> jumpVerify(@Body JumpRequest bean);
    /**
     * 生成收款二维码
     */
    @GET()
    Flowable<Byte> createQrCode(@Url String url);
    /**
     * 预缴验证
     */
    @POST(URLS.URL_GET_FEE_JUMP_ADVANCE_VERIFY)
    Flowable<JumpAdvanceVerityResponse> jumpAdvanceVerify(@Body JumpAdvanceRequset bean);
    /**
     * 生成订单
     */
    @POST(URLS.URL_GET_FEE_CREATE_ORDER)
    Flowable<CreateOrderModel> createOrder(@Body CreateOrderRequest bean);
    /**
     * 查询已付费明细
     */
    @POST(URLS.URL_GET_FEE_QUERY_FEEDDETAILS)
    Flowable<FeeSucResponse> queryFeedInfDetails(@Body QueryFeedDetailsInfoRequest bean);
    /**
     * 查询name
     */
    @POST(URLS.URL_GET_NAME_FROM_PHONE)
    Flowable<GetNameResponse> getNameFromPhone(@Body GetNameRequset bean);
    /**
     * 添加住户
     */
    @POST(URLS.URL_GET_ADD_HOUSER)
    Flowable<AddHouserResponse> AddHouser(@Body AddHouserRequset bean);
    /**
     * 查询订单状态
     */
    @POST()
    Flowable<QueryStateResponse> queryOrderState(@Url String url);
    /**
     * 获取欠费详单
     */
    @POST(URLS.URL_GET_FEE_LACK_DETAIL_LIST)
    Flowable<LackListResponse> getLackDetailList(@Body FeeDetailRequset bean);
    /**
     * 获取did
     */
    @POST()
    Flowable<DivideIdResponse> getFeeDivideId(@Url String url);
    /**
     * 获取默认园区ID
     */
    @GET()
    Flowable<DivideIdResponse> getDefauftDivideId(@Url String url);
    /**
     * 获取默认园区Name
     */
    @GET()
    Flowable<DivideIdNameResponse> getDefauftDivideName(@Url String url);
    /**
     * 获取上次催缴时间
     */
    @GET()
    Flowable<LastWorthTimeResponse> getLastWorthTime(@Url String url);
    /**
     * 获取Dickey
     */
    @GET()
    Flowable<DicRelationResponse> getDicKey(@Url String url);
    /**
     * 获取logo
     */
    @GET()
    Flowable<GetLogoResponse> getLogo(@Url String url);
}
