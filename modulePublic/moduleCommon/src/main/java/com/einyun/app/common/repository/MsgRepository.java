package com.einyun.app.common.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.common.constants.URLS;
import com.einyun.app.library.core.net.EinyunHttpService;

public class MsgRepository {
    MsgServiceApi serviceApi;

    public MsgRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(MsgServiceApi.class);
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
