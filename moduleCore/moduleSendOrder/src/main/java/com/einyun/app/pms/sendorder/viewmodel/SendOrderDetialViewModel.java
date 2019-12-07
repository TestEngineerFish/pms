package com.einyun.app.pms.sendorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;

public class SendOrderDetialViewModel extends BaseUploadViewModel {
    ResourceWorkOrderService service;
    MutableLiveData<DisttributeDetialModel> workOrderLiveData=new MutableLiveData<>();
    public SendOrderDetialViewModel(){
        service= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    /**
     * 获取详情
     * @param taskId
     * @return
     */
    public LiveData<DisttributeDetialModel> detial(String taskId){
        showLoading();
        service.distributeWaitDetial(taskId, new CallBack<DisttributeDetialModel>() {
            @Override
            public void call(DisttributeDetialModel data) {
                workOrderLiveData.postValue(data);
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return workOrderLiveData;
    }
}
