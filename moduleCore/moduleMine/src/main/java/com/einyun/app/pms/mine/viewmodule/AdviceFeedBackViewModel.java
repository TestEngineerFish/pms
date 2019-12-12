package com.einyun.app.pms.mine.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.mine.module.FeedBackBean;
import com.einyun.app.pms.mine.repository.FeedBackRepository;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class AdviceFeedBackViewModel extends BaseViewModel {
    FeedBackRepository repository=new FeedBackRepository();


    private MutableLiveData<Boolean> approval=new MutableLiveData<>();
    public LiveData<Boolean> sumitApproval(FeedBackBean feedBackBean){
        showLoading();
        repository.feedBackSumit(feedBackBean, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                approval.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return approval;
    }
    @NotNull
    public FeedBackBean getJsonObject(String content,String account,String name,String mobile,String userId,int position) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content" , content);
        jsonObject.put("feedbackAccount" ,account );
        jsonObject.put("feedbackName" ,name );
        jsonObject.put("feedbackPhone" ,mobile );
        jsonObject.put("feedbackId" , userId);
        jsonObject.put("issueType" ,position);
        return new Gson().fromJson(jsonObject.toString(),FeedBackBean.class);
    }
}
