package com.einyun.app.pms.patrol.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.pms.patrol.convert.PatrolInfoTypeConvert;
import com.einyun.app.pms.patrol.repository.PatrolRepo;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class PatrolViewModel extends BaseUploadViewModel {
    PatrolRepo repo = new PatrolRepo();
    ResourceWorkOrderService service;
    MutableLiveData<PatrolInfo> liveData = new MutableLiveData<>();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    public PatrolDetialRequest request;

    public PatrolViewModel(){
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        request=new PatrolDetialRequest();
    }

    /**
     * 工作节点
     *
     * @param patrolInfo
     * @return
     */
    public List<WorkNode> loadNodes(PatrolInfo patrolInfo) {
        List<WorkNode> nodes;
        nodes = Observable
                .fromIterable(patrolInfo.getData().getZyxcgd().getSub_inspection_work_order_flow_node())
                .map(index -> new WorkNode(
                        index.getF_WK_ID(),
                        index.getF_WK_CONTENT(),
                        index.getF_WK_NODE(),
                        index.getSign_type(),
                        index.getIs_photo(),
                        index.getF_WK_RESULT(),
                        index.getSign_time(),
                        index.getSort(),
                        index.getPatrol_point_id(),
                        index.getPic_example_url(),
                        index.getPatrol_items()
                ))
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
     * 获取已办详情
     * @param orderId
     * @return
     */
    public LiveData<PatrolInfo> loadDoneDetial(String orderId){
        PatrolInfo patrolInfo = repo.loadPatrolInfo(orderId,userModuleService.getUserId());
        if (patrolInfo != null) {
            liveData.postValue(patrolInfo);
        }
        if (patrolInfo == null) {
            showLoading();
        }
        service.patrolDoneDetial(request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                PatrolInfo patrolInfo = saveCache(data, orderId);
                liveData.postValue(patrolInfo);
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }


    /**
     * 获取巡查详情
     *
     * @return
     */
    public LiveData<PatrolInfo> loadPendingDetial(String orderId) {
        PatrolInfo patrolInfo = repo.loadPatrolInfo(orderId,userModuleService.getUserId());
        if (patrolInfo != null) {
            liveData.postValue(patrolInfo);
        }
        if (patrolInfo == null) {
            showLoading();
        }
        service.patrolPendingDetial(request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                PatrolInfo patrolInfo = saveCache(data, orderId);
                liveData.postValue(patrolInfo);
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }

    /**
     * 缓存详情
     * @param data
     * @param orderId
     * @return
     */
    @NotNull
    protected PatrolInfo saveCache(com.einyun.app.library.resource.workorder.model.PatrolInfo data, String orderId) {
        String jsonStr = new Gson().toJson(data);
        PatrolInfoTypeConvert convert = new PatrolInfoTypeConvert();
        PatrolInfo patrolInfo = convert.stringToSomeObject(jsonStr);
        patrolInfo.setUserId(userModuleService.getUserId());
        patrolInfo.setId(orderId);
        patrolInfo.setTaskId(request.getTaskId());
        repo.loadLocalUserData(orderId, userModuleService.getUserId(), new CallBack<PatrolLocal>() {
            @Override
            public void call(PatrolLocal data) {
                if (data == null) {
                    PatrolLocal patrolLocal = new PatrolLocal();
                    patrolLocal.setOrderId(orderId);
                    patrolLocal.setUserId(userModuleService.getUserId());
                    repo.saveLocalData(patrolLocal);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
        repo.updatePatrolCached(orderId, userModuleService.getUserId());
        repo.insertPatrolInfo(patrolInfo);
        return patrolInfo;
    }

    /**
     * 提交
     * @return
     */
    public LiveData<Boolean> submit(PatrolSubmitRequest request){
        MutableLiveData<Boolean> liveData=new MutableLiveData();
        showLoading();
        service.patrolSubmit(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
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
     * 完成任务，结束任务
     * @param orderId
     */
    public LiveData<Boolean> finishTask(String orderId){
        MutableLiveData<Boolean> liveData=new MutableLiveData<>();
        repo.deleteTask(orderId,userModuleService.getUserId(), new CallBack<Boolean>() {
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


    /**
     * 获取用户本地输入数据
     *
     * @param orderId
     * @return
     */
    public LiveData<PatrolLocal> loadLocalUserData(String orderId) {
        MutableLiveData<PatrolLocal> liveData=new MutableLiveData<>();
         repo.loadLocalUserData(orderId,userModuleService.getUserId(), new CallBack<PatrolLocal>() {
             @Override
             public void call(PatrolLocal data) {
                liveData.postValue(data);
             }

             @Override
             public void onFaild(Throwable throwable) {

             }
         });
        return liveData;
    }

    /**
     * 保存本地用户输入数据
     *
     * @param local
     */
    public void saveLocal(PatrolLocal local) {
        repo.saveLocalData(local);
    }



}
