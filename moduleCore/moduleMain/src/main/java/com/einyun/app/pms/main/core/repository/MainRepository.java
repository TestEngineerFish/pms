package com.einyun.app.pms.main.core.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.main.core.model.HasReadModel;
import com.einyun.app.pms.main.core.model.UCUserDetailsBean;
import com.einyun.app.pms.main.core.model.UserStarsBean;


public class MainRepository {
    MainServiceApi serviceApi;

    public MainRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(MainServiceApi.class);
    }



    /**
     * 获取评分等级
     * */
    public void queryStars(UserStarsBean bean, CallBack<UCUserDetailsBean> callBack) {
//        MutableLiveData<UCUserDetailsBean> liveData = new MutableLiveData<>();
        serviceApi.getStars(bean).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        Log.e("response", "queryStars: "+response);
                    callBack.call(response.getData());
//                    liveData.postValue(response.getValue());
                    }else{
                    callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * 获取是否已读
     * */
    public void hasRead( CallBack<HasReadModel> callBack) {
//        MutableLiveData<UCUserDetailsBean> liveData = new MutableLiveData<>();
        serviceApi.hasRead().compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        Log.e("response", "queryStars: "+response);
                        callBack.call(response.getData());
//                    liveData.postValue(response.getValue());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }

}
