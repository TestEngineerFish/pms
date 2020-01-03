package com.einyun.app.pms.repairs.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.repairs.repository.DataSourceFactory;

/**
 *RepairsViewModel
 */
public class RepairsViewModel extends BasePageListViewModel<RepairsModel> {
    // TODO: Implement the ViewModel
    private WorkOrderService workOrderService;
    LiveData<PagedList<RepairsModel>> liveData;
    RepairsPageRequest request;
    public void refresh(){
        if(liveData!=null){
        }
    }

    public RepairsViewModel() {
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);

    }

    public RepairsPageRequest getRequest() {
        return request;
    }

    public void setRequest(RepairsPageRequest request) {
        this.request = request;
    }

    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<RepairsModel>> loadPagingData(RepairsPageRequest repairsPageRequest,String tag){
            liveData= new LivePagedListBuilder(new DataSourceFactory(repairsPageRequest,tag), config)
                    .build();
        return liveData;
    }

    /**
     * 抢单
     * */
    public LiveData<Boolean> grabRepair(String taskId){
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        showLoading();
        workOrderService.grabRepair(taskId, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                liveData.postValue(false);
            }
        });
        return liveData;
    }

}
