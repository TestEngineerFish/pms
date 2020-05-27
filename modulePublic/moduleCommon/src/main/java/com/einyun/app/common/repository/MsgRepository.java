package com.einyun.app.common.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.common.constants.URLS;
import com.einyun.app.common.model.DisqualifiedDetailModel;
import com.einyun.app.common.model.UrlxcgdGetInstBOModule;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.library.workorder.model.RepairsDetailModel;

import java.net.URL;

public class MsgRepository {
   public MsgServiceApi serviceApi;
    public WorkOrderService workOrderService;
    public ResourceWorkOrderService service;

    public MsgRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(MsgServiceApi.class);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    /**
     * 单个已读
     * @param id
     * @param callBack
     * @return
     */
    public LiveData<Boolean> singleRead(String id, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        String url= URLS.URL_GET_SINGLE_READ+id;
        serviceApi.singleRead(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
    /**
     * 接单
     * @param id
     * @param callBack
     * @return
     */
    public LiveData<Boolean> receiveOrder(String url,PatrolSubmitRequest request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.receiveOrder(url,request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
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
    MutableLiveData<DisttributeDetialModel> workOrderLiveData = new MutableLiveData<>();
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

    public void getApprovalBasicInfo(String id, CallBack<UrlxcgdGetInstBOModule> callBack) {
        String url = URLS.URL_GET_APPROVAL_BASIC_INFO+id+"&reqParams=";
        serviceApi.getApprovalBasicInfo(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
    /**
     * get
     * 获取待跟进详情信息
     */
    public void getTODODetailInfo(String taskId, CallBack<DisqualifiedDetailModel> callBack) {
        String url = URLS.URL_GET_TO_FOLLOW_UP_DETAIL+taskId+"&reqParams=";
        serviceApi.getTODODetailInfo(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }

}
