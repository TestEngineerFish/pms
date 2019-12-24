package com.einyun.app.pms.complain.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.einyun.app.library.workorder.net.request.ComplainDetailCompleteRequest;
import com.einyun.app.library.workorder.net.request.PostCommunicationRequest;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.complain.repository.DataSourceFactory;

import java.util.List;

public class DetailViewModel extends BaseWorkOrderHandelViewModel {
    private WorkOrderService workOrderService;
    private DictService dictService;

    public DetailViewModel() {
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
        dictService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
    }

    public LiveData<Boolean> postCommunication(PostCommunicationRequest request) {
        showLoading();
        return workOrderService.postCommunication(request, new CallBack<Boolean>() {
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

    public LiveData<Boolean> complainDetailComplete(ComplainDetailCompleteRequest request){
        return workOrderService.complainDetailComplete(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public LiveData<Boolean> complainDetailSave(ComplainDetailCompleteRequest request){
        return workOrderService.complainDetailSave(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    /**
     * 报修详情
     */
    public LiveData<RepairsDetailModel> getComplainDetail(String instId, String taskId) {
        MutableLiveData<RepairsDetailModel> liveData = new MutableLiveData<>();
        showLoading();
        if (taskId == null)
            taskId = "";
        workOrderService.getClientOrderDetail(instId, taskId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }

    public LiveData<List<DictDataModel>> getByTypeKey(String typeKey) {
        return dictService.getByTypeKey(typeKey, new CallBack<List<DictDataModel>>() {
            @Override
            public void call(List<DictDataModel> data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

}
