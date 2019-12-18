package com.einyun.app.pms.customerinquiries.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.pms.customerinquiries.module.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;

public class CustomerInquiriesViewModel extends BaseViewModel {
    public String currentFragmentTag=FRAGMENT_TO_FOLLOW_UP;
    CustomerInquiriesRepository repository= new CustomerInquiriesRepository();
    private MutableLiveData<List<InquiriesTypesBean>> detialType=new MutableLiveData<>();
    public LiveData<List<InquiriesTypesBean>> queryAduitType(){
        showLoading();
        repository.queryType( new CallBack<List<InquiriesTypesBean>>() {
            @Override
            public void call(List<InquiriesTypesBean> data) {
                hideLoading();
                detialType.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialType;
    }
}
