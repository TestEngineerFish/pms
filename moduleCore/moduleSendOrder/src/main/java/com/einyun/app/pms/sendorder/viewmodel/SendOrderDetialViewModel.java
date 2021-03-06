package com.einyun.app.pms.sendorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.HistoryModel;
import com.einyun.app.library.resource.workorder.net.request.DistributeCheckRequest;
import com.einyun.app.library.resource.workorder.net.request.DistributeSubmitRequest;
import com.einyun.app.library.resource.workorder.net.request.DoneDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.ExtenDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.WorkOrderHanlerRequest;
import com.einyun.app.library.resource.workorder.net.request.formDataExten;
import com.einyun.app.library.resource.workorder.net.response.HistroyResponse;
import com.einyun.app.library.upload.model.PicUrl;

import java.util.List;

public class SendOrderDetialViewModel extends BaseWorkOrderHandelViewModel {
    ResourceWorkOrderService service;
    MutableLiveData<DisttributeDetialModel> workOrderLiveData = new MutableLiveData<>();

    public SendOrderDetialViewModel() {
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    public LiveData<DisttributeDetialModel> detial(String orderId) {
//        showLoading();
        service.distributeDetial(orderId, new CallBack<DisttributeDetialModel>() {
            @Override
            public void call(DisttributeDetialModel data) {
                workOrderLiveData.postValue(data);
//                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return workOrderLiveData;
    }

    /**
     * ??????????????????
     *
     * @param taskId
     * @return
     */
    public LiveData<DisttributeDetialModel> pendingDetial(String taskId) {
//        showLoading();
        service.distributeWaitDetial(taskId, new CallBack<DisttributeDetialModel>() {
            @Override
            public void call(DisttributeDetialModel data) {
                workOrderLiveData.postValue(data);
//                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
                workOrderLiveData.postValue(null);
            }
        });
        return workOrderLiveData;
    }

    /**
     * ??????????????????
     *
     * @param taskNodeId
     * @param proInsId
     * @return
     */
    public LiveData<DisttributeDetialModel> doneDetial(String taskNodeId, String proInsId) {
        DoneDetialRequest request = new DoneDetialRequest();
        request.setTaskNodeId(taskNodeId);
        request.setProInsId(proInsId);
//        showLoading();
        service.distributeDoneDetial(request, new CallBack<DisttributeDetialModel>() {
            @Override
            public void call(DisttributeDetialModel data) {
                workOrderLiveData.postValue(data);
//                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
                ThrowableParser.onFailed(throwable);
                workOrderLiveData.postValue(null);
            }
        });
        return workOrderLiveData;
    }

    /**
     * ??????
     *
     * @param taskId
     * @return
     */
    public LiveData<Boolean> takeOrder(String taskId) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        WorkOrderHanlerRequest request = new WorkOrderHanlerRequest();
        request.setTaskId(taskId);
        showLoading();
        service.distributeResponse(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
                liveData.postValue(false);
            }
        });
        return liveData;
    }

    /**
     * ????????????
     *
     * @param request
     * @return
     */
    public LiveData<Boolean> submit(DistributeSubmitRequest request) {
        showLoading();
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        service.distributeSubmit(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
                liveData.postValue(false);
            }
        });
        return liveData;
    }

    /**
     * ??????
     *
     * @param taskId
     * @return
     */
    public LiveData<Boolean> reply(String taskId) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        WorkOrderHanlerRequest request = new WorkOrderHanlerRequest();
        request.setTaskId(taskId);
        showLoading();
        service.distributeReply(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
                liveData.postValue(false);
            }
        });
        return liveData;
    }

    /**
     * ??????
     *
     * @param request
     * @return
     */
    public LiveData<Boolean> check(DistributeCheckRequest request) {
        showLoading();
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        service.distributeCheck(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
                liveData.postValue(false);
            }
        });
        return liveData;
    }

    public LiveData<BaseResponse<Object>> applyLatePlan(ExtenDetialRequest request, List<PicUrl> images) {
        showLoading();
        request.setApplyFiles(uploadManager.toJosnString(images));
        return service.planExten(request, new CallBack<BaseResponse<Object>>() {
            @Override
            public void call(BaseResponse<Object> data) {
                if (!"0".equals(data.getCode())) {
                    ToastUtil.show(BasicApplication.getInstance(), data.getMsg());
                }
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                hideLoading();
            }
        });
    }

    public LiveData<BaseResponse<Object>> applyLate(ExtenDetialRequest request, List<PicUrl> images) {
        showLoading();
        request.setApplyFiles(uploadManager.toJosnString(images));
        return service.exten(request, new CallBack<BaseResponse<Object>>() {
            @Override
            public void call(BaseResponse<Object> data) {
                if (!"0".equals(data.getCode())) {
                    ToastUtil.show(BasicApplication.getInstance(), data.getMsg());
                }
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                hideLoading();
            }
        });
    }

    /**
     * ??????????????????
     * @return
     */
    public LiveData<List<HistoryModel>> getHistory(String instId) {
        showLoading();
        MutableLiveData<List<HistoryModel>> histroyList=new MutableLiveData<>();

        service.getHistroy(instId, new CallBack<List<HistoryModel>>() {
            @Override
            public void call(List<HistoryModel> data) {
                hideLoading();
                histroyList.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                hideLoading();
            }
        });
        return histroyList;
    }

    public LiveData<formDataExten> getApplyDateInfo(String id){
        return service.getApplyDateInfo(id, new CallBack<formDataExten>() {
            @Override
            public void call(formDataExten data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                hideLoading();
            }
        });
    }

    public LiveData<Boolean> postApplyDateInfo(ExtenDetialRequest request, List<PicUrl> images){
        showLoading();
        request.setApplyFiles(uploadManager.toJosnString(images));
        request.getFormData().setAttachment(uploadManager.toJosnString(images));
        return service.postApplyDateInfo(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                hideLoading();
            }
        });
    }
}
