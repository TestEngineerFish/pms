package com.einyun.app.pms.patrol.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.repository.DataSourceFactory;
import com.einyun.app.pms.patrol.repository.PatrolPendingBoundaryCallBack;
import com.einyun.app.pms.patrol.repository.PatrolListRepo;

public class PatrolListViewModel extends BasePageListViewModel<Patrol> {

    PatrolListRepo repo=new PatrolListRepo();

    LiveData<PagedList<Patrol>> pageListClosed;
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<Patrol>> loadPendingData(PatrolPageRequest request){
        if(pageList ==null){
            pageList = new LivePagedListBuilder(repo.queryAll(), config)
                .setBoundaryCallback(new PatrolPendingBoundaryCallBack(request))
//                .setFetchExecutor(null)
                    .build();
        }
        return pageList;
    }

    public void refreshClosedList(){
        if(pageListClosed!=null){
            pageListClosed.getValue().getDataSource().invalidate();
        }
    }

    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<Patrol>> loadCloseData(PatrolPageRequest request){
        pageListClosed = new LivePagedListBuilder(new DataSourceFactory(request), config)
                    .build();
        return pageListClosed;
    }

}
