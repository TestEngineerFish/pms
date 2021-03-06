package com.einyun.app.pms.orderlist.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.GetNodeIdModel;
import com.einyun.app.library.resource.workorder.model.JobModel;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.model.OrgnizationModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.GetNodeIdRequest;
import com.einyun.app.library.resource.workorder.net.request.OrderListPageRequest;
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
    private OrderListPageRequest request = new OrderListPageRequest();
    LiveData<PagedList<OrderListModel>> liveData;
    private String tag;
    public OrderListPageRequest getRequest() {
        return request;
    }

    public void setRequest(OrderListPageRequest request) {
        this.request = request;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setOrgModel(OrgModel orgModel) {
        request.setDivideId(orgModel.getId());
        refreshUI();
    }

    public MutableLiveData<OrgnizationModel> orgnizationModelLiveData = new MutableLiveData<>();

    public MutableLiveData<GetNodeIdModel> getNodeIdModelMutableLiveData = new MutableLiveData<>();

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
     * ??????Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<OrderListModel>> loadPagingData(OrderListPageRequest orderListPageRequest, String tag){
        liveData= new LivePagedListBuilder(new DataSourceFactory(orderListPageRequest,tag), config)
                .build();
        return liveData;
    }
   /* *//**
     * ?????????????????? LiveData
     *
     * @return LiveData
     *//*
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
    }*/
    private String nodeId;
    /**
     * ?????????????????? LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<GetNodeIdModel> getNodeId(GetNodeIdRequest request,OrderListModel model) {
        showLoading();
        resourceWorkOrderService.getNodeId(request, new CallBack<GetNodeIdModel>() {


            @Override
            public void call(GetNodeIdModel data) {
                hideLoading();
                if (TextUtils.isEmpty(data.getNodeId())){
                    nodeId="";
                }else {
                    nodeId=data.getNodeId();
                }
                ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
                        .withString(RouteKey.KEY_ORDER_ID, model.getID_())
                        .withString(RouteKey.KEY_TASK_NODE_ID, nodeId)
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, model.getInstance_id())
                        .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                        .navigation();
                getNodeIdModelMutableLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return getNodeIdModelMutableLiveData;
    }

}
