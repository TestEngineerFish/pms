package com.einyun.app.pms.mine.viewmodule;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.repository.MsgRepository;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.GetNodeIdModel;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.library.resource.workorder.net.request.GetNodeIdRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.pms.mine.model.MsgModel;
import com.einyun.app.pms.mine.model.RequestPageBean;
import com.einyun.app.pms.mine.model.SignSetModule;
import com.einyun.app.pms.mine.repository.DataSourceFactory;
import com.einyun.app.pms.mine.repository.FeedBackRepository;

public class SignSetViewModel extends BasePageListViewModel<MsgModel> {
    FeedBackRepository repository=new FeedBackRepository();
    public MsgRepository msgRep=new MsgRepository();
    public WorkOrderService workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
    public ResourceWorkOrderService service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    private MutableLiveData<Boolean> approval=new MutableLiveData<>();
   public MutableLiveData<DisttributeDetialModel> workOrderLiveData = new MutableLiveData<>();
    public LiveData<Boolean> sumitSignText(SignSetModule Bean){
        showLoading();
        repository.SignTextSumit(Bean, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                approval.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return approval;
    }
    /**
     * 单个消息从未读到已读
     */
    private MutableLiveData<Boolean> singleReadModel=new MutableLiveData<>();
    public LiveData<Boolean> singleRead(String id){
        repository.singleRead(id, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                singleReadModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return singleReadModel;
    }
    /**
     * 抢单是否失效
     */
    private MutableLiveData<BaseResponse> isGrapModel=new MutableLiveData<>();
    public LiveData<BaseResponse> isGrap(String id){
        repository.isGrap(id, new CallBack<BaseResponse>() {
            @Override
            public void call(BaseResponse data) {
                hideLoading();
                isGrapModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return isGrapModel;
    }
    /**
     * 全部已读
     */
    private MutableLiveData<Boolean> allReadModel=new MutableLiveData<>();
    public LiveData<Boolean> allRead(String startTime,String endTime){
        repository.allRead(startTime,endTime, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                allReadModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return allReadModel;
    }
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<MsgModel>> loadPadingData(RequestPageBean requestBean, String tag){

        pageList = new LivePagedListBuilder(new DataSourceFactory(requestBean,tag), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();

        return pageList;
    }
    private ResourceWorkOrderService resourceWorkOrderService;
    public SignSetViewModel() {
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }
    public MutableLiveData<GetNodeIdModel> getNodeIdModelMutableLiveData = new MutableLiveData<>();
    private String nodeId;
    /**
     * 获取组织架构 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<GetNodeIdModel> getNodeId(GetNodeIdRequest request, OrderListModel model) {
        showLoading();
        resourceWorkOrderService.getNodeId(request, new CallBack<GetNodeIdModel>() {


            @Override
            public void call(GetNodeIdModel data) {
                hideLoading();
                if (data==null) {
                    nodeId="";
                }else {
                    if (TextUtils.isEmpty(data.getNodeId())){
                        nodeId="";
                    }else {
                        nodeId=data.getNodeId();
                    }
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
    /**
     *报修详情
     * */
    public LiveData<RepairsDetailModel> getRepairDetail(String instId){
        MutableLiveData<RepairsDetailModel> liveData = new MutableLiveData<>();
//        showLoading();
        workOrderService.getRepairDetail(instId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
//                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
//                ThrowableParser.onFailed(throwable);
                liveData.postValue(null);
            }
        });
        return liveData;
    }
    /**
     * 派工单获取代办详情
     *
     * @param taskId
     * @return
     */
    public LiveData<DisttributeDetialModel> pendingDetial(String taskId) {
//        showLoading();
        service.distributeWaitDetial(taskId, new CallBack<DisttributeDetialModel>() {
            @Override
            public void call(DisttributeDetialModel data) {
                workOrderLiveData.postValue(data);
//                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
                workOrderLiveData.postValue(null);
            }
        });
        return workOrderLiveData;
    }
    /**
     *计划工单 获取代办详情
     *
     * @return
     */
    MutableLiveData<PlanInfo> liveData = new MutableLiveData<>();
    public LiveData<PlanInfo> loadDetail(String proInsId, String taskId) {
            service.planOrderDetail(taskId, new CallBack<PlanInfo>() {
                @Override
                public void call(PlanInfo data) {
                    liveData.postValue(data);
                    hideLoading();
                }

                @Override
                public void onFaild(Throwable throwable) {
                    liveData.postValue(null);
//                    ThrowableParser.onFailed(throwable);
                }
            });


        return liveData;
    }
    /**
     * 获取巡查详情
     *
     * @return
     */
   public PatrolDetialRequest request=new PatrolDetialRequest();
    MutableLiveData<com.einyun.app.library.resource.workorder.model.PatrolInfo> liveData2 = new MutableLiveData<>();
    public LiveData<com.einyun.app.library.resource.workorder.model.PatrolInfo> loadPendingDetial() {

        service.patrolPendingDetial(request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                liveData2.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                    liveData2.postValue(null);

            }
        });
        return liveData2;
    }



}
