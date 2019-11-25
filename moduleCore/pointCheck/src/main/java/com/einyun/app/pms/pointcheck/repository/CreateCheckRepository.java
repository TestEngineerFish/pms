package com.example.shimaostaff.pointcheck.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shimaostaff.http.CallBack;
import com.example.shimaostaff.http.HttpService;
import com.example.shimaostaff.http.RxSchedulers;
import com.example.shimaostaff.pointcheck.model.ProjectContentModel;
import com.example.shimaostaff.pointcheck.model.ProjectModel;
import com.example.shimaostaff.pointcheck.net.CheckPointServiceApi;
import com.example.shimaostaff.pointcheck.net.URLs;
import com.example.shimaostaff.pointcheck.net.request.CreatePointCheckRequest;

import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.repository
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
        serviceApi = HttpService.getInstance().getServiceApi(CheckPointServiceApi.class);
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
