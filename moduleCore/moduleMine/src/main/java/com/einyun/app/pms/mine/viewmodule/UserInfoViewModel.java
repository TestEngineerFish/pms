package com.einyun.app.pms.mine.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.mine.model.GetUserByccountBean;
import com.einyun.app.pms.mine.model.UCUserDetailsBean;
import com.einyun.app.pms.mine.model.UserStarsBean;
import com.einyun.app.pms.mine.repository.FeedBackRepository;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class UserInfoViewModel extends BaseViewModel {

    FeedBackRepository repository=new FeedBackRepository();

    private MutableLiveData<GetUserByccountBean> detial=new MutableLiveData<>();
    public LiveData<GetUserByccountBean> getUserByccountBeanLiveData(String id){
        showLoading();
        repository.queryUserInfo(id, new CallBack<GetUserByccountBean>() {
            @Override
            public void call(GetUserByccountBean data) {
                hideLoading();
                detial.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detial;
    }

    private MutableLiveData<String> detialSign=new MutableLiveData<>();
    public LiveData<String> getSignText(String id){
        showLoading();
        repository.querySignText(id, new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                detialSign.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialSign;
    }

    private MutableLiveData<UCUserDetailsBean> detialStars=new MutableLiveData<>();
    public LiveData<UCUserDetailsBean> getStars(UserStarsBean bean){
        showLoading();
        repository.queryStars(bean, new CallBack<UCUserDetailsBean>() {
            @Override
            public void call(UCUserDetailsBean data) {
                hideLoading();
                detialStars.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialStars;
    }
    @NotNull
    public UserStarsBean getJsonObject( String userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("devideId", "");
        return new Gson().fromJson(jsonObject.toString(), UserStarsBean.class);
    }
}
