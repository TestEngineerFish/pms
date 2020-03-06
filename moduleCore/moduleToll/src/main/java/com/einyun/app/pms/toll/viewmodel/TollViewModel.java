package com.einyun.app.pms.toll.viewmodel;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.core.api.UserCenterService;
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
import com.einyun.app.pms.toll.respository.TollRepository;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.function.BinaryOperator;


public class TollViewModel extends BaseViewModel  {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    TollRepository repository= new TollRepository();
    TollRepository repository2= new TollRepository("");
    private MutableLiveData<WorthModel> allWorth=new MutableLiveData<>();
    public LiveData<WorthModel> allWorth(FeeRequset requset){
        showLoading();
        repository.allWorth(requset, new CallBack<WorthModel>() {
            @Override
            public void call(WorthModel data) {
                hideLoading();
                allWorth.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return allWorth;
    }
    private MutableLiveData<FeeModel> feedBackModule=new MutableLiveData<>();
    public LiveData<FeeModel> queryFeeInfo(FeeRequset requset){
        showLoading();
        repository.getFeeInfo(requset, new CallBack<FeeModel>() {
            @Override
            public void call(FeeModel data) {
                hideLoading();
                feedBackModule.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return feedBackModule;
    }
    private MutableLiveData<FeeModel> feedBackModule2=new MutableLiveData<>();
    public LiveData<FeeModel> queryFeeInfo2(FeeRequset requset){
        showLoading();
        repository.getFeeInfo(requset, new CallBack<FeeModel>() {
            @Override
            public void call(FeeModel data) {
                hideLoading();
                feedBackModule2.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return feedBackModule2;
    }
    private MutableLiveData<FeeModel> feedBackModule3=new MutableLiveData<>();
    public LiveData<FeeModel> queryFeeInfo3(FeeRequset requset){
        showLoading();
        repository.getFeeInfo(requset, new CallBack<FeeModel>() {
            @Override
            public void call(FeeModel data) {
                hideLoading();
                feedBackModule3.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return feedBackModule3;
    }
    /**
     * 欠费详情
     */
    private MutableLiveData<LackDetailModel> feeDetailModule=new MutableLiveData<>();
    public LiveData<LackDetailModel> queryFeeDetail(FeeDetailRequset requset){
        showLoading();
        repository.getFeeDetailInfo(requset, new CallBack<LackDetailModel>() {
            @Override
            public void call(LackDetailModel data) {
                hideLoading();
                feeDetailModule.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return feeDetailModule;
    }
/**
 * 预交费列表
 */
    private MutableLiveData<PaymentAdvanceModel2> paymentModule=new MutableLiveData<>();
    public LiveData<PaymentAdvanceModel2> getPaymentAdvanceList(FeeDetailRequset requset){
        showLoading();
        repository.getPaymentAdvanceList(requset, new CallBack<PaymentAdvanceModel2>() {
            @Override
            public void call(PaymentAdvanceModel2 data) {
                hideLoading();
                paymentModule.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return paymentModule;
    }
    /**
 * 跳缴验证
 */
    private MutableLiveData<JumpVerityModel> jumopModule=new MutableLiveData<>();
    public LiveData<JumpVerityModel> jumpVerify(JumpRequest requset){
        showLoading();
        repository.jumpVerify(requset, new CallBack<JumpVerityModel>() {
            @Override
            public void call(JumpVerityModel data) {
                hideLoading();
                jumopModule.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return jumopModule;
    }
    /**
     * 生成收款二维码
     */
    private MutableLiveData<Byte> createQrCodeModel=new MutableLiveData<>();
    public LiveData<Byte> createQrCode(int orderId){
        showLoading();
        repository.createQrCode(orderId, new CallBack<Byte>() {
            @Override
            public void call(Byte data) {
                hideLoading();
                createQrCodeModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return createQrCodeModel;
    }
//    /**
//     * 生成订单
//     */
//    private MutableLiveData<JumpVerityModel> createOrderModel=new MutableLiveData<>();
//    public LiveData<JumpVerityModel> createOrder(JumpRequest requset){
//        showLoading();
//        repository.jumpVerify(requset, new CallBack<JumpVerityModel>() {
//            @Override
//            public void call(JumpVerityModel data) {
//                hideLoading();
//                createOrderModel.postValue(data);
//            }
//
//            @Override
//            public void onFaild(Throwable throwable) {
//                hideLoading();
//            }
//        });
//        return createOrderModel;
//    }
    /**
     * 预缴费验证
     */
    private MutableLiveData<JsonObject> jumopAdvanceModule=new MutableLiveData<>();
    public LiveData<JsonObject> jumpAdvanceVerify(JumpAdvanceRequset requset){
        showLoading();
        repository.jumpAdvanceVerify(requset, new CallBack<JsonObject>() {
            @Override
            public void call(JsonObject data) {
                hideLoading();
                jumopAdvanceModule.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return jumopAdvanceModule;
    }
    /**
 * 生成订单
 */
    private MutableLiveData<CreateOrderModel> createOrderModule=new MutableLiveData<>();
    public LiveData<CreateOrderModel> createOrder(CreateOrderRequest requset){
        showLoading();
        repository.createOrder(requset, new CallBack<CreateOrderModel>() {
            @Override
            public void call(CreateOrderModel data) {
                hideLoading();
                createOrderModule.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return createOrderModule;
    }
    /**
     * 查询订单状态
     */
    private MutableLiveData<QueryStateModel> queryOrderStateModel=new MutableLiveData<>();
    public LiveData<QueryStateModel> queryOrderState(int requset){
//        showLoading();
        repository.queryOrderState(requset, new CallBack<QueryStateModel>() {
            @Override
            public void call(QueryStateModel data) {
                hideLoading();
                queryOrderStateModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return queryOrderStateModel;
    }
    /**
     * 查询已付费明细
     */
    private MutableLiveData<FeeSucInfoModel> queryFeedInfDetailsModel=new MutableLiveData<>();
    public LiveData<FeeSucInfoModel> queryFeedInfDetails(QueryFeedDetailsInfoRequest requset){
        showLoading();
        repository.queryFeedInfDetails(requset, new CallBack<FeeSucInfoModel>() {
            @Override
            public void call(FeeSucInfoModel data) {
                hideLoading();
                queryFeedInfDetailsModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return queryFeedInfDetailsModel;
    }
    /**
     * 欠费详单
     */
    private MutableLiveData<TollModel> lackDetailListModel=new MutableLiveData<>();
    public LiveData<TollModel> getLackDetailList(FeeDetailRequset requset){
        showLoading();
        repository.getLackDetailList(requset, new CallBack<TollModel>() {
            @Override
            public void call(TollModel data) {
                hideLoading();
                lackDetailListModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return lackDetailListModel;
    }
    /**
     * 获取feeDivideId
     */
    private MutableLiveData<String> getFeeDivideId=new MutableLiveData<>();
    public LiveData<String> getFeeDivideId(String did,String type){
//        showLoading();
        repository.getFeeDivideId(did,type, new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                getFeeDivideId.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getFeeDivideId;
    }
    /**
     * 获取defauftDivideId
     */
    private MutableLiveData<String> getDefauftDivideId=new MutableLiveData<>();
    public LiveData<String> getDefauftDivideId(String userId){
//        showLoading();
        repository2.getDefauftDivideId(userId ,new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                getDefauftDivideId.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getDefauftDivideId;
    }
    /**
     * 获取defauftName
     */
    private MutableLiveData<List<DivideNameModel>> getDefauftName=new MutableLiveData<>();
    public LiveData<List<DivideNameModel>> getDefauftDivideName(String userId){
//        showLoading();
        repository2.getDefauftDivideName(userId ,new CallBack<List<DivideNameModel>>() {
            @Override
            public void call(List<DivideNameModel> data) {
                hideLoading();
                getDefauftName.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getDefauftName;
    }
    /**
     * 获取feeHouseId
     */
    private MutableLiveData<String> getFeeHouseId=new MutableLiveData<>();
    public LiveData<String> getFeeHouseId(String did,String type){
//        showLoading();
        repository.getFeeDivideId(did,type, new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                getFeeHouseId.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getFeeHouseId;
    }
    /**
     * 获取上次催缴time
     */
    private MutableLiveData<WorthTimeModel> getLastWorthTime=new MutableLiveData<>();
    public LiveData<WorthTimeModel> getLastWorthTime(String did){
//        showLoading();
        repository.getLastWorthTime( did,new CallBack<WorthTimeModel>() {
            @Override
            public void call(WorthTimeModel data) {
                hideLoading();
                getLastWorthTime.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getLastWorthTime;
    }
    /**
     *判断字符串是否全为英文
     * @param str
     * @return
     **/
    public boolean isEnglish(String str){
        //【全为英文】返回true  否则false
        // boolean result1 = str.matches("[a-zA-Z]+");
        // 【全为数字】返回true
        // Boolean result6 = str.matches("[0-9]+");
        // 【除英文和数字外无其他字符(只有英文数字的字符串)】返回true 否则false
        // boolean result2 = str.matches("[a-zA-Z0-9]+");
        // 【含有英文】true
         String regex1 = ".*[a-zA-z].*";
         boolean result3 = str.matches(regex1);
         return result3;
        // 【含有数字】true
        // String regex2 = ".*[0-9].*";
        // boolean result4 = str.matches(regex2);
        // 判断是否为纯中文，不是返回false
        // String regex3 = "[\\u4e00-\\u9fa5]+";
        // boolean result5 = str.matches(regex3);
        // System.out.println(result1+"--"+result2+"--"+result3				+"--"+result4+"--"+result5+"--"+result6);
        }
    /**
     * 获取用户Id
     *
     * @return
     */
    public String getUserId() {
        return userModuleService.getUserId();
    }
}
