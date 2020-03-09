package com.einyun.app.pms.toll.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.toll.constants.URLS;
import com.einyun.app.pms.toll.model.CreateOrderModel;
import com.einyun.app.pms.toll.model.CreateOrderRequest;
import com.einyun.app.pms.toll.model.DivideIdModel;
import com.einyun.app.pms.toll.model.DivideNameModel;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.FeeModel;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.model.FeeSucInfoModel;
import com.einyun.app.pms.toll.model.JumpAdvanceRequset;
import com.einyun.app.pms.toll.model.JumpRequest;
import com.einyun.app.pms.toll.model.JumpVerityModel;
import com.einyun.app.pms.toll.model.LackDetailModel;
import com.einyun.app.pms.toll.model.LackListModel;
import com.einyun.app.pms.toll.model.PaymentAdvanceModel;
import com.einyun.app.pms.toll.model.PaymentAdvanceModel2;
import com.einyun.app.pms.toll.model.QrCodeRequest;
import com.einyun.app.pms.toll.model.QueryFeedDetailsInfoRequest;
import com.einyun.app.pms.toll.model.QueryOrderStateRequest;
import com.einyun.app.pms.toll.model.QueryStateModel;
import com.einyun.app.pms.toll.model.TollModel;
import com.einyun.app.pms.toll.model.WorthModel;
import com.einyun.app.pms.toll.model.WorthTimeModel;
import com.google.gson.JsonObject;


import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_INQUIRIES_ORDER_LIST;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;
import static com.einyun.app.pms.toll.constants.URLS.URL_GET_FEE_QUERY_ORDER_STATE;


public class TollRepository {
    TollServiceApi serviceApi;
    TollServiceApi serviceApi2;
    String baseUrl;
    public TollRepository() {
//        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi("http://106.75.162.186:10018",TollServiceApi.class);
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi("https://fee.einyun.com/",TollServiceApi.class);
}

    public TollRepository(String s) {

        serviceApi2 = EinyunHttpService.Companion.getInstance().getServiceApi(TollServiceApi.class);
    }
    /**
     * get
     * 获取问询详情基本信息
     */
    public void getFeeInfo(FeeRequset requset, CallBack<FeeModel> callBack) {
        serviceApi.getFeeInfo(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 一键催缴
     */
    public void allWorth(FeeRequset requset, CallBack<WorthModel> callBack) {
        serviceApi.allWorth(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取欠费详情基本信息
     */
    public void getFeeDetailInfo(FeeDetailRequset requset, CallBack<LackDetailModel> callBack) {
        serviceApi.getFeeDetailInfo(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取预缴列表
     */
    public void getPaymentAdvanceList(FeeDetailRequset requset, CallBack<PaymentAdvanceModel2> callBack) {
        serviceApi.getPaymentAdvanceList(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 跳缴验证
     */
    public void jumpVerify(JumpRequest requset, CallBack<JumpVerityModel> callBack) {
        serviceApi.jumpVerify(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
//    /**
//     * get
//     * s生成收款二维码
//     */
//    public void createQrCode(int orderId, CallBack<Byte> callBack) {
//        String url=URLS.URL_GET_FEE_QR_CODE+orderId;
//        serviceApi.createQrCode(url).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
////                    if(response.isState()){
//                        callBack.call(response);
////                    }else{
////                        callBack.onFaild(new Exception(response));
////                    }
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//    }
    /**
     * get
     * 预缴验证
     */
    public void jumpAdvanceVerify(JumpAdvanceRequset requset, CallBack<JsonObject> callBack) {
        serviceApi.jumpAdvanceVerify(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
//                    if(response){
                        callBack.call(response.getData());
//                    }else{
//                        callBack.onFaild(new Exception(response));
//                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 生成订单
     */
    public void createOrder(CreateOrderRequest requset, CallBack<CreateOrderModel> callBack) {
        serviceApi.createOrder(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response);
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 查询订单状态
     */
    public void queryOrderState(int requset, CallBack<QueryStateModel> callBack) {
        String url=URL_GET_FEE_QUERY_ORDER_STATE+requset;
        serviceApi.queryOrderState(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 查询已付费明细
     */
    public void queryFeedInfDetails(QueryFeedDetailsInfoRequest requset, CallBack<FeeSucInfoModel> callBack) {
        serviceApi.queryFeedInfDetails(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取欠费详单信息
     */
    public void getLackDetailList(FeeDetailRequset requset, CallBack<TollModel> callBack) {
        serviceApi.getLackDetailList(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取feedid
     */
    public void getFeeDivideId(String did,String type, CallBack<String> callBack) {
        String url= URLS.URL_GET_FEE_DIVIDE_ID+type+did;
//        String url= URLS.URL_GET_FEE_DIVIDE_ID+"0/"+did;
        serviceApi.getFeeDivideId(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取默认分期id
     *
     */
    public void getDefauftDivideId(String userId, CallBack<String> callBack) {
        String url= URLS.URL_GET_DEFAUFT_DIVIDE_ID+userId;
//        String url= URLS.URL_GET_FEE_DIVIDE_ID+"0/"+did;
        serviceApi2.getDefauftDivideId(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取默认分期iName
     *
     */
    public void getDefauftDivideName(String userId, CallBack<List<DivideNameModel>> callBack) {
        String url= URLS.URL_GET_DEFAUFT_DIVIDE_NAME+userId;
        serviceApi2.getDefauftDivideName(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取上次催缴时间
     */
    public void getLastWorthTime(String did, CallBack<WorthTimeModel> callBack) {
        String url= URLS.URL_GET_LAST_WORTH_TIME+did;
        serviceApi.getLastWorthTime(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
}
