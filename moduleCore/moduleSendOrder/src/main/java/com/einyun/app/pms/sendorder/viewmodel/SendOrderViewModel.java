package com.einyun.app.pms.sendorder.viewmodel;

import androidx.lifecycle.LiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.sendorder.model.SendOrderModel;

import java.util.List;

public class SendOrderViewModel extends BaseViewModel {
    private ResourceWorkOrderRepo resourceWorkOrderRepo;
    private ResourceWorkOrderService resourceWorkOrderService;
    private LiveData<List<DistributeWorkOrder>> workOrderListViewModel;

    public SendOrderViewModel() {
        this.resourceWorkOrderRepo = new ResourceWorkOrderRepo();
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    /**
     * 获取派工单代办列表
     * @return
     */
    public LiveData<List<DistributeWorkOrder>> distributeWaitPage(String divideId, String txId,String type,String fState,String checkId,String envType2,String envType3) {
        //网络数据交互，显示Loading
        showLoading();
        workOrderListViewModel = resourceWorkOrderService.distributeWaitPage(divideId, txId,type, fState,checkId,envType2,envType3, new CallBack<List<DistributeWorkOrder>>() {

            @Override
            public void call(List<DistributeWorkOrder> data) {
                //关闭Loading
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });//数据获取
        return workOrderListViewModel;
    }
}
