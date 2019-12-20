package com.einyun.app.pms.complain.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.ComplainPageRequest;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.complain.repository.DataSourceFactory;

/**
 * RepairsViewModel
 */
public class ComplainViewModel extends BasePageListViewModel<ComplainModel> {
    // TODO: Implement the ViewModel

    LiveData<PagedList<ComplainModel>> liveData;
    RepairsPageRequest request;

    public void refresh() {
        if (liveData != null) {
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
     *
     * @return LiveData
     */
    public LiveData<PagedList<ComplainModel>> loadPagingData(ComplainPageRequest request, String tag) {
        liveData = new LivePagedListBuilder(new DataSourceFactory(request, tag), config)
                .build();
        return liveData;
    }

}
