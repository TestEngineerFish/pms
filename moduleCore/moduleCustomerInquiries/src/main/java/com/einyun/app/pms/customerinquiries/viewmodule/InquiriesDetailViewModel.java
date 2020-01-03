package com.einyun.app.pms.customerinquiries.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.customerinquiries.constants.URLS;
import com.einyun.app.pms.customerinquiries.model.DealRequest;
import com.einyun.app.pms.customerinquiries.model.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.model.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.model.InquiriesDetailModule;
import com.einyun.app.pms.customerinquiries.model.OrderDetailInfoModule;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;

public class InquiriesDetailViewModel extends BaseViewModel {

    CustomerInquiriesRepository repository= new CustomerInquiriesRepository();

    private MutableLiveData<InquiriesDetailModule> InquiriesBasicInfo=new MutableLiveData<>();
    public LiveData<InquiriesDetailModule> queryInquiriesBasicInfo(String id){
//        showLoading();
        repository.getInquiriesBasicInfo(id, new CallBack<InquiriesDetailModule>() {
            @Override
            public void call(InquiriesDetailModule data) {
//                hideLoading();
                InquiriesBasicInfo.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                InquiriesBasicInfo.postValue(null);
//                hideLoading();
            }
        });
        return InquiriesBasicInfo;
    }
    /*
     * 处理接口
     * */
    private MutableLiveData<Boolean> deal=new MutableLiveData<>();
    public LiveData<Boolean> deal(DealRequest dealRequest){
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
     MutableLiveData<Boolean> dealSave=new MutableLiveData<>();
    public LiveData<Boolean> dealSave(DealSaveRequest dealSaveRequest){
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
    public LiveData<Boolean> evaluation(EvaluationRequest evaluationRequest){
        showLoading();
        repository.evaluation(evaluationRequest, new CallBack<Boolean>() {
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
    private MutableLiveData<OrderDetailInfoModule> queryOrderInfo=new MutableLiveData<>();
    public LiveData<OrderDetailInfoModule> queryOrderInfo(String procInstId,String taskId){
        showLoading();
        repository.getOrderInfo(procInstId,taskId, new CallBack<OrderDetailInfoModule>() {
            @Override
            public void call(OrderDetailInfoModule data) {
                hideLoading();
                queryOrderInfo.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return queryOrderInfo;
    }
    /*
     * 评价
     * */
    private MutableLiveData<Boolean> isCanApply=new MutableLiveData<>();
    public LiveData<Boolean> isCanApply(String  id,String type){
        showLoading();
        String url= URLS.URL_GET_IS_CAN_APPLY_CLOSE_ORDER+id+"&auditSubType="+type;
        repository.isCanApply(url, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                isCanApply.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return isCanApply;
    }
}
