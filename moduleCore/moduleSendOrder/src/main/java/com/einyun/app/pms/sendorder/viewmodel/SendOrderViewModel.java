package com.einyun.app.pms.sendorder.viewmodel;

import android.util.Log;

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

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING;
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
    public int listType= ListType.PENDING.getType();
    private ResourceWorkOrderService resourceWorkOrderService;
    private MutableLiveData<List<ResourceTypeBean>> tiaoxianList = new MutableLiveData<>();//条线
    private MutableLiveData<List<WorkOrderTypeModel>> workOrderTypeList = new MutableLiveData<>();//条线
    public List<ResourceTypeBean> resourceTypeBeans = new ArrayList<>();
    private DistributePageRequest request = new DistributePageRequest();
    public DistributePageRequest requestDone=new DistributePageRequest();

    public DistributePageRequest getRequest() {
        return request;
    }

    public void setRequest(DistributePageRequest request) {
        this.request = request;
    }


    public void setOrgModel(OrgModel orgModel) {
        if(listType==ListType.PENDING.getType()){
            request.setDivideId(orgModel.getId());
        }else{
            requestDone.setDivideId(orgModel.getId());
        }
        switchCondition();
    }

    public List<SelectModel> listAll = new ArrayList<>();

    public MutableLiveData<OrgnizationModel> orgnizationModelLiveData = new MutableLiveData<>();

    public SendOrderViewModel() {
        repo = new SendOrderRespository();
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    public MutableLiveData<List<JobModel>> jobModels = new MutableLiveData<>();

    /**
     * * 获取代办列表
     *
     * @return LiveData
     */
    public LiveData<PagedList<Distribute>> loadPadingData(DistributePageRequest request) {
        if (pendingBoundaryCallBack == null) {
            pendingBoundaryCallBack = new PendingBoundaryCallBack(request);
        }
        if (pageList == null) {
            pageList = new LivePagedListBuilder(repo.queryAll(request.getUserId(), Distribute.ORDER_TYPE_PENDING), config)
                    .setBoundaryCallback(pendingBoundaryCallBack)
                    .setFetchExecutor(Executors.newFixedThreadPool(2))
                    .build();
        }
        return pageList;
    }


    @Override
    public void refresh() {
        if(isPending()){
            pendingBoundaryCallBack.refresh();
        }else{
            doneBoundaryCallBack.refresh();
        }
    }

    private boolean isPending(){
        return listType==ListType.PENDING.getType();
    }

    public void switchCondition(){
        if(isPending()){
            pendingBoundaryCallBack.switchCondition();
        }else{
            doneBoundaryCallBack.switchCondition();
        }
    }

    /**
     * 获取已办列表
     *
     * @param request
     * @return
     */
    public LiveData<PagedList<Distribute>> loadDonePagingData(DistributePageRequest request) {
        if (doneBoundaryCallBack == null) {
            doneBoundaryCallBack = new DoneBoundaryCallBack(request);
        }
        if (donePageList == null) {
            donePageList = new LivePagedListBuilder(repo.queryAll(request.getUserId(), Distribute.ORDER_TYPE_DONE), config)
                    .setBoundaryCallback(doneBoundaryCallBack)
                    .setFetchExecutor(Executors.newFixedThreadPool(2))
                    .build();
        }
        return donePageList;
    }

    /**
     * 获取跳线 LiveData
     *
     * @return LiveData
     */
    public LiveData<List<ResourceTypeBean>> getTiaoXian() {
        resourceWorkOrderService.getTiaoXian(new CallBack<List<ResourceTypeBean>>() {
            @Override
            public void call(List<ResourceTypeBean> data) {
                hideLoading();
                tiaoxianList.postValue(data);
                resourceTypeBeans = new ArrayList<>();
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return tiaoxianList;
    }

    public void onConditionSelected(Map<String, SelectModel> selected) {
        if(isPending()){
            request.resetConditions();
        }else{
            requestDone.resetConditions();
        }

        if (selected.get(SELECT_LINE) != null) {
            String lineId = selected.get(SELECT_LINE).getKey();
            if(isPending()){
                request.setTxId(lineId);
            }else{
                requestDone.setTxId(lineId);
            }
        }
        if (selected.get(SELECT_ORDER_TYPE) != null) {
            String orderType = selected.get(SELECT_ORDER_TYPE).getKey();
            if(isPending()){
                request.setType(orderType);
            }else{
                requestDone.setType(orderType);
            }
        }
        if (selected.get(SELECT_ORDER_TYPE2) != null) {
            if(isPending()){
                request.setEnvType2(selected.get(SELECT_ORDER_TYPE2).getKey());
            }else{
                requestDone.setEnvType2(selected.get(SELECT_ORDER_TYPE2).getKey());
            }

        }
        if (selected.get(SELECT_ORDER_TYPE3) != null) {
            if(isPending()){
                request.setEnvType3(selected.get(SELECT_ORDER_TYPE3).getKey());
            }else{
                requestDone.setEnvType3(selected.get(SELECT_ORDER_TYPE3).getKey());
            }

        }
        if (selected.get(SELECT_IS_OVERDUE) != null) {
            if(isPending()){
                request.setFState(selected.get(SELECT_IS_OVERDUE).getKey());
            }else{
                requestDone.setFState(selected.get(SELECT_IS_OVERDUE).getKey());
            }
        }
        switchCondition();
    }

    /**
     * 获取跳线 LiveData
     *
     * @return LiveData
     */
    public LiveData<List<WorkOrderTypeModel>> getOrderType() {
        showLoading();
        resourceWorkOrderService.getWorkOrderType(new CallBack<List<WorkOrderTypeModel>>() {
            @Override
            public void call(List<WorkOrderTypeModel> data) {
                hideLoading();
                workOrderTypeList.postValue(data);
                //先获取第一级别，并将其他级别按照parentid分组
                listAll = new ArrayList<>();
                for (WorkOrderTypeModel beanLoop : data) {
                    SelectModel selectModel = new SelectModel();
                    selectModel.setId(beanLoop.getId());
                    selectModel.setIsCheck(false);
                    selectModel.setContent(beanLoop.getText());
                    selectModel.setType("");
                    selectModel.setTypeId(beanLoop.getTypeId());
                    selectModel.setKey(beanLoop.getKey());
                    selectModel.setName(beanLoop.getName());
                    selectModel.setParentId(beanLoop.getParentId());
                    selectModel.setOpen(beanLoop.getOpen());
                    selectModel.setText(beanLoop.getText());
                    selectModel.setKey(beanLoop.getKey());
                    listAll.add(selectModel);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return workOrderTypeList;
    }

    /**
     * 获取组织架构 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<OrgnizationModel> getOrgnization(String divideId) {
        showLoading();
        resourceWorkOrderService.getOrgnization(divideId, new CallBack<OrgnizationModel>() {
            @Override
            public void call(OrgnizationModel data) {
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
        resourceWorkOrderService.getJob(request, new CallBack<JobPage>() {
            @Override
            public void call(JobPage data) {
                hideLoading();
                jobModels.postValue(data.getRows());
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

}
