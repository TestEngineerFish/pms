package com.einyun.app.pms.plan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.db.entity.Plan;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.model.JobModel;
import com.einyun.app.library.resource.workorder.model.JobPage;
import com.einyun.app.library.resource.workorder.model.OrgnizationModel;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrder;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.GetJobRequest;
import com.einyun.app.library.resource.workorder.net.request.ResendOrderRequest;
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.plan.repository.DoneBoundaryCallBack;
import com.einyun.app.pms.plan.repository.OrderDataSourceFactory;
import com.einyun.app.pms.plan.repository.PendingBoundaryCallBack;
import com.einyun.app.pms.plan.repository.PlanRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_DATE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE2;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE3;

public class PlanOrderViewModel extends BasePageListViewModel<PlanWorkOrder> {
    private ResourceWorkOrderRepo resourceWorkOrderRepo;
    LiveData<PagedList<PlanWorkOrder>> donePageList;
    private OrgModel orgModel;
    private PlanRepository planRepository;
    public DistributePageRequest request=new DistributePageRequest();
    private PendingBoundaryCallBack pendingBoundaryCallBack;
    private DoneBoundaryCallBack doneBoundaryCallBack;

    private LiveData<PagedList<Plan>> dbPendingPageList;
    private LiveData<PagedList<Plan>> dbClosedPageList;

    public void setRequest(DistributePageRequest request) {
        this.request = request;
    }

    public OrgModel getOrgModel() {
        return orgModel;
    }

    public void setOrgModel(OrgModel orgModel) {
        this.orgModel = orgModel;
        request.setDivideId(orgModel.getId());
//        refreshUI();
    }
    public PlanOrderViewModel() {
        this.resourceWorkOrderRepo = new ResourceWorkOrderRepo();
        planRepository=new PlanRepository();
    }

    public MutableLiveData<List<JobModel>> jobModels=new MutableLiveData<>();

    public void refresh(String fragmentTag){
        if(fragmentTag.equals(RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)){
            if(pendingBoundaryCallBack!=null){
                pendingBoundaryCallBack.refresh();
            }
        }else{
            if(doneBoundaryCallBack!=null){
                doneBoundaryCallBack.refresh();
            }
        }
    }

    public void switchCondition(String fragmentTag){
        if(fragmentTag.equals(RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)){
            if(pendingBoundaryCallBack!=null){
                pendingBoundaryCallBack.switchCondition();
            }
        }else{
            if(doneBoundaryCallBack!=null){
                doneBoundaryCallBack.switchCondition();
            }
        }
    }

    /**
     * 获取代办列表
     * @return
     */
    public LiveData<PagedList<Plan>> loadPendingInDB() {
        if(pendingBoundaryCallBack==null){
            pendingBoundaryCallBack=new PendingBoundaryCallBack(request);
        }
        if(dbPendingPageList==null){
            dbPendingPageList=new LivePagedListBuilder(planRepository.queryAll(request.getUserId(), ListType.PENDING.getType()), config)
                    .setBoundaryCallback(pendingBoundaryCallBack)
                    .setFetchExecutor(Executors.newFixedThreadPool(2))
                    .build();
        }
        return dbPendingPageList;
    }

    /**
     * 获取已办列表
     * @return
     */
    public LiveData<PagedList<Plan>> loadDoneInDB(){
        if(doneBoundaryCallBack==null){
            doneBoundaryCallBack=new DoneBoundaryCallBack(request);
        }
        if(dbClosedPageList==null){
           dbClosedPageList= new LivePagedListBuilder(planRepository.queryAll(request.getUserId(), ListType.DONE.getType()), config)
                    .setBoundaryCallback(doneBoundaryCallBack)
                    .setFetchExecutor(Executors.newFixedThreadPool(2))
                    .build();
        }
        return dbClosedPageList;
    }

    /**
     * * 在线搜索代办列表
     *
     * @return LiveData
     */
    public LiveData<PagedList<PlanWorkOrder>> loadPadingNetData(String tag) {
        if (!StringUtil.isNullStr(request.getDivideId())){
            request.setDivideId(null);
        }
        pageList = new LivePagedListBuilder(new OrderDataSourceFactory(request,tag), config)
                .build();
        return pageList;
    }


    /**
     * 在线搜索已办列表
     * @return
     */
    public LiveData<PagedList<PlanWorkOrder>> loadDonePagingNetData(String tag){
       donePageList= new LivePagedListBuilder(new OrderDataSourceFactory(request,tag), config)
                .build();
       return donePageList;
    }

    public void onConditionSelected(Map<String, SelectModel> selected){
        request.resetConditions();
        request.setPeriod(selected.get(SELECT_DATE)==null?null:selected.get(SELECT_DATE).getKey());
        request.setDivideId(selected.get(SELECT_LINE)==null?null:selected.get(SELECT_LINE).getKey());
        request.setType(selected.get(SELECT_ORDER_TYPE)==null?null:selected.get(SELECT_ORDER_TYPE).getKey());
        request.setEnvType2(selected.get(SELECT_ORDER_TYPE2)==null?null:selected.get(SELECT_ORDER_TYPE2).getKey());
        request.setEnvType3(selected.get(SELECT_ORDER_TYPE3)==null?null:selected.get(SELECT_ORDER_TYPE3).getKey());
        request.setOtStatus(selected.get(SELECT_IS_OVERDUE)==null?null:selected.get(SELECT_IS_OVERDUE).getKey());
//        refreshUI();
    }

    /**
     * 获取审批角色 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<List<JobModel>> getJob(GetJobRequest request) {
        showLoading();
        resourceWorkOrderRepo.getJob(request,new CallBack<JobPage>() {
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
        MutableLiveData<ResendOrderResponse> resend=new MutableLiveData<>();
        resourceWorkOrderRepo.resendOrder(request,new CallBack<ResendOrderResponse>() {
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
