package com.einyun.app.pms.patrol.repository;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.convert.PatrolListTypeConvert;
import com.google.gson.Gson;
import java.util.List;

/**
 * paging,data from network and db
 */
public class PatrolPendingBoundaryCallBack extends PagedList.BoundaryCallback<Patrol> {

    PatrolListRepo patrolRepo;
    PatrolPageRequest request;
    ResourceWorkOrderService service= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    public PatrolPendingBoundaryCallBack(PatrolPageRequest request) {
        super();
        patrolRepo=new PatrolListRepo();
        this.request=request;
    }

    /**
     * 数据初始化
     */
    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
//        service.patrolWaitPage(request, new CallBack<PatrolWorkOrderPage>() {
//            @Override
//            public void call(PatrolWorkOrderPage data) {
//                PatrolListTypeConvert convert=new PatrolListTypeConvert();
//                List<Patrol> patrols=convert.stringToSomeObject(new Gson().toJson(data.getRows()));
//                patrolRepo.initData(patrols);
//            }
//
//            @Override
//            public void onFaild(Throwable throwable) {
//
//            }
//        });
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Patrol itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Patrol itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
    }
}
