package com.einyun.app.pms.create.viewmodel;

import android.net.Uri;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.workorder.model.ComplainModelPageResult;
import com.einyun.app.library.workorder.model.Door;
import com.einyun.app.library.workorder.model.DoorResult;
import com.einyun.app.library.workorder.model.TypeBigAndSmallModel;
import com.einyun.app.library.workorder.model.UserInfoByHouseIdModel;
import com.einyun.app.library.workorder.net.request.ComplainAppendRequest;
import com.einyun.app.library.workorder.net.request.CreateClientComplainOrderRequest;
import com.einyun.app.library.workorder.net.request.CreateClientEnquiryOrderRequest;
import com.einyun.app.library.resource.workorder.net.request.CreateSendOrderRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.einyun.app.library.workorder.net.request.CreateClientRepairOrderRequest;
import com.einyun.app.pms.create.viewmodel.contract.CreateViewModelContract;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务逻辑处理，UI交互
 */
public class CreateViewModel extends BaseViewModel implements CreateViewModelContract {
    private static final String TAG = CreateViewModel.class.getSimpleName();
    private DictService dictService;
    private WorkOrderService workOrderService;
    private ResourceWorkOrderService resourceWorkOrderService;
    private UserCenterService userCenterService;
    //    private LiveData<UserModel> mUserModel;
    private final Map<String, String> uploadedImages = new ConcurrentHashMap<>();
    private ImageUploadManager uploadManager = new ImageUploadManager();

    public CreateViewModel() {
        dictService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
        userCenterService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
        resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
    }


    @Override
    public LiveData<List<DictDataModel>> getByTypeKey(String typeKey) {
        MutableLiveData<List<DictDataModel>> liveData = new MutableLiveData<>();
        BasicDataManager.getInstance().loadBasicDataByTypeKey(new CallBack<List<DictDataModel>>() {
            @Override
            public void call(List<DictDataModel> data) {
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        }, typeKey);
        return liveData;
    }

    @Override
    public LiveData<List<DictDataModel>> getTypesListByKey(String typeKey) {
        MutableLiveData<List<DictDataModel>> liveData = new MutableLiveData<>();
        BasicDataManager.getInstance().loadBasicDataTypesListKey(new CallBack<List<DictDataModel>>() {
            @Override
            public void call(List<DictDataModel> data) {
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        }, typeKey);
        return liveData;
    }

    public LiveData<List<TypeAndLine>> typeAndLineList() {
        return workOrderService.typeAndLineList(new CallBack<List<TypeAndLine>>() {
            @Override
            public void call(List<TypeAndLine> data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
    public LiveData<TypeBigAndSmallModel> typeBigAndSmall() {
        return workOrderService.typeBigAndSmall(new CallBack<TypeBigAndSmallModel>() {
            @Override
            public void call(TypeBigAndSmallModel data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
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


    @Override
    public LiveData<Boolean> createSendOrder(CreateSendOrderRequest request, List<PicUrl> images) {
        if (uploadManager != null) {
            request.setImageList(uploadManager.toJosnString(images));
        }
        showLoading();
        return resourceWorkOrderService.createSendOrder(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public LiveData<Boolean> createClientEnquiryOrder(CreateClientEnquiryOrderRequest request, List<PicUrl> images) {
        if (uploadManager != null) {
            request.getBizData().setImageList(uploadManager.toJosnString(images));
        }
        showLoading();
        return workOrderService.startEnquiry(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public LiveData<Boolean> createClientComplainOrder(CreateClientComplainOrderRequest request, List<PicUrl> images) {
        if (uploadManager != null) {
            request.getBizData().setImageList(uploadManager.toJosnString(images));
        }
        showLoading();
        return workOrderService.startComplain(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public LiveData<Boolean> createClientRepairOrder(CreateClientRepairOrderRequest request, List<PicUrl> images) {
        if (uploadManager != null) {
            request.getBizData().setImageList(uploadManager.toJosnString(images));
        }
        showLoading();
        return workOrderService.startRepair(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public LiveData<ComplainModelPageResult> complainWorkListdPage(String mobile) {
        return workOrderService.complainWorkListdPage(new PageBean(PageBean.DEFAULT_PAGE, PageBean.MAX_PAGE_SIZE), mobile, new CallBack<ComplainModelPageResult>() {
            @Override
            public void call(ComplainModelPageResult data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public LiveData<Boolean> appendComplain(ComplainAppendRequest request) {
        showLoading();
        return workOrderService.appendComplain(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });
    }


    public LiveData<List<UserInfoByHouseIdModel>> getUserInfoByHouseId(String houseId) {
        return workOrderService.getUserInfoByHouseId(houseId, new CallBack<List<UserInfoByHouseIdModel>>() {
            @Override
            public void call(List<UserInfoByHouseIdModel> data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<List<ResourceTypeBean>> getResourceInfos(CreateSendOrderRequest request) {
        return resourceWorkOrderService.getResourceInfos(request.getDivideId(), request.getTxCode(), new CallBack<List<ResourceTypeBean>>() {
            @Override
            public void call(List<ResourceTypeBean> data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<List<OrgModel>> getDisposePerson(String orgId, String dimCode) {
        return userCenterService.getDisposePerson(orgId, dimCode, new CallBack<List<OrgModel>>() {
            @Override
            public void call(List<OrgModel> data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
    @Override
    public LiveData<List<OrgModel>> getCheckedPerson(String orgId) {
        return userCenterService.getCheckedPerson(orgId, new CallBack<List<OrgModel>>() {
            @Override
            public void call(List<OrgModel> data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
    /**
     * 获取报修类别与条线
     *
     * @return
     */
    public LiveData<Door> repairTypeList() {
        return workOrderService.repairTypeList(new CallBack<Door>() {
            @Override
            public void call(Door data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
}
