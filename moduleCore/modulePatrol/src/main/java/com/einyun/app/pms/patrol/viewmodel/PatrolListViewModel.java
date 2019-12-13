package com.einyun.app.pms.patrol.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.convert.PatrolListTypeConvert;
import com.einyun.app.pms.patrol.repository.DataSourceFactory;
import com.einyun.app.pms.patrol.repository.DoneBoundaryCallBack;
import com.einyun.app.pms.patrol.repository.PatrolListRepo;
import com.einyun.app.pms.patrol.repository.PatrolPendingBoundaryCallBack;
import com.google.gson.Gson;

import java.util.List;

public class PatrolListViewModel extends BasePageListViewModel<Patrol> {
    PatrolListRepo repo = new PatrolListRepo();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    LiveData<PagedList<Patrol>> pageListClosed;
    public PatrolPageRequest request;
    PatrolPendingBoundaryCallBack patrolPendingBoundaryCallBack;
    DoneBoundaryCallBack doneBoundaryCallBack;


    public PatrolListViewModel() {
        request = new PatrolPageRequest();
        request.setPageSize(PageBean.MAX_PAGE_SIZE);
        request.setUserId(userModuleService.getUserId());
    }

    /**
     * 刷新数据
     *
     */
    public void refresh() {
        if(patrolPendingBoundaryCallBack!=null){
            patrolPendingBoundaryCallBack.refresh();
        }
    }


    /**
     * 刷新已关闭列表
     */
    public void refreshClosedList() {
        if (doneBoundaryCallBack != null) {
            doneBoundaryCallBack.refresh();
        }
    }

    /**
     * 获取Paging LiveData
     *
     * @return LiveData
     */
    public LiveData<PagedList<Patrol>> loadPendingData() {
        if (patrolPendingBoundaryCallBack == null) {
            patrolPendingBoundaryCallBack = new PatrolPendingBoundaryCallBack(request);
        }
        if (pageList == null) {
            pageList = new LivePagedListBuilder(repo.queryAll(request.getUserId(), ListType.PENDING.getType()), config)
                    .setBoundaryCallback(patrolPendingBoundaryCallBack)
                    .build();
        }
        return pageList;
    }

    /**
     * 获取Paging LiveData
     *
     * @return LiveData
     */
    public LiveData<PagedList<Patrol>> loadCloseData() {
        if (doneBoundaryCallBack == null) {
            doneBoundaryCallBack = new DoneBoundaryCallBack(request);
        }
        if (pageListClosed == null) {
            pageListClosed = new LivePagedListBuilder(repo.queryAll(request.getUserId(), ListType.DONE.getType()), config)
                    .setBoundaryCallback(doneBoundaryCallBack)
                    .build();
        }
        return pageListClosed;
    }

}
