package com.einyun.app.pms.mine.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;

import com.einyun.app.pms.mine.constants.URLS;
import com.einyun.app.pms.mine.module.FeedBackBean;
import com.einyun.app.pms.mine.module.GetUserByccountBean;
import com.einyun.app.pms.mine.module.SignSetModule;
import com.einyun.app.pms.mine.module.UCUserDetailsBean;
import com.einyun.app.pms.mine.module.UserStarsBean;
import com.einyun.app.pms.mine.response.FeedBackServiceApi;
import com.google.gson.Gson;

import java.util.List;

public class FeedBackRepository {
    FeedBackServiceApi serviceApi;

    public FeedBackRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(FeedBackServiceApi.class);
    }



    /**
     * 意见提交
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> feedBackSumit(FeedBackBean request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.sumitFeedBack(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    /**
     * 获取用户信息
     * */
    public void queryUserInfo(String id, CallBack<GetUserByccountBean> callBack) {
        String url = URLS.URL_GET_USER_INFO_BY_ACCOUNT + id;
        serviceApi.getUserInfo(url).compose(RxSchedulers.inIoMain())
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
     * 获取个性签名
     * */
    public void querySignText(String id, CallBack<String> callBack) {
        String url = URLS.URL_GET_NEW_USER_SIGN_TEXT + id;
        serviceApi.getSignText(url).compose(RxSchedulers.inIoMain())
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
     * 上传照片
    * */
    public LiveData<Boolean> create(GetUserByccountBean request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.upload(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    /**
     * 个性签名
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> SignTextSumit(SignSetModule request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.setSignText(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
}
