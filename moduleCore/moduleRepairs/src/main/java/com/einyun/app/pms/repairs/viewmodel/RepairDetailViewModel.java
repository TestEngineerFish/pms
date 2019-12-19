package com.einyun.app.pms.repairs.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.net.request.RepairSendOrderRequest;

public class RepairDetailViewModel extends BaseUploadViewModel {

    private WorkOrderService workOrderService;
    public RepairDetailViewModel() {
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);

    }


    /**
     *报修详情
     * */
    public LiveData<RepairsDetailModel> getRepairDetail(String instId){
        MutableLiveData<RepairsDetailModel> liveData = new MutableLiveData<>();
        showLoading();
        workOrderService.getRepairDetail(instId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }

    /**
     * 派单
     * */
    public LiveData<Boolean> repairSend(RepairSendOrderRequest request){
        MutableLiveData<Boolean> liveData = new MutableLiveData<Boolean>();
        showLoading();
        workOrderService.repaireSend(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }
}
