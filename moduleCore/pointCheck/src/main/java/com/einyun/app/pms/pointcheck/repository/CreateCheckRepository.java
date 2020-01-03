package com.einyun.app.pms.pointcheck.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.pointcheck.model.ProjectContentModel;
import com.einyun.app.pms.pointcheck.model.ProjectModel;
import com.einyun.app.pms.pointcheck.net.CheckPointServiceApi;
import com.einyun.app.pms.pointcheck.net.URLs;
import com.einyun.app.pms.pointcheck.net.request.CreatePointCheckRequest;

import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.repository
 * @ClassName: CreateCheckRepository
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 15:22
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 15:22
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CreateCheckRepository {
    CheckPointServiceApi serviceApi;

    public CreateCheckRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(CheckPointServiceApi.class);
    }

    /**
     * 获取地块下点检事项集
     * @param ids
     * @param callBack
     * @return
     */
    public LiveData<List<ProjectModel>> projects(String ids, CallBack<List<ProjectModel>> callBack) {
        MutableLiveData<List<ProjectModel>> liveData = new MutableLiveData<>();
        serviceApi.projects(ids).compose(RxSchedulers.inIoMain()).subscribe(
                response -> {
                    if (response.isState()) {
                        callBack.call(response.getData());
                    }
                }, throwable -> {
                    callBack.onFaild(throwable);
                }
        );
        return liveData;
    }

    /**
     * 获取点检内容
     * @param projectId
     * @param callBack
     * @return
     */
    public LiveData<ProjectContentModel> projectContent(String projectId, CallBack<ProjectContentModel> callBack) {
        MutableLiveData<ProjectContentModel> liveData = new MutableLiveData<>();
        String url = URLs.URL_POINT_CHECK_PROJECT_CONTENT + projectId;
        serviceApi.projectContent(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if (response.isState()) {
                        callBack.call(response.getData());
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }

    /**
     * 新增点检
     * @param request
     * @param callBack
     * @return
     */
    public LiveData<Boolean> create(CreatePointCheckRequest request, CallBack<Boolean> callBack) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        serviceApi.create(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    liveData.postValue(response.isState());
                    callBack.call(response.isState());
                }, error -> {
                    callBack.onFaild(error);
                });
        return liveData;
    }
}
