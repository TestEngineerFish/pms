package com.einyun.app.pms.repairs.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.repairs.repository.DataSourceFactory;

/**
 *RepairsViewModel
 */
public class RepairsViewModel extends BasePageListViewModel<RepairsModel> {
    // TODO: Implement the ViewModel

    LiveData<PagedList<RepairsModel>> liveData;
    RepairsPageRequest request;
    public void refresh(){
        if(liveData!=null){
        }
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
    public LiveData<PagedList<RepairsModel>> loadPagingData(RepairsPageRequest repairsPageRequest){
            liveData= new LivePagedListBuilder(new DataSourceFactory(repairsPageRequest), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                    .build();
        return liveData;
    }

    /**
     * 抢单
     * */
//    public

    /**
     * 抢单
     * */
}
