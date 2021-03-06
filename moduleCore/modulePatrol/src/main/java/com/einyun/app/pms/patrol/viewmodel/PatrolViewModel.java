package com.einyun.app.pms.patrol.viewmodel;

import android.net.Uri;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.bean.SubInspectionWorkOrderFlowNode;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.URLS;
import com.einyun.app.common.manager.GetUploadJson;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.Result;
import com.einyun.app.common.model.WorkOrderType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.common.utils.ToastUtil;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.patrol.convert.PatrolInfoTypeConvert;
import com.einyun.app.pms.patrol.model.DelayDay;
import com.einyun.app.pms.patrol.model.SignCheckResult;
import com.einyun.app.pms.patrol.model.SignInType;
import com.einyun.app.pms.patrol.repository.PatrolRepo;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;

public class PatrolViewModel extends BaseWorkOrderHandelViewModel {
    protected PatrolRepo repo = new PatrolRepo();
    ResourceWorkOrderService service;
    MutableLiveData<PatrolInfo> liveData = new MutableLiveData<>();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    public PatrolDetialRequest request;
    protected MutableLiveData<PatrolLocal> localData=new MutableLiveData<>();
    public IUserModuleService getUserService(){
        return userModuleService;
    }
    public void setUserModuleService(IUserModuleService userModuleService){
        this.userModuleService=userModuleService;
    }
    public PatrolViewModel(){
        workOrderType= WorkOrderType.PATROL.type();
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        request=new PatrolDetialRequest();
    }
    /**
     * ??????
     * @return
     */
    public LiveData<BaseResponse> receiceOrder(PatrolSubmitRequest request){
        MutableLiveData<BaseResponse> liveData=new MutableLiveData();
        showLoading();
        String url= URLS.URL_GET_PRATROL_RECEIVE_ORDER;
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
     * ??????
     * @return
     */
    public LiveData<BaseResponse> assignOrder(PatrolSubmitRequest request){
        MutableLiveData<BaseResponse> liveData=new MutableLiveData();
        showLoading();
        String url= URLS.URL_GET_PATROL_ASSIGNE_ORDER;
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
     * ????????????
     *
     * @param patrolInfo
     * @return
     */
    public List<WorkNode> loadNodes(PatrolInfo patrolInfo) {
        List<WorkNode> nodes;
        if (patrolInfo==null) {
            return new ArrayList<>();
        }
        nodes = Observable
                .fromIterable(patrolInfo.getData().getZyxcgd().getSub_inspection_work_order_flow_node())
                .map(index -> new WorkNode(
                        index.getF_WK_ID(),
                        index.getF_WK_CONTENT(),
                        index.getF_WK_NODE(),
                        index.getSign_type(),
                        index.getSign_result(),
                        index.getIs_photo(),
                        index.getF_WK_RESULT(),
                        index.getSign_time(),
                        index.getSort(),
                        index.getPatrol_point_id(),
                        index.getPic_example_url(),
                        index.getPatrol_items(),
                        index.getPic_url()
                ))
                .toList()
                .blockingGet();
        Collections.sort(nodes, (o1, o2) -> {
            if (TextUtils.isEmpty(o1.number) || TextUtils.isEmpty(o2.number)) {
                return o1.sort-o2.sort;
            }
            int num1 = Integer.parseInt(o1.number);
            int num2 = Integer.parseInt(o2.number);
            return num1 - num2;
        });
        return nodes;
    }

    /**
     * ??????????????????
     * @param orderId
     * @return
     */
    public LiveData<PatrolInfo> loadDoneDetial(String orderId){
        PatrolInfo patrolInfo = repo.loadPatrolInfo(orderId,userModuleService.getUserId());
        if (patrolInfo != null) {
            liveData.postValue(patrolInfo);
        }
        if (patrolInfo == null) {
//            showLoading();
        }
        service.patrolDoneDetial(request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                PatrolInfo patrolInfo = saveCache(data, orderId);
                liveData.postValue(patrolInfo);
//                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
                if(patrolInfo==null){
                    liveData.postValue(null);
                }
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }


    /**
     * ??????????????????
     *
     * @return
     */
    public LiveData<PatrolInfo> loadPendingDetial(String orderId) {
        PatrolInfo patrolInfo = repo.loadPatrolInfo(orderId,userModuleService.getUserId());
        if (patrolInfo != null) {
            liveData.postValue(patrolInfo);
        }
        if (patrolInfo == null) {
//            showLoading();
        }
        service.patrolPendingDetial(request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                PatrolInfo patrolInfo = saveCache(data, orderId);
                liveData.postValue(patrolInfo);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                if(patrolInfo==null){
                    liveData.postValue(null);
                }
            }
        });
        return liveData;
    }

    /**
     * ????????????
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
     * ??????
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
                LiveDataBusUtils.postPatrolClosedRefresh();
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
     * ???????????????????????????
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
                ToastUtil.show(CommonApplication.getInstance(),"??????????????????"+throwable.getMessage());
                liveData.postValue(false);
            }
        });
        return liveData;
    }

    public boolean hasSignIn(WorkNode workNode){
        if(SignInType.QR.equals(workNode.sign_type)){
            if(SignCheckResult.SIGN_IN_SUCCESS==workNode.sign_result){
                return true;
            }
        }
        return false;
    }

    public boolean hasCapture(WorkNode workNode){
        if(workNode.is_photo>0){
            if(!TextUtils.isEmpty(workNode.pic_url)||(workNode.getCachedImages()!=null&&workNode.getCachedImages().size()>0)){
                return true;
            }
        }
        return false;
    }


    /**
     * ??????????????????????????????
     *
     * @param orderId
     * @return
     */
    public LiveData<PatrolLocal> loadLocalUserData(String orderId) {
         repo.loadLocalUserData(orderId,userModuleService.getUserId(), new CallBack<PatrolLocal>() {
             @Override
             public void call(PatrolLocal data) {
                 localData.postValue(data);
             }

             @Override
             public void onFaild(Throwable throwable) {

             }
         });
        return localData;
    }

    /**
     * ?????????????????????
     * @param orderId
     * @param pointId
     */
    public void signInSuccess(String orderId,String pointId){
        repo.loadLocalUserData(orderId, userModuleService.getUserId(), new CallBack<PatrolLocal>() {
            @Override
            public void call(PatrolLocal data) {
                try {
                    List<WorkNode> nodes=data.getNodes();
                    for(WorkNode node:nodes){
                        if(pointId.equals(node.patrol_point_id)){
                            node.setSign_time(TimeUtil.Now());
                            node.setSign_result(SignCheckResult.SIGN_IN_SUCCESS);
                            repo.saveLocalData(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * ??????????????????????????????
     *
     * @param local
     */
    public void saveLocal(PatrolLocal local) {
        local.setUserId(userModuleService.getUserId());
        repo.saveLocalData(local);
    }


    /**
     * ??????????????????????????????
     * @param nodes
     * @return
     */
    public LiveData<List<WorkNode>> uploadWorkNodesImages(List<WorkNode> nodes) {
        MutableLiveData<List<WorkNode>>liveData=new MutableLiveData<>();
        int count=0;
        //???????????????????????????????????????
        for(WorkNode node:nodes){
            if(node.getIs_photo()>0&&!TextUtils.isEmpty(node.getPatrol_point_id())){
                count++;
            }
        }
        if(count<=0){
            liveData.postValue(nodes);
            return liveData;
        }

        ExecutorService fixedThreadPool=Executors.newFixedThreadPool(5);
        CountDownLatch latch=new CountDownLatch(count);//??????????????????
        //????????????
        showLoading();
        for(WorkNode node:nodes){
            if(node.getIs_photo()>0&&!TextUtils.isEmpty(node.getPatrol_point_id())){
                fixedThreadPool.execute(() -> {
                    List<String> images=node.getCachedImages();//???????????????????????????????????????
                    List<Uri> uris=convertUris(images);
                    ImageUploadManager manager=new ImageUploadManager();
                    manager.upload(uris, new CallBack<List<PicUrl>>() {
                        @Override
                        public void call(List<PicUrl> data) {
                            //??????????????????
                            GetUploadJson getUploadJsonStr = new GetUploadJson(data).invoke();
                            List<PicUrlModel> picUrlModels = getUploadJsonStr.getPicUrlModels();
                            String picsJson=getUploadJsonStr.getGson().toJson(picUrlModels);
                            node.setPic_url(picsJson);//?????????????????????????????????????????????
                            latch.countDown();
                        }

                        @Override
                        public void onFaild(Throwable throwable) {
                            latch.countDown();
                        }
                    });
                });
            }
        }
        fixedThreadPool.execute(() -> {
            try {
                latch.await();//????????????????????????
                hideLoading();
                liveData.postValue(nodes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        fixedThreadPool.shutdown();
        return liveData;
    }

    /**
     * ???????????????????????????????????????
     */
    public List<SubInspectionWorkOrderFlowNode> wrapWorkOrderFlowNodes(List<SubInspectionWorkOrderFlowNode> flowNodes,List<WorkNode> nodes){
        for(SubInspectionWorkOrderFlowNode flowNode:flowNodes){
            for(WorkNode node:nodes){
                if(flowNode.getPatrol_point_id().equals(node.getPatrol_point_id())){
                    flowNode.setPic_url(node.getPic_url());
                    flowNode.setSign_result(node.getSign_result());
                    flowNode.setSign_time(node.getSign_time());
                    flowNode.setF_WK_RESULT(SignCheckResult.F_WK_RESULT_SUCCESS);
                }
            }
        }
        return flowNodes;
    }

    /**
     * ??????????????????String 2 Uri
     * @param images
     * @return
     */
    private List<Uri> convertUris(List<String> images){
        List<Uri> uris=new ArrayList<>();
        for(String path:images){
            Uri uri=Uri.parse(path);
            uris.add(uri);
        }
        return uris;
    }

    private MutableLiveData<DelayDay> delayDay=new MutableLiveData<>();
    public LiveData<DelayDay> getDealyInfo(String id){
        showLoading();
        repo.getDealyInfo(id, new CallBack<DelayDay>() {
            @Override
            public void call(DelayDay data) {
                hideLoading();
                delayDay.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return delayDay;
    }
}
