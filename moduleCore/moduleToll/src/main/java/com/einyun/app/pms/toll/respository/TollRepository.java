package com.einyun.app.pms.toll.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.utils.PicEvUtils;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.toll.constants.URLS;
import com.einyun.app.pms.toll.model.AddHouserModel;
import com.einyun.app.pms.toll.model.AddHouserRequset;
import com.einyun.app.pms.toll.model.CreateOrderModel;
import com.einyun.app.pms.toll.model.CreateOrderRequest;
import com.einyun.app.pms.toll.model.DicRelationModel;
import com.einyun.app.pms.toll.model.DivideIdModel;
import com.einyun.app.pms.toll.model.DivideNameModel;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.FeeModel;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.model.FeeSucInfoModel;
import com.einyun.app.pms.toll.model.GetLogoModel;
import com.einyun.app.pms.toll.model.GetNameModel;
import com.einyun.app.pms.toll.model.GetNameRequset;
import com.einyun.app.pms.toll.model.GetSignModel;
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
import com.einyun.app.pms.toll.model.SetSignModel;
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
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(PicEvUtils.getBaseFeeUrl((String) SPUtils.get(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE,"")),TollServiceApi.class);
//        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi("https://fee.einyun.com/",TollServiceApi.class);
}

    public TollRepository(String s) {

        serviceApi2 = EinyunHttpService.Companion.getInstance().getServiceApi(TollServiceApi.class);
    }
    /**
     * get
     * ??????????????????????????????
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
     * ????????????
     */
    public void allWorth(FeeRequset requset, CallBack<WorthModel> callBack) {
        serviceApi.allWorth(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception());
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * ??????????????????????????????
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
     * ??????????????????
     */
    public void getSign(FeeDetailRequset requset, CallBack<GetSignModel> callBack) {
        serviceApi.getSign(requset).compose(RxSchedulers.inIoMain())
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
     * ?????????
     */
    public void setSign(FeeDetailRequset requset, CallBack<SetSignModel> callBack) {
        serviceApi.setSign(requset).compose(RxSchedulers.inIoMain())
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
     * ??????????????????
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
     * ????????????
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
//     * s?????????????????????
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
     * ????????????
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
     * ????????????
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
     * ??????????????????
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
     * ?????????????????????
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
     * ??????name
     */
    public void getNameFromPhone(GetNameRequset requset, CallBack<GetNameModel> callBack) {
        serviceApi.getNameFromPhone(requset).compose(RxSchedulers.inIoMain())
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
     * ????????????
     */
    public void AddHouser(AddHouserRequset requset, CallBack<AddHouserModel> callBack) {
        serviceApi.AddHouser(requset).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
//                    if(response.isState()){
                        callBack.call(response.getData());
//                    }else{
////                        callBack.onFaild(new Exception(response));
//                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * ????????????????????????
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
     * ??????feedid
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
     * ??????????????????id
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
     * ??????????????????iName
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
     * ????????????????????????
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
    /**
     * get
     * ??????dickey
     */
    public void getDicKey(String did, CallBack<DicRelationModel> callBack) {
        String url= URLS.URL_GET_DICKEY+did;
        serviceApi.getDicKey(url).compose(RxSchedulers.inIoMain())
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
     * ??????logo
     */
    public void getLogo(String did, CallBack<GetLogoModel> callBack) {
        String url= URLS.URL_GET_TENANT_LOGO+did;
        serviceApi2.getLogo(url).compose(RxSchedulers.inIoMain())
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
