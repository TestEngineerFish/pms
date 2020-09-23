package com.einyun.app.pms.pointcheck.viewmodel;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.pointcheck.model.ProjectContentModel;
import com.einyun.app.pms.pointcheck.model.ProjectModel;
import com.einyun.app.pms.pointcheck.net.request.CreatePointCheckRequest;
import com.einyun.app.pms.pointcheck.repository.CreateCheckRepository;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.viewmodel
 * @ClassName: CreateCheckViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 15:21
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 15:21
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CreateCheckViewModel extends BaseViewModel {
    private final String TAG = "CreateCheckViewModel";
    private final Map<String, String> uploadedImages = new ConcurrentHashMap<>();
    private CreateCheckRepository repository = new CreateCheckRepository();
    public MutableLiveData<List<ProjectModel>> projects = new MutableLiveData<>();
    public MutableLiveData<List<String>> projectItems = new MutableLiveData<>();
    private ImageUploadManager uploadManager=new ImageUploadManager();

    /**
     * 获取地块下面的点检事项
     *
     * @param ids
     * @return
     */
    public MutableLiveData<List<String>> loadProjects(String ids) {
        repository.projects(ids, new CallBack<List<ProjectModel>>() {
            @Override
            public void call(List<ProjectModel> data) {
                List<String> strings = loadProjectItems(data);
                if (strings.size()==0) {
                    ToastUtil.show(CommonApplication.getInstance(), "该园区下无点检事项");
                }else {
                    projectItems.postValue(loadProjectItems(data));
                    projects.postValue(data);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
        return projectItems;
    }

    /**
     * 根据点检事项名称获取点检内容
     *
     * @param projectName
     * @return
     */
    public LiveData<ProjectContentModel> loadProjectContent(String projectName) {
        String projectId = loadProjectIdByName(projectName);
        MutableLiveData<ProjectContentModel> liveData = new MutableLiveData<>();
        repository.projectContent(projectId, new CallBack<ProjectContentModel>() {
            @Override
            public void call(ProjectContentModel data) {
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
        return liveData;
    }

    /**
     * 通过点检事项名称获取点检事项ID
     *
     * @param projectName
     * @return
     */
    public String loadProjectIdByName(String projectName) {
        String projectId = null;
        List<ProjectModel> list = projects.getValue();
        for (ProjectModel model : list) {
            if (model.getCheckName().trim().equals(projectName.trim())) {
                return model.getId();
            }
        }
        return projectId;
    }

    /**
     * 获取点检事项所有的检测事项
     *
     * @param data
     * @return
     */
    private List<String> loadProjectItems(List<ProjectModel> data) {
        List<String> list = new ArrayList<>();
        if (data != null) {
            for (ProjectModel model : data) {
                list.add(model.getCheckName());
            }
        }
        return list;
    }

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
                    for(PicUrl picUrl:data){
                        if(TextUtils.isEmpty(picUrl.getOriginUrl())){
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

    @NotNull
    private List<Uri> filterUris(List<Uri> allSelectedPhotos) {
        List<Uri> todoUploadUris = new ArrayList<>();

        // 根据当前已选判断哪些图片是未上传的
        for (Uri selectedPhoto : allSelectedPhotos) {
            if (!uploadedImages.keySet().contains(selectedPhoto.toString())) {
                todoUploadUris.add(selectedPhoto);
            }
        }

        // 删除缓存中已经上传但已经被删除的图片
        for (String uploadeUrl : uploadedImages.keySet()) {
            Uri uploadedUri=Uri.fromFile(new File(uploadeUrl));
            if (!allSelectedPhotos.contains(uploadedUri)) {
                uploadedImages.remove(uploadedUri);
            }
        }
        return todoUploadUris;
    }

    /**
     * 新增点检
     *
     * @param request
     * @return
     */
    public LiveData<Boolean> create(CreatePointCheckRequest request, List<PicUrl> images) {
        if (uploadManager != null) {
            request.setPicUrl(uploadManager.toJosnString(images));
        }
        showLoading();
        return repository.create(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
    }


}
