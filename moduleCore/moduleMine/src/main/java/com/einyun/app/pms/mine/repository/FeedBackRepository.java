package com.einyun.app.pms.mine.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.library.core.net.EinyunHttpService;

import com.einyun.app.pms.mine.constants.URLS;
import com.einyun.app.pms.mine.model.FeedBackBean;
import com.einyun.app.pms.mine.model.GetUserByccountBean;
import com.einyun.app.pms.mine.model.IsGrabModel;
import com.einyun.app.pms.mine.model.MsgListModel;
import com.einyun.app.pms.mine.model.RequestPageBean;
import com.einyun.app.pms.mine.model.SignSetModule;
import com.einyun.app.pms.mine.model.UCUserDetailsBean;
import com.einyun.app.pms.mine.model.UserStarsBean;
import com.einyun.app.pms.mine.net.response.FeedBackServiceApi;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_ORDER_LIST;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;

public class FeedBackRepository {
    FeedBackServiceApi serviceApi;

    public FeedBackRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(FeedBackServiceApi.class);
    }

    public void pageQuery(RequestPageBean page, String tag, CallBack<MsgListModel> callback) {
        String url = URLS.URL_GET_MSG_LIST;

        serviceApi.getMsgList(url,page).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callback.call(response.getData());

                    }
                }, error -> {
                    callback.onFaild(error);
                    error.printStackTrace();
                });
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
    /**
     * 单个已读
     * @param id
     * @param callBack
     * @return
     */
    public LiveData<Boolean> singleRead(String id, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        String url=URLS.URL_GET_SINGLE_READ+id;
        serviceApi.singleRead(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    /**
     * 是否抢单
     * @param id
     * @param callBack
     * @return
     */
    public LiveData<BaseResponse> isGrap(String id, CallBack<BaseResponse> callBack) {
        MutableLiveData<BaseResponse> liveData2 = new MutableLiveData<>();
        String url=URLS.URL_GET_IS_GRAP+id+"&reqParams=";
        serviceApi.isGrap(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
//                    liveData2.postValue(response);
                    callBack.call(response);
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData2;
    }
    /**
     * 全部已读
     * @param
     * @param callBack
     * @return
     */
    public LiveData<Boolean> allRead(String startTime,String endTime, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        String url=URLS.URL_GET_ALL_READ;
        serviceApi.allRead(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
}
