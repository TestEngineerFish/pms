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
 * @Description: java???????????????
 * @CreateDate: 2019/10/08 15:23
 * @UpdateUser: ????????????
 * @UpdateDate: 2019/10/08 15:23
 * @UpdateRemark: ???????????????
 * @Version: 1.0
 */
public interface TollServiceApi {

    /**
     * ??????????????????
     */
    @POST(URLS.URL_GET_FEE_INFO)
    Flowable<FeeResponse> getFeeInfo(@Body FeeRequset bean);
    /**
     * ????????????
     */
    @POST(URLS.URL_GET_FEE_ALL_WORTH)
    Flowable<WorthResponse> allWorth(@Body FeeRequset bean);
    /**
     * ????????????????????????
     */
    @POST(URLS.URL_GET_FEE_DETAIL_INFO)
    Flowable<FeeDetailResponse> getFeeDetailInfo(@Body FeeDetailRequset bean);
    /**
     * ??????????????????
     */
    @POST(URLS.URL_GET_SIGN)
    Flowable<GetHadSignResponse> getSign(@Body FeeDetailRequset bean);
    /**
     * ?????????
     */
    @POST(URLS.URL_SET_SIGN)
    Flowable<SetSignResponse> setSign(@Body FeeDetailRequset bean);
    /**
     * ?????????????????????
     */
    @POST(URLS.URL_GET_FEE_ADVANCE)
    Flowable<PaymentAdvanceResponse> getPaymentAdvanceList(@Body FeeDetailRequset bean);
    /**
     * ????????????
     */
    @POST(URLS.URL_GET_FEE_JUMP_VERIFY)
    Flowable<JumpVerityResponse> jumpVerify(@Body JumpRequest bean);
    /**
     * ?????????????????????
     */
    @GET()
    Flowable<Byte> createQrCode(@Url String url);
    /**
     * ????????????
     */
    @POST(URLS.URL_GET_FEE_JUMP_ADVANCE_VERIFY)
    Flowable<JumpAdvanceVerityResponse> jumpAdvanceVerify(@Body JumpAdvanceRequset bean);
    /**
     * ????????????
     */
    @POST(URLS.URL_GET_FEE_CREATE_ORDER)
    Flowable<CreateOrderModel> createOrder(@Body CreateOrderRequest bean);
    /**
     * ?????????????????????
     */
    @POST(URLS.URL_GET_FEE_QUERY_FEEDDETAILS)
    Flowable<FeeSucResponse> queryFeedInfDetails(@Body QueryFeedDetailsInfoRequest bean);
    /**
     * ??????name
     */
    @POST(URLS.URL_GET_NAME_FROM_PHONE)
    Flowable<GetNameResponse> getNameFromPhone(@Body GetNameRequset bean);
    /**
     * ????????????
     */
    @POST(URLS.URL_GET_ADD_HOUSER)
    Flowable<AddHouserResponse> AddHouser(@Body AddHouserRequset bean);
    /**
     * ??????????????????
     */
    @POST()
    Flowable<QueryStateResponse> queryOrderState(@Url String url);
    /**
     * ??????????????????
     */
    @POST(URLS.URL_GET_FEE_LACK_DETAIL_LIST)
    Flowable<LackListResponse> getLackDetailList(@Body FeeDetailRequset bean);
    /**
     * ??????did
     */
    @POST()
    Flowable<DivideIdResponse> getFeeDivideId(@Url String url);
    /**
     * ??????????????????ID
     */
    @GET()
    Flowable<DivideIdResponse> getDefauftDivideId(@Url String url);
    /**
     * ??????????????????Name
     */
    @GET()
    Flowable<DivideIdNameResponse> getDefauftDivideName(@Url String url);
    /**
     * ????????????????????????
     */
    @GET()
    Flowable<LastWorthTimeResponse> getLastWorthTime(@Url String url);
    /**
     * ??????Dickey
     */
    @GET()
    Flowable<DicRelationResponse> getDicKey(@Url String url);
    /**
     * ??????logo
     */
    @GET()
    Flowable<GetLogoResponse> getLogo(@Url String url);
}
