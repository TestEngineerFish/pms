package com.einyun.app.pms.repairs.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.pms.repairs.repository.DataSourceFactory;

/**
 *RepairsViewModel
 */
public class RepairsViewModel extends BasePageListViewModel {
    // TODO: Implement the ViewModel

    LiveData<PagedList<DictDataModel>> liveData;

    public void refresh(){
        if(liveData!=null){
            liveData.getValue().getDataSource().invalidate();
        }
    }
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<DictDataModel>> loadPadingData(){
        if(liveData==null){
            liveData= new LivePagedListBuilder(new DataSourceFactory(), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                    .build();
        }
        return liveData;
    }
}
