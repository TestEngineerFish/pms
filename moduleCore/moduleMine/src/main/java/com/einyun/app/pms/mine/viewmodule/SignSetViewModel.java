package com.einyun.app.pms.mine.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.mine.model.SignSetModule;
import com.einyun.app.pms.mine.repository.FeedBackRepository;

public class SignSetViewModel extends BaseViewModel {
    FeedBackRepository repository=new FeedBackRepository();
    private MutableLiveData<Boolean> approval=new MutableLiveData<>();
    public LiveData<Boolean> sumitSignText(SignSetModule Bean){
        showLoading();
        repository.SignTextSumit(Bean, new CallBack<Boolean>() {
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
}
