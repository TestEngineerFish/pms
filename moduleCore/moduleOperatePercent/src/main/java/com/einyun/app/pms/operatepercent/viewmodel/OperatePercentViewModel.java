package com.einyun.app.pms.operatepercent.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.library.core.api.DashBoardService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.dashboard.model.AllChargedModel;
import com.einyun.app.library.dashboard.net.request.AllChargedRequest;
import com.einyun.app.library.resource.workorder.model.OrgnizationModel;
import com.einyun.app.pms.operatepercent.ui.AllChargeActivity;

public class OperatePercentViewModel extends BaseViewModel {
    private DashBoardService dashBoardService;
    public MutableLiveData<AllChargedModel> allChargedModelLiveData=new MutableLiveData<>();
    public OperatePercentViewModel() {
        this.dashBoardService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DASHBOARD);
    }

    public OperatePercentViewModel(AllChargeActivity allChargeActivity, OperatePercentModelFactory operatePercentModelFactory) {
    }

    /**
     * 获取组织架构 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<AllChargedModel> getAllChargedProject(AllChargedRequest request) {
        showLoading();

        dashBoardService.allChargedProject(request, new CallBack<AllChargedModel>() {
            @Override
            public void call(AllChargedModel data) {
                hideLoading();
               allChargedModelLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return allChargedModelLiveData;
    }
}
