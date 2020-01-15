package com.einyun.app.pms.customerinquiries.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.customerinquiries.constants.URLS;
import com.einyun.app.pms.customerinquiries.model.DealRequest;
import com.einyun.app.pms.customerinquiries.model.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.model.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.model.FeedBackModule;
import com.einyun.app.pms.customerinquiries.model.FeedBackRequest;
import com.einyun.app.pms.customerinquiries.model.InquiriesDetailModule;
import com.einyun.app.pms.customerinquiries.model.InquiriesListModule;
import com.einyun.app.pms.customerinquiries.model.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.model.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.model.OrderDetailInfoModule;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_INQUIRIES_ORDER_LIST;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


public class CustomerInquiriesRepository {
    CustomerInquiriesServiceApi serviceApi;
    public static int mPage1;
    public static int mPage2;
    public static int mPage3;
    public static int mPage4;
    public static int mPage5;
    public CustomerInquiriesRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(CustomerInquiriesServiceApi.class);
    }
    public void pageQuery(InquiriesRequestBean page, String tag, CallBack<InquiriesListModule> callback) {
        String url="";
        switch (tag) {
            case FRAGMENT_TO_FOLLOW_UP://待跟进
                url = URLS.URL_GET_TO_FOLLOW_UP_LIST;
                serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                        .subscribe(response -> {
                    if(response.isState()){
                        if (mPage1==page.getPageBean().getPage()) {
                        }else {
                            mPage1=page.getPageBean().getPage();
                            callback.call(response.getData());
                        }
                    }
                        }, error -> {
                            callback.onFaild(error);
                            error.printStackTrace();
                        });
                break;
            case FRAGMENT_TO_FEED_BACK://待反馈
                url = URLS.URL_GET_TO_FEED_BACK_LIST;
                serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                        .subscribe(response -> {
                            if(response.isState()){
                                if (mPage2==page.getPageBean().getPage()) {
                                }else {
                                    mPage2=page.getPageBean().getPage();
                                    callback.call(response.getData());
                                }
                            }
                        }, error -> {
                            callback.onFaild(error);
                            error.printStackTrace();
                        });
                break;
            case FRAGMENT_HAVE_TO_FOLLOW_UP://已跟进
                url = URLS.URL_GET_HAVE_TO_FOLLOW_UP_LIST;
                serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                        .subscribe(response -> {
                            if(response.isState()){
                                if (mPage3==page.getPageBean().getPage()) {
                                }else {
                                    mPage3=page.getPageBean().getPage();
                                    callback.call(response.getData());
                                }
                            }
                        }, error -> {
                            callback.onFaild(error);
                            error.printStackTrace();
                        });
                break;
            case FRAGMENT_TRANSFERRED_TO://已办结
                url = URLS.URL_GET_TRANSFERRED_TO_LIST;
                serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                        .subscribe(response -> {
                            if(response.isState()){
                                if (mPage4==page.getPageBean().getPage()) {
                                }else {
                                    mPage4=page.getPageBean().getPage();
                                    callback.call(response.getData());
                                }
                            }
                        }, error -> {
                            callback.onFaild(error);
                            error.printStackTrace();
                        });
                break;
            case FRAGMENT_COPY_ME://抄送我
                url = URLS.URL_GET_COPY_ME_LIST;
                serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                        .subscribe(response -> {
                            if(response.isState()){
                                if (mPage5==page.getPageBean().getPage()) {
                                }else {
                                    mPage5=page.getPageBean().getPage();
                                    callback.call(response.getData());
                                }
                            }
                        }, error -> {
                            callback.onFaild(error);
                            error.printStackTrace();
                        });
                break;
            case FRAGMENT_INQUIRIES_ORDER_LIST://问询工单列表
                url = URLS.URL_GET_ORDER_LIST;
                serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                        .subscribe(response -> {
                            if(response.isState()){
//                                if (mPage5==page.getPageBean().getPage()) {
//                                }else {
//                                    mPage5=page.getPageBean().getPage();
                                    callback.call(response.getData());
//                                }
                            }
                        }, error -> {
                            callback.onFaild(error);
                            error.printStackTrace();
                        });
                break;
        }

//            serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
//                    .subscribe(response -> {
////                    if(response.isState()){
////                        if (mPage1==page.getPageBean().getPage()) {
////
////                        }else {
////                            mPage1=page.getPageBean().getPage();
//                            callback.call(response.getData());
//                            Logger.d("ItemDataSourcce.0pageBean .."+page.getPageBean().getPage());
////                        }
//
////                    }
//                    }, error -> {
//                        callback.onFaild(error);
//                        error.printStackTrace();
//                    });


    }
    public void queryType(CallBack<List<InquiriesTypesBean>> callBack) {
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
    /**
     * get
     * 获取问询详情基本信息
     */
    public void getInquiriesBasicInfo(String id, CallBack<InquiriesDetailModule> callBack) {
        String url = URLS.URL_GET_INQUIRIES_DETAIL_INFO+id;
        serviceApi.getInquiriesDetailInfo(url).compose(RxSchedulers.inIoMain())
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
    /**
     * 处理
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> dealSubmit(DealRequest request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.dealSubmit(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    /**
     * 处理保存
     * @param request
     * @param callBack
     * @return
     */
    public void dealSave(DealSaveRequest request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.dealSave(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
//                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
    }

    /**
     * 评价
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> evaluation(EvaluationRequest request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.Evaluation(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    /**
     * get
     * 获取问询详情基本信息
     */
    public void getFeedbackInfo(String id, CallBack<FeedBackModule> callBack) {
        String url = URLS.URL_GET_FEEDBACK_INFO+id;
        serviceApi.getFeedbackInfo(url).compose(RxSchedulers.inIoMain())
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
    /**
     * 处理
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> feedback(FeedBackRequest request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.feedbacksubmit(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    /**
     * get
     * 获取工单历史信息
     */
    public void getOrderInfo(String procInstId,String taskId, CallBack<OrderDetailInfoModule> callBack) {
        String url = URLS.URL_GET_ORDER_DETAIL_INFO+procInstId+"&taskId="+taskId;
        serviceApi.getOrderInfo(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                    Log.e(TAG, "getOrderInfo: "+error.getMessage());
                });
    }
    /**
     * 评价
     * @param
     * @param callBack
     * @return
     */
    public LiveData<Boolean> isCanApply(String url,CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.isCanApply(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    private static final String TAG = "CustomerInquiriesReposi";
}
