package com.einyun.app.pms.plan.viewmodel;

import android.os.Handler;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.db.entity.PlanLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.URLS;
import com.einyun.app.common.repository.MsgRepository;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.ForseScanCodeModel;
import com.einyun.app.base.db.entity.PlanInfo;
import com.einyun.app.library.resource.workorder.model.Sub_jhgdgzjdb;
import com.einyun.app.library.resource.workorder.net.request.DoneDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.pms.plan.convert.PlanInfoTypeConvert;
import com.einyun.app.pms.plan.model.PlanOrderResLineModel;
import com.einyun.app.pms.plan.repository.PlanOrderRepository;
import com.einyun.app.pms.plan.repository.PlanOrderServiceApi;
import com.einyun.app.pms.plan.repository.PlanRepository;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;

public class PlanOrderDetailViewModel extends BaseWorkOrderHandelViewModel {
    MutableLiveData<com.einyun.app.base.db.entity.PlanInfo> liveData = new MutableLiveData<>();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    ResourceWorkOrderService service;
    public MsgRepository repository;
    public  PlanRepository planRepository;

    public PlanOrderDetailViewModel(){
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        repository=new MsgRepository();
        planRepository = new PlanRepository();
    }
    PlanOrderRepository repository2= new PlanOrderRepository("");

