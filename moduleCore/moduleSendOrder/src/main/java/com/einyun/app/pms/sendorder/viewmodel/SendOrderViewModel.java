package com.einyun.app.pms.sendorder.viewmodel;

import android.net.LinkAddress;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WaitCount;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.response.TiaoXianResponse;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.repository.OrderDataSourceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendOrderViewModel extends BasePageListViewModel {
    private ResourceWorkOrderRepo resourceWorkOrderRepo;
    private ResourceWorkOrderService resourceWorkOrderService;
    private LiveData<DistributeWorkOrderPage> workOrderListViewModel;
    private MutableLiveData<List<ResourceTypeBean>> tiaoxianList = new MutableLiveData<>();//条线
    private MutableLiveData<List<WorkOrderTypeModel>> workOrderTypeList = new MutableLiveData<>();//条线
    public List<SelectModel> selectModelList = new ArrayList<>();
    public List<ResourceTypeBean> resourceTypeBeans = new ArrayList<>();
    private OrgModel orgModel;

    public OrgModel getOrgModel() {
        return orgModel;
    }

    public void setOrgModel(OrgModel orgModel) {
        this.orgModel = orgModel;
    }

    public List<SelectModel> listAll = new ArrayList<>();

    public SendOrderViewModel() {
        this.resourceWorkOrderRepo = new ResourceWorkOrderRepo();
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    /**
     * 获取Paging LiveData
     *
     * @return LiveData
     */
    public LiveData<PagedList<DistributeWorkOrder>> loadPadingData(DistributePageRequest request) {

        pageList = new LivePagedListBuilder(new OrderDataSourceFactory(request), config)
                .build();
        return pageList;
    }

    /**
     * 获取跳线 LiveData
     *
     * @return LiveData
     */
    public LiveData<List<ResourceTypeBean>> getTiaoXian() {
        showLoading();
        resourceWorkOrderRepo.getTiaoXian(new CallBack<List<ResourceTypeBean>>() {
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

    /**
     * 获取跳线 LiveData
     *
     * @return LiveData
     */
    public LiveData<List<WorkOrderTypeModel>> getOrderType() {
        showLoading();
        resourceWorkOrderRepo.getWorkOrderType(new CallBack<List<WorkOrderTypeModel>>() {
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
}
