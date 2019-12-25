package com.einyun.app.pms.mine.viewmodule;

import android.net.Uri;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.mine.model.GetUserByccountBean;
import com.einyun.app.pms.mine.repository.FeedBackRepository;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserHeadShotViewModel extends BaseViewModel {
    private ImageUploadManager uploadManager=new ImageUploadManager();
    private final Map<String, String> uploadedImages = new ConcurrentHashMap<>();
    FeedBackRepository repository=new FeedBackRepository();

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
    public LiveData<Boolean> create(GetUserByccountBean request, List<PicUrl> images) {
        if (uploadManager != null) {
            JSONObject jsonObject = JSON.parseObject(images.get(0).getUploaded());
//            getUserByccountBean.setPhoto(jsonObject.getString("filePath"));
            String filePath = jsonObject.getString("filePath");
            request.setPhoto(filePath);
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
