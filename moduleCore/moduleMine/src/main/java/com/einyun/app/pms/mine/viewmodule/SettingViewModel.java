package com.einyun.app.pms.mine.viewmodule;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.library.core.api.MdmService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.mdm.model.FeedBackListModel;
import com.einyun.app.library.mdm.net.request.FeedBackAddRequest;
import com.einyun.app.library.upload.model.PicUrl;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SettingViewModel extends BaseViewModel {
    private final Map<String, String> uploadedImages = new ConcurrentHashMap<>();
    private ImageUploadManager uploadManager = new ImageUploadManager();
    MdmService mdmService;

    public SettingViewModel() {
        this.mdmService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_MDM);
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
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
            Uri uploadedUri = Uri.fromFile(new File(uploadeUrl));
            if (!allSelectedPhotos.contains(uploadedUri)) {
                uploadedImages.remove(uploadedUri);
            }
        }
        return todoUploadUris;
    }
/**
* 新增意见反馈
* */
    public LiveData<Object> addFeedBack(FeedBackAddRequest request) {
        MutableLiveData<Object> liveData = new MutableLiveData<>();
        showLoading();
        mdmService.addFeedBack(request, new CallBack<Object>() {
            @Override
            public void call(Object data) {
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
     * 获取意见反馈列表
     * */
    public LiveData<List<FeedBackListModel>> getFeedBackList(String userId) {
        MutableLiveData<List<FeedBackListModel>> liveData = new MutableLiveData<>();
        showLoading();
        mdmService.getFeedBackList(userId, new CallBack<List<FeedBackListModel>>() {

            @Override
            public void call(List<FeedBackListModel> feedBackListModels) {
                hideLoading();
                liveData.postValue(feedBackListModels);
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
     * 新增意见反馈
     * */
    public LiveData<FeedBackListModel> getFeedBackDetail(String feedId) {
        MutableLiveData<FeedBackListModel> liveData = new MutableLiveData<>();
        showLoading();
        mdmService.getFeedBackDetail(feedId, new CallBack<FeedBackListModel>() {
            @Override
            public void call(FeedBackListModel feedBackListModel) {
                hideLoading();
                liveData.postValue(feedBackListModel);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }


}
