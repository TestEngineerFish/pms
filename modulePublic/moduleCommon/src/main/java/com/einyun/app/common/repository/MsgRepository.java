package com.einyun.app.common.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.common.constants.URLS;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.core.net.EinyunHttpService;

public class MsgRepository {
    MsgServiceApi serviceApi;
    private final WorkOrderService workOrderService;
    private final ResourceWorkOrderService service;

    public MsgRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(MsgServiceApi.class);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    /**
     * 单个已读
     * @param id
     * @param callBack
     * @return
     */
    public LiveData<Boolean> singleRead(String id, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        String url= URLS.URL_GET_SINGLE_READ+id;
        serviceApi.singleRead(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
}
