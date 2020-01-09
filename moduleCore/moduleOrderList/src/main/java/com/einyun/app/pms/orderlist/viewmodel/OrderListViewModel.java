package com.einyun.app.pms.orderlist.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.JobModel;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.model.OrgnizationModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.orderlist.repository.DataSourceFactory;

import java.util.List;
import java.util.Map;

import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE2;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE3;

public class OrderListViewModel extends BasePageListViewModel<OrderListModel> {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    LiveData<PagedList<Distribute>> donePageList;
    public int listType = ListType.PENDING.getType();
    private ResourceWorkOrderService resourceWorkOrderService;
    private DistributePageRequest request = new DistributePageRequest();
    LiveData<PagedList<OrderListModel>> liveData;
    public DistributePageRequest getRequest() {
        return request;
    }

    public void setRequest(DistributePageRequest request) {
        this.request = request;
    }

/*

    public void setOrgModel(OrgModel orgModel) {
        request.setDivideId(orgModel.getId());
        switchCondition();
    }
*/

    public MutableLiveData<OrgnizationModel> orgnizationModelLiveData = new MutableLiveData<>();

    public OrderListViewModel() {
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    public MutableLiveData<List<JobModel>> jobModels = new MutableLiveData<>();




    /*public void  onConditionSelected(Map<String, SelectModel> selected) {
        if (isPending()) {
            request.resetConditions();
        }
        request.setTxId(selected.get(SELECT_LINE) == null ? null : selected.get(SELECT_LINE).getKey());
        request.setType(selected.get(SELECT_ORDER_TYPE) == null ? null : selected.get(SELECT_ORDER_TYPE).getKey());
        request.setEnvType2(selected.get(SELECT_ORDER_TYPE2) == null ? null : selected.get(SELECT_ORDER_TYPE2).getKey());
        request.setEnvType3(selected.get(SELECT_ORDER_TYPE3) == null ? null : selected.get(SELECT_ORDER_TYPE3).getKey());
        request.setFState(selected.get(SELECT_IS_OVERDUE) == null ? null : selected.get(SELECT_IS_OVERDUE).getKey());
        switchCondition();
    }*/

    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<OrderListModel>> loadPagingData(DistributePageRequest distributePageRequest, String tag){
        liveData= new LivePagedListBuilder(new DataSourceFactory(distributePageRequest,tag), config)
                .build();
        return liveData;
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
}
