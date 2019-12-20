package com.einyun.app.pms.customerinquiries.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.customerinquiries.constants.URLS;
import com.einyun.app.pms.customerinquiries.module.DealRequest;
import com.einyun.app.pms.customerinquiries.module.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.module.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.module.FeedBackModule;
import com.einyun.app.pms.customerinquiries.module.FeedBackRequest;
import com.einyun.app.pms.customerinquiries.module.InquiriesDetailModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesListModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.module.InquiriesTypesBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


public class CustomerInquiriesRepository {
    CustomerInquiriesServiceApi serviceApi;

    public CustomerInquiriesRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(CustomerInquiriesServiceApi.class);
    }
    public void pageQuery(InquiriesRequestBean page, String tag, CallBack<InquiriesListModule> callback) {
//        QueryBuilder queryBuilder=new QueryBuilder();
//        Query build = queryBuilder.addQueryItem("","").build();
//        PageQueryRequest request = new PageQueryRequest();
//        request.setPageBean(page);
        String url="";
        switch (tag) {
            case FRAGMENT_TO_FOLLOW_UP://待跟进
                url = URLS.URL_GET_TO_FOLLOW_UP_LIST;
                break;
            case FRAGMENT_TO_FEED_BACK://待反馈
                url = URLS.URL_GET_TO_FEED_BACK_LIST;
                break;
            case FRAGMENT_HAVE_TO_FOLLOW_UP://已跟进
                url = URLS.URL_GET_HAVE_TO_FOLLOW_UP_LIST;
                break;
            case FRAGMENT_TRANSFERRED_TO://已办结
                url = URLS.URL_GET_TRANSFERRED_TO_LIST;
                break;
            case FRAGMENT_COPY_ME://抄送我
                url = URLS.URL_GET_COPY_ME_LIST;
                break;
        }

            serviceApi.getInquiriesList(url,page).compose(RxSchedulers.inIoMain())
                    .subscribe(response -> {
//                    if(response.isState()){
//                        if (mPage1==page.getPageBean().getPage()) {
//
//                        }else {
//                            mPage1=page.getPageBean().getPage();
                            callback.call(response.getData());
                            Logger.d("ItemDataSourcce.0pageBean .."+page.getPageBean().getPage());
//                        }

//                    }
                    }, error -> {
                        callback.onFaild(error);
                        error.printStackTrace();
                    });


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
    public LiveData<Boolean> dealSave(DealSaveRequest request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.dealSave(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }

    /**
     * 评价
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> Evaluation(EvaluationRequest request, CallBack<Boolean> callBack) {
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
}
