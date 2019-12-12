package com.einyun.app.pms.patrol.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.convert.PatrolListTypeConvert;
import com.einyun.app.pms.patrol.repository.DataSourceFactory;
import com.einyun.app.pms.patrol.repository.PatrolListRepo;
import com.google.gson.Gson;

import java.util.List;

public class PatrolListViewModel extends BasePageListViewModel<Patrol> {
    ResourceWorkOrderService service= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    PatrolListRepo repo=new PatrolListRepo();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    LiveData<PagedList<Patrol>> pageListClosed;
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<Patrol>> loadPendingData(PatrolPageRequest request){
        if(pageList ==null){
            pageList = new LivePagedListBuilder(repo.queryAll(userModuleService.getUserId()), config)
//                .setBoundaryCallback(new PatrolPendingBoundaryCallBack(request))
                    .build();
        }
        return pageList;
    }

    /**
     * 刷新数据
     * @param request
     */
    public void refresh(PatrolPageRequest request) {
        service.patrolWaitPage(request, new CallBack<PatrolWorkOrderPage>() {
            @Override
            public void call(PatrolWorkOrderPage data) {
                PatrolListTypeConvert convert=new PatrolListTypeConvert();
                List<Patrol> patrols=convert.stringToSomeObject(new Gson().toJson(data.getRows()));
                for(Patrol patrol:patrols){
                    patrol.setUserId(userModuleService.getUserId());
                }
                //同步数据
                repo.sync(patrols,userModuleService.getUserId(), new CallBack<Boolean>() {
                    @Override
                    public void call(Boolean data) {
                        //刷新列表
//                        refresh();
                    }

                    @Override
                    public void onFaild(Throwable throwable) {

                    }
                });
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }


    /**
     * 刷新已关闭列表
     */
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
