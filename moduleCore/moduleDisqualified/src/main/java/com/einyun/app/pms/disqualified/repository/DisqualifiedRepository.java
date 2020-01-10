package com.einyun.app.pms.disqualified.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;

import com.einyun.app.pms.disqualified.constants.URLS;
import com.einyun.app.pms.disqualified.model.DisqualifiedListModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;
import com.einyun.app.pms.disqualified.model.OrderCodeBean;
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
    public void queryType(String type,CallBack<List<DisqualifiedTypesBean>> callBack) {
        String url = URLS.URL_GET_LINE_STATE_LIST+type;
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
    public void queryOrderCode(CallBack<String> callBack) {
        String url = URLS.URL_GET_ORDER_CODE;
        serviceApi.getOrderCode(url).compose(RxSchedulers.inIoMain())
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
    public LiveData<Boolean> dealSubmit(CreateUnQualityRequest request, CallBack<Boolean> callBack) {
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
    private static final String TAG = "CustomerInquiriesReposi";
}
