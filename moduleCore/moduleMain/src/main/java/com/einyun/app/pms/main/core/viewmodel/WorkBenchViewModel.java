package com.einyun.app.pms.main.core.viewmodel;

import androidx.lifecycle.LiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.core.api.DashBoardService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.dashboard.model.OperateCaptureData;
import com.einyun.app.library.dashboard.model.UserMenuData;
import com.einyun.app.library.dashboard.model.WorkOrderData;
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest;
import com.einyun.app.library.dashboard.net.response.WorkOrderResponse;
import com.einyun.app.library.resource.workorder.model.WaitCount;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.BlocklogNums;
import com.einyun.app.pms.main.core.viewmodel.contract.WorkBenchViewModelContract;

import java.util.Calendar;
import java.util.List;

public class WorkBenchViewModel extends BaseViewModel implements WorkBenchViewModelContract {
    // TODO: Implement the ViewModel
    private static final String TAG = WorkBenchViewModel.class.getSimpleName();
    DashBoardService mService;
    WorkOrderService workOrderService;
    UserCenterService userCenterService;
    ResourceWorkOrderService resourceWorkOrderService;

    public WorkBenchViewModel() {
//        mUsersRepo = new UserRepository();
        mService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DASHBOARD);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
        userCenterService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
        resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    @Override
    public LiveData<WorkOrderData> workOrderData(List<String> orgIds) {
        WorkOrderRequest request = new WorkOrderRequest();
        request.setOrgIds(orgIds);
        Calendar c = Calendar.getInstance();
        request.setYear(String.valueOf(c.get(Calendar.YEAR)));
        request.setMonth(String.valueOf(c.get(Calendar.MONTH) + 1));
        return mService.workOrderData(request, new CallBack<WorkOrderData>() {
            @Override
            public void call(WorkOrderData data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<OperateCaptureData> operateCaptureData(List<String> orgCodes) {
        return mService.operateCaptureData(orgCodes, new CallBack<OperateCaptureData>() {
            @Override
            public void call(OperateCaptureData data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<String> userMenuData(int menuType) {
        return mService.userMenuData(menuType, new CallBack<String>() {
            @Override
            public void call(String data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<Integer> getAuditCount() {
        return workOrderService.getAuditCount(new CallBack<Integer>() {
            @Override
            public void call(Integer data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<List<OrgModel>> userCenterUserList(String userId) {
        return userCenterService.userCenterUserList(userId, new CallBack<List<OrgModel>>() {
            @Override
            public void call(List<OrgModel> data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    /**
     * 获取待办数量（客户报修，客户询问，客户投诉）
     * @return
     */
    @Override
    public LiveData<BlocklogNums> getBlocklogNums() {
        return workOrderService.getBlocklogNums(new CallBack<BlocklogNums>() {
            @Override
            public void call(BlocklogNums data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<WaitCount> getWaitCount() {
        return resourceWorkOrderService.getWaitCount(new CallBack<WaitCount>() {
            @Override
            public void call(WaitCount data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

}
