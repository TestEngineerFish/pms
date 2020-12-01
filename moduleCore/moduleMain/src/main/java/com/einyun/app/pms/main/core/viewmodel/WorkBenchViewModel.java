package com.einyun.app.pms.main.core.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.databinding.LayoutListPageStateBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.DashBoardService;
import com.einyun.app.library.core.api.MdmService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.dashboard.model.OperateCaptureData;
import com.einyun.app.library.dashboard.model.UserMenuData;
import com.einyun.app.library.dashboard.model.WorkOrderData;
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest;
import com.einyun.app.library.dashboard.net.response.WorkOrderResponse;
import com.einyun.app.library.mdm.model.NoticeModel;
import com.einyun.app.library.mdm.model.SystemNoticeModel;
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest;
import com.einyun.app.library.mdm.net.response.NoticeListPageResult;
import com.einyun.app.library.resource.workorder.model.WaitCount;
import com.einyun.app.library.uc.user.model.KaoQingHistroyModel;
import com.einyun.app.library.uc.user.model.KaoQingOrgModel;
import com.einyun.app.library.uc.user.model.Param;
import com.einyun.app.library.uc.user.model.UserInfoModel;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.uc.user.net.request.GetKaoQingHistoryRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.uc.usercenter.model.WorkStatusModel;
import com.einyun.app.library.workorder.model.BlocklogNums;
import com.einyun.app.pms.main.core.viewmodel.contract.WorkBenchViewModelContract;

import org.mockito.internal.matchers.Any;

import java.util.Calendar;
import java.util.List;

import static com.einyun.app.common.utils.UserUtil.getUserId;

public class WorkBenchViewModel extends BaseViewModel implements WorkBenchViewModelContract {
    // TODO: Implement the ViewModel
    private static final String TAG = WorkBenchViewModel.class.getSimpleName();
    DashBoardService mService;
    WorkOrderService workOrderService;
    UserCenterService userCenterService;
    UCService ucService;
    ResourceWorkOrderService resourceWorkOrderService;
    private MdmService mdmService;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    public WorkBenchViewModel() {
//        mUsersRepo = new UserRepository();
        mService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DASHBOARD);
        mdmService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_MDM);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
        userCenterService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
        ucService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_UC);
        resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    @Override
    public LiveData<WorkOrderData> workOrderData(String orgId, String userId) {
        WorkOrderRequest request = new WorkOrderRequest();
        request.setOrgId(orgId);
        Calendar c = Calendar.getInstance();
        request.setYear(String.valueOf(c.get(Calendar.YEAR)));
        request.setMonth(String.valueOf(c.get(Calendar.MONTH) + 1));
        request.setUserId(userId);
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

//    /**
//     * 获取Paging LiveData
//     *
//     * @return LiveData
//     */
//    public LiveData<List<NoticeModel>> getNotices(NoticeListPageRequest noticeListPageRequest, LayoutListPageStateBinding pageState) {
//        MutableLiveData<List<NoticeModel>> liveData = new MutableLiveData();
//        mdmService.getNoticeTop(noticeListPageRequest, new CallBack<NoticeListPageResult>() {
//            @Override
//            public void call(NoticeListPageResult data) {
//                liveData.postValue(data.getRows());
//
//            }
//
//            @Override
//            public void onFaild(Throwable throwable) {
//                liveData.postValue(null);
//            }
//        });
//        return liveData;
//    }

    /**
     * 获取Paging LiveData
     *
     * @return LiveData
     */
    public LiveData<List<NoticeModel>> getNotices(NoticeListPageRequest noticeListPageRequest, LayoutListPageStateBinding pageState) {
        MutableLiveData<List<NoticeModel>> liveData = new MutableLiveData();
        mdmService.getNoticeTop(noticeListPageRequest, new CallBack<NoticeListPageResult>() {
            @Override
            public void call(NoticeListPageResult data) {
                liveData.postValue(data.getRows());

            }

            @Override
            public void onFaild(Throwable throwable) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public LiveData<WorkOrderData> workOrderData(String orgId, String year, String month, String userId) {
        WorkOrderRequest request = new WorkOrderRequest();
        request.setOrgId(orgId);
        request.setYear(year);
        request.setMonth(month);
        request.setUserId(userId);
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
//                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<String> userMenuData(int menuType) {
        MutableLiveData<String> liveData = new MutableLiveData();
        mService.userMenuData(menuType, new CallBack<String>() {
            @Override
            public void call(String data) {
                if (data == null) {
                    liveData.postValue("");
                } else {
                    liveData.postValue(data);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
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
     *
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

    public LiveData<SystemNoticeModel> getSystemNotice() {
        MutableLiveData<SystemNoticeModel> liveData = new MutableLiveData();
        mdmService.getSystemNotice(new CallBack<SystemNoticeModel>() {
            @Override
            public void call(SystemNoticeModel data) {
                liveData.postValue(data);

            }

            @Override
            public void onFaild(Throwable throwable) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<SystemNoticeModel> getSystemNoticeDetail(String id) {
        MutableLiveData<SystemNoticeModel> liveData = new MutableLiveData();
        mdmService.getSystemNoticeDetail(id, new CallBack<SystemNoticeModel>() {
            @Override
            public void call(SystemNoticeModel data) {
                liveData.postValue(data);

            }

            @Override
            public void onFaild(Throwable throwable) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<UserInfoModel> getKaoQingSize(String orgCode) {
        MutableLiveData<UserInfoModel> liveData = new MutableLiveData();
        ucService.getKaoQingSize(orgCode, new CallBack<UserInfoModel>() {
            @Override
            public void call(UserInfoModel data) {
                liveData.postValue(data);

            }

            @Override
            public void onFaild(Throwable throwable) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<List<KaoQingOrgModel>> getOrgLocation() {
        MutableLiveData<List<KaoQingOrgModel>> liveData = new MutableLiveData();
        showLoading();
        ucService.getOrgLocation(new CallBack<List<KaoQingOrgModel>>() {
            @Override
            public void call(List<KaoQingOrgModel> data) {
                hideLoading();
                liveData.postValue(data);

            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<String> kaoQing(KaoQingOrgModel model) {
        MutableLiveData<String> liveData = new MutableLiveData();
        showLoading();
        userCenterService.updateWorkStatus(userModuleService.getUserId(), userModuleService.getUserName(), model.getStatus(), model.getOrgId(), model.getOrgName(), model.getMark(), model.getNote(), new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }

    public LiveData<List<Param>> ifKaoQingOut() {
        MutableLiveData<List<Param>> liveData = new MutableLiveData();
        showLoading();
        ucService.ifKaoQingOut(userModuleService.getUserName(), new CallBack<List<Param>>() {
            @Override
            public void call(List<Param> data) {
                hideLoading();
                liveData.postValue(data);

            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<List<KaoQingHistroyModel>> getKaoQingHistroy() {
        MutableLiveData<List<KaoQingHistroyModel>> liveData = new MutableLiveData();
        GetKaoQingHistoryRequest request = new GetKaoQingHistoryRequest();
        request.setUserId(userModuleService.getUserId());
        showLoading();
        ucService.getKaoQingHistroy(request, new CallBack<List<KaoQingHistroyModel>>() {
            @Override
            public void call(List<KaoQingHistroyModel> data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }

    public LiveData<String> getWorkState() {
        MutableLiveData<String> liveData = new MutableLiveData();
        showLoading();
        userCenterService.getWorkStatus(userModuleService.getUserId(), new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }
}
