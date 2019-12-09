package com.einyun.app.pms.sendorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.library.uc.user.model.UserInfoModel;
import com.einyun.app.library.uc.usercenter.net.request.SearchUserRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectPeopleViewModel extends BaseViewModel {
    private UserCenterService userCenterService;
    private WorkOrderService workOrderService;
    public List<GetMappingByUserIdsResponse> users;

    public SelectPeopleViewModel() {
        userCenterService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
    }

    public LiveData<List<GetMappingByUserIdsResponse>> searchUserByCondition(List<String> jobIdList, List<String> orgIdList) {
        SearchUserRequest request = new SearchUserRequest();
        SearchUserRequest.UserIds userIds = new SearchUserRequest.UserIds();
        userIds.setJobIdList(jobIdList);
        userIds.setOrgIdList(orgIdList);
        request.setParams(userIds);
        return userCenterService.searchUserByCondition(request, new CallBack<List<GetMappingByUserIdsResponse>>() {
            @Override
            public void call(List<GetMappingByUserIdsResponse> data) {
                List<String> request = new ArrayList<>();
                for (GetMappingByUserIdsResponse response : data) {
                    request.add(response.getId());
                }
                users = data;
                getMappingByUserIds(request);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    public LiveData<Map<String, GetMappingByUserIdsResponse>> getMappingByUserIds(List<String> request) {
        return workOrderService.getMappingByUserIds(request, new CallBack<Map<String, GetMappingByUserIdsResponse>>() {
            @Override
            public void call(Map<String, GetMappingByUserIdsResponse> data) {
                for (GetMappingByUserIdsResponse user : users) {
                    user.setPendingCount(data.get(user.getId()).getPendingCount());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }
}
