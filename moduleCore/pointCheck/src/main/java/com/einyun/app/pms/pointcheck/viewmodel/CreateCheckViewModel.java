package com.example.shimaostaff.pointcheck.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.net.Uri;
import android.util.Log;

import com.example.shimaostaff.http.CallBack;
import com.example.shimaostaff.pointcheck.model.ProjectContentModel;
import com.example.shimaostaff.pointcheck.model.ProjectModel;
import com.example.shimaostaff.pointcheck.model.State;
import com.example.shimaostaff.pointcheck.net.request.CreatePointCheckRequest;
import com.example.shimaostaff.pointcheck.repository.CreateCheckRepository;
import com.example.shimaostaff.tools.ToastUtil;
import com.example.shimaostaff.tools.UploadUtil;
import com.example.shimaostaff.view.MyApp;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.viewmodel
 * @ClassName: CreateCheckViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 15:21
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 15:21
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CreateCheckViewModel extends ViewModel {
    private final String TAG="CreateCheckViewModel";
    private final Map<Uri, String> uploadedImages = new ConcurrentHashMap<>();
    private CreateCheckRepository repository=new CreateCheckRepository();
    public MutableLiveData<List<ProjectModel>> projects=new MutableLiveData<>();
    public MutableLiveData<List<String>> projectItems=new MutableLiveData<>();
    public MutableLiveData<State> state=new MutableLiveData<>();

    /**
     * 获取地块下面的点检事项
     * @param ids
     * @return
     */
    public MutableLiveData<List<String>> loadProjects(String ids){
        repository.projects(ids, new CallBack<List<ProjectModel>>() {
            @Override
            public void call(List<ProjectModel> data) {
                projectItems.postValue(loadProjectItems(data));
                projects.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
        return projectItems;
    }

    /**
     * 根据点检事项名称获取点检内容
     * @param projectName
     * @return
     */
    public LiveData<ProjectContentModel> loadProjectContent(String projectName){
        String projectId=loadProjectIdByName(projectName);
        MutableLiveData<ProjectContentModel> liveData=new MutableLiveData<>();
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
     * @param projectName
     * @return
     */
    public String loadProjectIdByName(String projectName){
        String projectId=null;
        List<ProjectModel> list=projects.getValue();
        for(ProjectModel model:list){
            if(model.getCheckName().trim().equals(projectName.trim())){
                return model.getId();
            }
        }
        return projectId;
    }

    /**
     * 获取点检事项所有的检测事项
     * @param data
     * @return
     */
    private List<String> loadProjectItems(List<ProjectModel> data) {
        List<String> list=new ArrayList<>();
        if(data!=null){
            for(ProjectModel model:data){
                list.add(model.getCheckName());
            }
        }
        return list;
    }

    public LiveData<Boolean> uploadImages(List<Uri> allSelectedPhotos){
        MutableLiveData<Boolean> uploadState=new MutableLiveData<>();
        List<Uri> todoUploadUris = new ArrayList<>();

        // 根据当前已选判断哪些图片是未上传的
        for (Uri selectedPhoto : allSelectedPhotos) {
            if (!uploadedImages.keySet().contains(selectedPhoto)) {
                todoUploadUris.add(selectedPhoto);
            }
        }

        // 删除缓存中已经上传但已经被删除的图片
        for (Uri uploadedUri : uploadedImages.keySet()) {
            if (!allSelectedPhotos.contains(uploadedUri)) {
                uploadedImages.remove(uploadedUri);
            }
        }
        // 如果所有照片已经被上传过，则直接回调
        if (allSelectedPhotos.size() == uploadedImages.size()) {
            uploadState.postValue(true);
            return uploadState;
        }

        state.postValue(State.SHOWLOADING);
        for (Uri value : todoUploadUris) {
            UploadUtil.uploadImageBackAll(MyApp.get(), value, new UploadUtil.UploadCallback() {
                @Override
                public void onSuccess(String imageUrl) {
                    Log.e(TAG, "imageUrl = " + imageUrl);
                    uploadedImages.put(value, imageUrl);
                    // 所有图片上传完毕
                    if (allSelectedPhotos.size() == uploadedImages.size()) {
                        state.postValue(State.HIDELOADING);
                        uploadState.postValue(true);
                    }
                }

                @Override
                public void onFailed() {
                    state.postValue(State.HIDELOADING);
                    ToastUtil.show("提交图片失败，请重试");
                }
            });
        }
        return uploadState;
    }

    /**
     * 新增点检
     * @param request
     * @return
     */
    public LiveData<Boolean> create(CreatePointCheckRequest request){
       request.setPicUrl(picUrlJson());
        state.postValue(State.SHOWLOADING);
        return repository.create(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                state.postValue(State.HIDELOADING);
            }

            @Override
            public void onFaild(Throwable throwable) {
                state.postValue(State.HIDELOADING);
            }
        });
    }

    private String picUrlJson(){
        JSONArray jsonArray = new JSONArray();
        try {
            for (Uri uri : uploadedImages.keySet()) {
                String data = uploadedImages.get(uri).replace("fileId", "id").replace("fileName", "name").replace("filePath", "path");
                org.json.JSONObject json = new org.json.JSONObject(data);
                jsonArray.put(json);
            }
            Log.d(TAG, "picUrl->" + jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}
