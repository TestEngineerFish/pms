package com.einyun.app.pms.customerinquiries.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.customerinquiries.model.FeedBackModule;
import com.einyun.app.pms.customerinquiries.model.FeedBackRequest;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;

public class FeedBackViewModel extends BaseViewModel {
    CustomerInquiriesRepository repository= new CustomerInquiriesRepository();
    private MutableLiveData<FeedBackModule> feedBackModule=new MutableLiveData<>();
    public LiveData<FeedBackModule> queryFeedbackInfo(String id){
        showLoading();
        repository.getFeedbackInfo(id, new CallBack<FeedBackModule>() {
            @Override
            public void call(FeedBackModule data) {
                hideLoading();
                feedBackModule.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return feedBackModule;
    }
    /*
     * 反馈提交
     * */
    private MutableLiveData<Boolean> feedbacksubmit=new MutableLiveData<>();
    public LiveData<Boolean> feedBack(FeedBackRequest feedBackRequest){
        showLoading();
        repository.feedback(feedBackRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                feedbacksubmit.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return feedbacksubmit;
    }
}
