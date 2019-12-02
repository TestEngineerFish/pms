package com.einyun.app.patrol.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.patrol.repository.PatrolBoundaryCallBack;
import com.einyun.app.patrol.repository.PatrolListRepo;

public class PatrolListViewModel extends BasePageListViewModel<Patrol> {

    PatrolListRepo repo=new PatrolListRepo();
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<Patrol>> loadPadingData(PatrolPageRequest request){
        if(pageList ==null){
            pageList = new LivePagedListBuilder(repo.queryAll(), config)
                .setBoundaryCallback(new PatrolBoundaryCallBack(request))
//                .setFetchExecutor(null)
                    .build();
        }
        return pageList;
    }

}
