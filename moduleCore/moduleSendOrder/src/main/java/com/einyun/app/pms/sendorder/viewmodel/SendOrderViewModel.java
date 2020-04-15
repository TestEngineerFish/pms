package com.einyun.app.pms.sendorder.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.JobModel;
import com.einyun.app.library.resource.workorder.model.JobPage;
import com.einyun.app.library.resource.workorder.model.OrgnizationModel;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.GetJobRequest;
import com.einyun.app.library.resource.workorder.net.request.GetOrgRequest;
import com.einyun.app.library.resource.workorder.net.request.ResendOrderRequest;
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.sendorder.repository.DoneBoundaryCallBack;
import com.einyun.app.pms.sendorder.repository.PendingBoundaryCallBack;
import com.einyun.app.pms.sendorder.repository.SendOrderRespository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE2;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE3;

public class SendOrderViewModel extends BasePageListViewModel<Distribute> {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    LiveData<PagedList<Distribute>> donePageList;
    PendingBoundaryCallBack pendingBoundaryCallBack;
    DoneBoundaryCallBack doneBoundaryCallBack;
    SendOrderRespository repo;
    public int listType = ListType.PENDING.getType();
    private ResourceWorkOrderService resourceWorkOrderService;
    private DistributePageRequest request = new DistributePageRequest();

    public DistributePageRequest getRequest() {
        return request;
    }

    public void setRequest(DistributePageRequest request) {
        this.request = request;
    }


    public void setOrgModel(OrgModel orgModel) {
        request.setDivideId(orgModel.getId());
        switchCondition();
    }

    public MutableLiveData<List<OrgnizationModel>> orgnizationModelLiveData = new MutableLiveData<>();

    public SendOrderViewModel() {
        repo = new SendOrderRespository();
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    public MutableLiveData<List<JobModel>> jobModels = new MutableLiveData<>();

    /**
     * * 获取代办列表
     * @return LiveData
     */
    public LiveData<PagedList<Distribute>> loadPadingData() {
        if (pendingBoundaryCallBack == null) {
            pendingBoundaryCallBack = new PendingBoundaryCallBack(request);
        }
        if (pageList == null) {
            pageList = new LivePagedListBuilder(repo.queryAll(request.getUserId(), Distribute.ORDER_TYPE_PENDING), config)
                    .setBoundaryCallback(pendingBoundaryCallBack)
                    .build();
        }
        return pageList;
    }


    @Override
    public void refresh() {
        if (isPending()) {
            pageList.getValue().getDataSource().invalidate();
            pendingBoundaryCallBack.refresh();
        } else {
            donePageList.getValue().getDataSource().invalidate();
            doneBoundaryCallBack.refresh();
        }
    }

    private boolean isPending() {
        return listType == ListType.PENDING.getType();
    }

    public void switchCondition() {
        if (isPending()) {
            pendingBoundaryCallBack.switchCondition();
        } else {
            doneBoundaryCallBack.switchCondition();
        }
    }

    /**
     * 获取已办列表
     *
     * @return
     */
    public LiveData<PagedList<Distribute>> loadDonePagingData() {
        if (doneBoundaryCallBack == null) {
            doneBoundaryCallBack = new DoneBoundaryCallBack(request);
        }
        if (donePageList == null) {
            donePageList = new LivePagedListBuilder(repo.queryAll(request.getUserId(), Distribute.ORDER_TYPE_DONE), config)
                    .setBoundaryCallback(doneBoundaryCallBack)
                    .build();
        }
        return donePageList;
    }

    public void  onConditionSelected(Map<String, SelectModel> selected) {
        if (isPending()) {
            request.resetConditions();
        }
        request.setTxId(selected.get(SELECT_LINE) == null ? null : selected.get(SELECT_LINE).getKey());
        request.setType(selected.get(SELECT_ORDER_TYPE) == null ? null : selected.get(SELECT_ORDER_TYPE).getKey());
        request.setEnvType2(selected.get(SELECT_ORDER_TYPE2) == null ? null : selected.get(SELECT_ORDER_TYPE2).getKey());
        request.setEnvType3(selected.get(SELECT_ORDER_TYPE3) == null ? null : selected.get(SELECT_ORDER_TYPE3).getKey());
        request.setFState(selected.get(SELECT_IS_OVERDUE) == null ? null : selected.get(SELECT_IS_OVERDUE).getKey());
        switchCondition();
    }


    /**
     * 获取组织架构 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<List<OrgnizationModel>> getOrgnization(GetOrgRequest request) {
        showLoading();
        resourceWorkOrderService.getOrgnization(request, new CallBack<List<OrgnizationModel>>() {
            @Override
            public void call(List<OrgnizationModel> data) {
                hideLoading();
                orgnizationModelLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return orgnizationModelLiveData;
    }

    /**
     * 获取审批角色 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<List<JobModel>> getJob(GetJobRequest request) {
        showLoading();
        resourceWorkOrderService.getJob(request, new CallBack<List<JobModel>>() {
            @Override
            public void call(List<JobModel> data) {
                hideLoading();
                jobModels.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
            }
        });

        return jobModels;
    }

    /**
     * 转单 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<ResendOrderResponse> resendOrder(ResendOrderRequest request) {
        showLoading();
        MutableLiveData<ResendOrderResponse> resend = new MutableLiveData<>();
        resourceWorkOrderService.resendOrder(request, new CallBack<ResendOrderResponse>() {
            @Override
            public void call(ResendOrderResponse data) {
                hideLoading();
                resend.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
        return resend;
    }
    /**
     * 三大客服类转单 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<ResendOrderResponse> resendCusOrder(ResendOrderRequest request) {
        showLoading();
        MutableLiveData<ResendOrderResponse> resend = new MutableLiveData<>();
        resourceWorkOrderService.resendCusOrder(request, new CallBack<ResendOrderResponse>() {
            @Override
            public void call(ResendOrderResponse data) {
                hideLoading();
                resend.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return resend;
    }
}