    public String getUserName(){

        return userModuleService.getRealName();
    }
    public String getUserID(){

        return userModuleService.getUserId();
    }
    /**
     * 提交
     * @return
     */
    public LiveData<Boolean> submit(PatrolSubmitRequest request){
        MutableLiveData<Boolean> liveData=new MutableLiveData();
        showLoading();
        service.planSubmit(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                com.einyun.app.common.utils.ToastUtil.show(CommonApplication.getInstance(),"工单处理失败"+throwable.getMessage());
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }
    /**
     * 接单
     * @return
     */
    public LiveData<BaseResponse> receiceOrder(PatrolSubmitRequest request){
        MutableLiveData<BaseResponse> liveData=new MutableLiveData();
        showLoading();
        String url= URLS.URL_GET_RECEIVE_ORDER;
        repository.receiveOrder(url,request, new CallBack<BaseResponse>() {
            @Override
            public void call(BaseResponse data) {
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
    /**
     * 指派
     * @return
     */
    public LiveData<BaseResponse> assignOrder(PatrolSubmitRequest request){
        MutableLiveData<BaseResponse> liveData=new MutableLiveData();
        showLoading();
        String url= URLS.URL_GET_ASSIGNE_ORDER;
        repository.receiveOrder(url,request, new CallBack<BaseResponse>() {
            @Override
            public void call(BaseResponse data) {
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
    /**
     * checkQrCode
     * @return
     */
    public LiveData<ForseScanCodeModel> checkQrCode(String  request){
        MutableLiveData<ForseScanCodeModel> checkQrCodeModel=new MutableLiveData();
        showLoading();
        service.checkQrCodeModel(request, new CallBack<ForseScanCodeModel>() {
            @Override
            public void call(ForseScanCodeModel data) {
                hideLoading();
                checkQrCodeModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ToastUtil.show(CommonApplication.getInstance(), "请扫描正确的二维码");
                ThrowableParser.onFailed(throwable);
            }
        });
        return checkQrCodeModel;
    }

    /**
     * 获取上次催缴time
     */
    private MutableLiveData<List<PlanOrderResLineModel>> getLastWorthTime=new MutableLiveData<>();
    public LiveData<List<PlanOrderResLineModel>> getLastWorthTime(String did){
//        showLoading();
        repository2.getLastWorthTime( did,new CallBack<List<PlanOrderResLineModel>>() {
            @Override
            public void call(List<PlanOrderResLineModel> data) {
                hideLoading();
                getLastWorthTime.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getLastWorthTime;
    }
    /**
     * 工作节点
     *
     * @param patrolInfo
     * @return
     */
    public List<WorkNode> loadNodes(PlanInfo patrolInfo) {
        List<WorkNode> nodes;
        nodes = Observable
                .fromIterable(patrolInfo.getData().getZyjhgd().getSub_jhgdgzjdb())
                .map(index -> new WorkNode(
                        index.getF_WK_ID(),
                        index.getF_WK_CONTENT(),
                        index.getF_WK_NODE(),
                        index.getF_WK_RESULT(),
                        index.getF_CHECK_RESULT(),
                        index.getF_NODE_PICTURE()))
                .toList()
                .blockingGet();
        Collections.sort(nodes, (o1, o2) -> {
            if (TextUtils.isEmpty(o1.number) || TextUtils.isEmpty(o2.number)) {
                return 0;
            }
            int num1 = Integer.parseInt(o1.number);
            int num2 = Integer.parseInt(o2.number);
            return num1 - num2;
        });
        return nodes;
    }

    /**
     * 获取巡查详情
     *
     * @return
     */
    public LiveData<PlanInfo> loadDetail(String proInsId, String taskId, String taskNodeId, String fragmentTag,String orderId) {
        if (fragmentTag.equals(FRAGMENT_PLAN_OWRKORDER_DONE)) {
//            PlanInfo planInfo = planRepository.loadPlanInfo(orderId, userModuleService.getUserId());
//            if (planInfo != null) {
//                liveData.postValue(planInfo);
//            }
            DoneDetialRequest request = new DoneDetialRequest();
            request.setProInsId(proInsId);
            request.setTaskNodeId(taskNodeId);
            service.planDoneDetial(request, new CallBack<com.einyun.app.library.resource.workorder.model.PlanInfo>() {
                @Override
                public void call(com.einyun.app.library.resource.workorder.model.PlanInfo data) {
                    PlanInfo planInfo = saveCache(data, orderId);
                    liveData.postValue(planInfo);
                    hideLoading();
                }

                @Override
                public void onFaild(Throwable throwable) {
                    if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
                        //连接错误
                        ToastUtil.show(CommonApplication.getInstance(), "请连接网络后，进行处理");
                    }else {
                        ThrowableParser.onFailed(throwable);
                    }
//                    liveData.postValue(null);
                }
            });
        } else {
            PlanInfo planInfo = planRepository.loadPlanInfo(orderId, userModuleService.getUserId());
            if (planInfo != null) {
                liveData.postValue(planInfo);
            }
            service.planOrderDetail(taskId, new CallBack<com.einyun.app.library.resource.workorder.model.PlanInfo>() {
                @Override
                public void call(com.einyun.app.library.resource.workorder.model.PlanInfo data) {
                    PlanInfo planInfo = saveCache(data, orderId);
                    liveData.postValue(planInfo);
                    hideLoading();
                }

                @Override
                public void onFaild(Throwable throwable) {
//                    liveData.postValue(null);
                    if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
                        //连接错误
                        ToastUtil.show(CommonApplication.getInstance(), "请连接网络后，进行处理");
                    }else {

                        ThrowableParser.onFailed(throwable);
                    }
                }
            });
        }

        return liveData;
    }
    /**
     * 缓存详情
     * @param data
     * @param orderId
     * @return
     */
    @NotNull
    public PlanInfo saveCache(com.einyun.app.library.resource.workorder.model.PlanInfo data, String orderId) {
        String jsonStr = new Gson().toJson(data);
        PlanInfoTypeConvert convert = new PlanInfoTypeConvert();
        PlanInfo patrolInfo = convert.stringToSomeObject(jsonStr);
        patrolInfo.setUserId(userModuleService.getUserId());
        patrolInfo.setId(orderId);
        planRepository.insertPlanInfo(patrolInfo);
//        patrolInfo.setTaskId(request.getTaskId());
//        repo.loadLocalUserData(orderId, userModuleService.getUserId(), new CallBack<PatrolLocal>() {
//            @Override
//            public void call(PatrolLocal data) {
//                if (data == null) {
//                    PatrolLocal patrolLocal = new PatrolLocal();
//                    patrolLocal.setOrderId(orderId);
//                    patrolLocal.setUserId(userModuleService.getUserId());
//                    repo.saveLocalData(patrolLocal);
//                }
//            }
//
//            @Override
//            public void onFaild(Throwable throwable) {
//
//            }
//        });
        planRepository.updatePlanCached(orderId, userModuleService.getUserId());
//        repo.insertPatrolInfo(patrolInfo);
        return patrolInfo;
    }
    /**
     * 缓存操作
     * @param data
     * @param orderId
     * @return
     */
    @NotNull
    public PlanInfo saveCache(PlanInfo data, String orderId) {
        String jsonStr = new Gson().toJson(data);
        PlanInfoTypeConvert convert = new PlanInfoTypeConvert();
        PlanInfo patrolInfo = convert.stringToSomeObject(jsonStr);
        patrolInfo.setUserId(userModuleService.getUserId());
        patrolInfo.setId(orderId);
        planRepository.insertPlanInfo(patrolInfo);
//        patrolInfo.setTaskId(request.getTaskId());
//        repo.loadLocalUserData(orderId, userModuleService.getUserId(), new CallBack<PatrolLocal>() {
//            @Override
//            public void call(PatrolLocal data) {
//                if (data == null) {
//                    PatrolLocal patrolLocal = new PatrolLocal();
//                    patrolLocal.setOrderId(orderId);
//                    patrolLocal.setUserId(userModuleService.getUserId());
//                    repo.saveLocalData(patrolLocal);
//                }
//            }
//
//            @Override
//            public void onFaild(Throwable throwable) {
//
//            }
//        });
//        repo.insertPatrolInfo(patrolInfo);
        return patrolInfo;
    }
    /**
     * 获取用户本地输入数据
     *
     * @param orderId
     * @return
     */
    protected MutableLiveData<PlanLocal> localData=new MutableLiveData<>();
    public LiveData<PlanLocal> loadLocalUserData(String orderId) {
        planRepository.loadPlanLocal(orderId,userModuleService.getUserId(), new CallBack<PlanLocal>() {
            @Override
            public void call(PlanLocal data) {
                localData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
        return localData;
    }
    /**
     * 保存本地用户输入数据
     *
     * @param local
     */
    public void saveLocal(PlanLocal local) {
        local.setUserId(userModuleService.getUserId());
        planRepository.insertPlanLocal(local);
    }
//    /**
//     * 删除本地缓存
//     *
//     * @param
//     */
//    public void delLocal(String orderId) {
//        planRepository.deletePlanInfo(orderId,userModuleService.getUserId());
//        planRepository.deletePlanLocal(orderId,userModuleService.getUserId());
//    }
    /**
     * 完成任务，结束任务
     * @param orderId
     */
    public LiveData<Boolean> finishTask(String orderId){
        MutableLiveData<Boolean> liveData=new MutableLiveData<>();
        planRepository.deleteTask(orderId,userModuleService.getUserId(), new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                liveData.postValue(true);
            }

            @Override
            public void onFaild(Throwable throwable) {
                liveData.postValue(false);
            }
        });
        return liveData;
    }
}
