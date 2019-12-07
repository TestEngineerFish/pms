package com.einyun.app.pms.patrol.viewmodel;

import android.net.Uri;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.net.request.PatrolDetialRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.patrol.convert.PatrolInfoTypeConvert;
import com.einyun.app.pms.patrol.repository.PatrolRepo;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;

public class PatrolViewModel extends BaseUploadViewModel {
    PatrolRepo repo = new PatrolRepo();
    MutableLiveData<PatrolInfo> liveData = new MutableLiveData<>();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

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
                        index.getF_WK_RESULT()))
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
     * @param taskId
     * @return
     */
    public LiveData<PatrolInfo> loadDetial(String taskId) {
        PatrolInfo patrolInfo = repo.loadPatrolInfo(taskId);
        if (patrolInfo != null) {
            liveData.postValue(patrolInfo);
        }
        if (patrolInfo == null) {
            showLoading();
        }
        PatrolDetialRequest request = new PatrolDetialRequest();
        request.setTaskId(taskId);
        ResourceWorkOrderService service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        service.patrolDetial(request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                String jsonStr = new Gson().toJson(data);
                PatrolInfoTypeConvert convert = new PatrolInfoTypeConvert();
                PatrolInfo patrolInfo = convert.stringToSomeObject(jsonStr);
                patrolInfo.setUserId(userModuleService.getUserId());
                patrolInfo.setTaskId(taskId);
                if(patrolInfo==null){
                    PatrolLocal patrolLocal = new PatrolLocal();
                    patrolLocal.setTaskId(taskId);
                    patrolLocal.setUserId(userModuleService.getUserId());
                    repo.saveLocalData(patrolLocal);
                }
                repo.updatePatrolCached(taskId);
                repo.insertPatrolInfo(patrolInfo);
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
     * 保存巡查
     */
    public void save(PatrolInfo info) {
        repo.savePatrolInfo(info);
    }


    /**
     * 获取用户本地输入数据
     *
     * @param taskId
     * @return
     */
    public LiveData<PatrolLocal> loadLocalUserData(String taskId) {
        MutableLiveData<PatrolLocal> liveData=new MutableLiveData<>();
         repo.loadLocalUserData(taskId, new CallBack<PatrolLocal>() {
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

    /**
     * 上传图片
     *
     * @param allSelectedPhotos
     * @return
     */
    public LiveData<List<PicUrl>> uploadImages(List<Uri> allSelectedPhotos) {
        MutableLiveData<List<PicUrl>> uploadState = new MutableLiveData<>();
        List<Uri> todoUploadUris = filterUris(allSelectedPhotos);
        // 如果所有照片已经被上传过，则直接回调
        if (allSelectedPhotos.size() == uploadedImages.size()) {
            uploadState.postValue(new ArrayList<>());
            return uploadState;
        }

        showLoading();
        try {
            uploadManager.upload(todoUploadUris, new CallBack<List<PicUrl>>() {
                @Override
                public void call(List<PicUrl> data) {
                    for (PicUrl picUrl : data) {
                        if (TextUtils.isEmpty(picUrl.getOriginUrl())) {
                            uploadedImages.put(picUrl.getOriginUrl(), picUrl.getUploaded());
                        }
                    }
                    hideLoading();
                    uploadState.postValue(data);
                }

                @Override
                public void onFaild(Throwable throwable) {
                    uploadState.postValue(null);
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            uploadState.postValue(null);
        }
        return uploadState;
    }

}
