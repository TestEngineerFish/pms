package com.einyun.app.pms.repairs.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.pms.repairs.repository.DataSourceFactory;

/**
 *RepairsViewModel
 */
public class RepairsViewModel extends BaseViewModel {
    // TODO: Implement the ViewModel
    PagedList.Config config = new PagedList.Config.Builder()
            .setPageSize(PageBean.DEFAULT_PAGE_SIZE)                         //配置分页加载的数量
            .setEnablePlaceholders(false)     //配置是否启动PlaceHolders
            .setInitialLoadSizeHint(PageBean.DEFAULT_PAGE_SIZE)              //初始化加载的数量
            .build();
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
