package com.einyun.app.pms.main.core.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.main.core.model.HasReadModel;
import com.einyun.app.pms.main.core.model.ScanListModel;
import com.einyun.app.pms.main.core.model.ScanPatrolModel;
import com.einyun.app.pms.main.core.model.ScanRequest;
import com.einyun.app.pms.main.core.model.ScanResModel;
import com.einyun.app.pms.main.core.model.UCUserDetailsBean;
import com.einyun.app.pms.main.core.model.UserStarsBean;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_ORDER_LIST;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SCAN_HISTORY;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SCAN_WAIT_DEAL;
import static com.einyun.app.pms.main.core.repository.URLS.URL_GET_PATROL_HISTORY;
import static com.einyun.app.pms.main.core.repository.URLS.URL_GET_PATROL_WAIT;
import static com.einyun.app.pms.main.core.repository.URLS.URL_GET_RES_HISTORY;
import static com.einyun.app.pms.main.core.repository.URLS.URL_GET_RES_WAIT;


public class MainRepository {
    MainServiceApi serviceApi;

    public MainRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(MainServiceApi.class);
    }


    /**
     * 获取评分等级
     */
    public void queryStars(UserStarsBean bean, CallBack<UCUserDetailsBean> callBack) {
//        MutableLiveData<UCUserDetailsBean> liveData = new MutableLiveData<>();
        serviceApi.getStars(bean).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if (response.isState()) {
                        Log.e("response", "queryStars: " + response);
                        callBack.call(response.getData());
//                    liveData.postValue(response.getValue());
                    } else {
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }

    /**
     * 获取是否已读
     */
    public void hasRead(CallBack<HasReadModel> callBack) {
//        MutableLiveData<UCUserDetailsBean> liveData = new MutableLiveData<>();
        serviceApi.hasRead().compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if (response.isState()) {
                        Log.e("response", "queryStars: " + response);
                        callBack.call(response.getData());
//                    liveData.postValue(response.getValue());
                    } else {
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * 获取资源基本信息
     */
    public void getRes(String url,CallBack<ScanResModel> callBack) {
//        MutableLiveData<UCUserDetailsBean> liveData = new MutableLiveData<>();
        serviceApi.getRes(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if (response.isState()) {
                        Log.e("response", "queryStars: " + response);
                        callBack.call(response.getData());
//                    liveData.postValue(response.getValue());
                    } else {
                        callBack.onFaild(new Exception(response.getCode()+""));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * 获取巡更点基本信息
     */
    public void getPatrol(String url,CallBack<ScanPatrolModel> callBack) {
//        MutableLiveData<UCUserDetailsBean> liveData = new MutableLiveData<>();
        serviceApi.getPatrol(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if (response.isState()) {
                        Log.e("response", "queryStars: " + response);
                        callBack.call(response.getData());
//                    liveData.postValue(response.getValue());
                    } else {
                        callBack.onFaild(new Exception(response.getCode()+""));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }

    public void pageQuery(ScanRequest request, String tag, String type, CallBack<ScanListModel> callback) {
        String url = "";
        switch (tag) {
            case FRAGMENT_SCAN_WAIT_DEAL:
                switch (type) {
                    case "30"://30开头 资源类

                        url=URL_GET_RES_WAIT;
                        break;
                    case "31"://31 开头 巡更点
                        url=URL_GET_PATROL_WAIT;
                        break;
                }
                break;
            case FRAGMENT_SCAN_HISTORY:
                switch (type) {
                    case "30"://30开头 资源类
                        url=URL_GET_RES_HISTORY;
                        break;
                    case "31"://31 开头 巡更点
                        url=URL_GET_PATROL_HISTORY;
                        break;
                }
                break;
        }
        serviceApi.getDisqualifiedList(url, request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if (response.isState()) {
                        callback.call(response.getData());

                    }
                }, error -> {
                    callback.onFaild(error);
                    error.printStackTrace();
                });
    }
}
