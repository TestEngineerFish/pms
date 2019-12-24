package com.einyun.app.pms.sendorder.viewmodel;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.db.bean.DelayExtensionApplication;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.resource.workorder.model.ApplyCloseModel;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.resource.workorder.net.request.ApplyCusCloseRequest;
import com.einyun.app.library.resource.workorder.net.response.ApplyCloseResponse;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.sendorder.model.ImageDataBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ApplyCloseViewModel extends BaseUploadViewModel {
    private ResourceWorkOrderRepo resourceWorkOrderRepo;
    private ImageUploadManager uploadManager = new ImageUploadManager();

    public ApplyCloseViewModel() {
        resourceWorkOrderRepo = new ResourceWorkOrderRepo();
    }
    /**
     * 申请延期 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<ApplyCloseResponse> applyClosePlan(ApplyCloseRequest request, List<PicUrl> images) {
        if (uploadManager != null) {
            request.setApplyFiles(uploadManager.toJosnString(images));
        }
        showLoading();
        MutableLiveData<ApplyCloseResponse> resend=new MutableLiveData<>();
        resourceWorkOrderRepo.planApplyClose(request,new CallBack<ApplyCloseResponse>() {
            @Override
            public void call(ApplyCloseResponse data) {
                hideLoading();
                resend.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });

        return resend;
    }
    /**
     * 申请延期 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<ApplyCloseResponse> applyClose(ApplyCloseRequest request, List<PicUrl> images) {
        if (uploadManager != null) {
            request.setApplyFiles(uploadManager.toJosnString(images));
        }
        showLoading();
        MutableLiveData<ApplyCloseResponse> resend=new MutableLiveData<>();
        resourceWorkOrderRepo.applyClose(request,new CallBack<ApplyCloseResponse>() {
            @Override
            public void call(ApplyCloseResponse data) {
                hideLoading();
                resend.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });

        return resend;
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
    @Override
    public List<Uri> filterUris(List<Uri> allSelectedPhotos) {
        return super.filterUris(allSelectedPhotos);
    }
    /**
     * 客户问询强制关闭 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<ApplyCloseResponse> applyCustomerClose(ApplyCusCloseRequest request,String midUrl, List<PicUrl> images) {
        if (uploadManager != null) {
            request.getBizData().setFclose_apply_attach(uploadManager.toJosnString(images));

        }
//        Log.e("", "applyCustomerClose: "+new Gson().toJson(ApplyCusCloseRequest.class) );
        showLoading();
        MutableLiveData<ApplyCloseResponse> resend=new MutableLiveData<>();
        resourceWorkOrderRepo.applyCustomerClose(request,midUrl,new CallBack<ApplyCloseResponse>() {
            @Override
            public void call(ApplyCloseResponse data) {
                hideLoading();
                resend.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });

        return resend;
    }
}
