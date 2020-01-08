package com.einyun.app.pms.disqualified.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;

import com.einyun.app.pms.disqualified.constants.URLS;
import com.einyun.app.pms.disqualified.model.DisqualifiedListModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;
import com.einyun.app.pms.disqualified.net.request.DisqualifiedListRequest;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


public class DisqualifiedRepository {
    DisqualifiedServiceApi serviceApi;

    public DisqualifiedRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(DisqualifiedServiceApi.class);
    }
    public void pageQuery(DisqualifiedListRequest page, String tag, CallBack<DisqualifiedListModel> callback) {
        String url="";
        switch (tag) {
            case FRAGMENT_TO_FOLLOW_UP://待跟进
                url = URLS.URL_GET_TO_FOLLOW_UP_LIST;
                break;
            case FRAGMENT_TO_FEED_BACK://待反馈
                url = URLS.URL_GET_TO_FEED_BACK_LIST;
                break;
        }
        serviceApi.getDisqualifiedList(url,page).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callback.call(response.getData());

                    }
                }, error -> {
                    callback.onFaild(error);
                    error.printStackTrace();
                });
    }
    public void queryType(CallBack<List<DisqualifiedTypesBean>> callBack) {
        String url = URLS.URL_GET_QUIRIES_TYPES;
        serviceApi.getTypes(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
//    /**
//     * get
//     * 获取问询详情基本信息
//     */
//    public void getInquiriesBasicInfo(String id, CallBack<InquiriesDetailModule> callBack) {
//        String url = URLS.URL_GET_INQUIRIES_DETAIL_INFO+id;
//        serviceApi.getInquiriesDetailInfo(url).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
//                    if(response.isState()){
//                        callBack.call(response.getData());
//                    }else{
//                        callBack.onFaild(new Exception(response.getCode()));
//                    }
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//    }
//    /**
//     * 处理
//     * @param request
//     * @param callBack
//     * @return
//     */
//    public LiveData<Boolean> dealSubmit(DealRequest request, CallBack<Boolean> callBack) {
//        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
//        serviceApi.dealSubmit(request).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
//                    liveData.postValue(response.isState());
//                    callBack.call(response.isState());
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//        return liveData;
//    }
//    /**
//     * 处理保存
//     * @param request
//     * @param callBack
//     * @return
//     */
//    public void dealSave(DealSaveRequest request, CallBack<Boolean> callBack) {
//        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
//        serviceApi.dealSave(request).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
////                    liveData.postValue(response.isState());
//                    callBack.call(response.isState());
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//    }
//
//    /**
//     * 评价
//     * @param request
//     * @param callBack
//     * @return
//     */
//    public LiveData<Boolean> evaluation(EvaluationRequest request, CallBack<Boolean> callBack) {
//        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
//        serviceApi.Evaluation(request).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
//                    liveData.postValue(response.isState());
//                    callBack.call(response.isState());
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//        return liveData;
//    }
//    /**
//     * get
//     * 获取问询详情基本信息
//     */
//    public void getFeedbackInfo(String id, CallBack<FeedBackModule> callBack) {
//        String url = URLS.URL_GET_FEEDBACK_INFO+id;
//        serviceApi.getFeedbackInfo(url).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
//                    if(response.isState()){
//                        callBack.call(response.getData());
//                    }else{
//                        callBack.onFaild(new Exception(response.getCode()));
//                    }
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//    }
//    /**
//     * 处理
//     * @param request
//     * @param callBack
//     * @return
//     */
//    public LiveData<Boolean> feedback(FeedBackRequest request, CallBack<Boolean> callBack) {
//        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
//        serviceApi.feedbacksubmit(request).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
//                    liveData.postValue(response.isState());
//                    callBack.call(response.isState());
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//        return liveData;
//    }
//    /**
//     * get
//     * 获取工单历史信息
//     */
//    public void getOrderInfo(String procInstId,String taskId, CallBack<OrderDetailInfoModule> callBack) {
//        String url = URLS.URL_GET_ORDER_DETAIL_INFO+procInstId+"&taskId="+taskId;
//        serviceApi.getOrderInfo(url).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
//                    if(response.isState()){
//                        callBack.call(response.getData());
//                    }else{
//                        callBack.onFaild(new Exception(response.getCode()));
//                    }
//                }, error -> {
//                    callBack.onFaild(error);
//                    Log.e(TAG, "getOrderInfo: "+error.getMessage());
//                });
//    }
//    /**
//     * 评价
//     * @param
//     * @param callBack
//     * @return
//     */
//    public LiveData<Boolean> isCanApply(String url,CallBack<Boolean> callBack) {
//        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
//        serviceApi.isCanApply(url).compose(RxSchedulers.inIoMain())
//                .subscribe(response -> {
//                    liveData.postValue(response.isState());
//                    callBack.call(response.isState());
//                }, error -> {
//                    callBack.onFaild(error);
//                });
//        return liveData;
//    }
    private static final String TAG = "CustomerInquiriesReposi";
}
