package com.einyun.app.pms.customerinquiries.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.customerinquiries.module.DealRequest;
import com.einyun.app.pms.customerinquiries.module.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.module.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.module.InquiriesDetailModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;

public class InquiriesDetailViewModel extends BaseViewModel {

    CustomerInquiriesRepository repository= new CustomerInquiriesRepository();

    private MutableLiveData<InquiriesDetailModule> InquiriesBasicInfo=new MutableLiveData<>();
    public LiveData<InquiriesDetailModule> queryInquiriesBasicInfo(String id){
        showLoading();
        repository.getInquiriesBasicInfo(id, new CallBack<InquiriesDetailModule>() {
            @Override
            public void call(InquiriesDetailModule data) {
                hideLoading();
                InquiriesBasicInfo.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return InquiriesBasicInfo;
    }
    /*
     * 处理接口
     * */
    private MutableLiveData<Boolean> deal=new MutableLiveData<>();
    public LiveData<Boolean> Deal(DealRequest dealRequest){
        showLoading();
        repository.dealSubmit(dealRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                deal.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return deal;
    }
    /*
     * 处理保存接口
     * */
    private MutableLiveData<Boolean> dealSave=new MutableLiveData<>();
    public LiveData<Boolean> DealSave(DealSaveRequest dealSaveRequest){
        showLoading();
        repository.dealSave(dealSaveRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                dealSave.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return dealSave;
    }
    /*
     * 评价
     * */
    private MutableLiveData<Boolean> evaluation=new MutableLiveData<>();
    public LiveData<Boolean> Evaluation(EvaluationRequest evaluationRequest){
        showLoading();
        repository.Evaluation(evaluationRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                evaluation.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return evaluation;
    }
}
