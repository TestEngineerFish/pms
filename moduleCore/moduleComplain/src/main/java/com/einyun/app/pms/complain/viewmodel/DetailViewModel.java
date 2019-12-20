package com.einyun.app.pms.complain.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.PostCommunicationRequest;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.complain.repository.DataSourceFactory;

import java.util.List;

public class DetailViewModel extends BaseUploadViewModel {
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
