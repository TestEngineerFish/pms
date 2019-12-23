package com.einyun.app.pms.patrol.repository;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.repository.BaseBoundaryCallBack;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.convert.PatrolListTypeConvert;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * paging,data from network and db
 */
public class PatrolPendingBoundaryCallBack extends PagedList.BoundaryCallback<Patrol> {
    protected Lock lock = new ReentrantLock();
    protected PatrolListRepo patrolRepo;
    protected PatrolPageRequest request;
    protected int listType = ListType.PENDING.getType();
    protected ResourceWorkOrderService service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);

    public PatrolPendingBoundaryCallBack(PatrolPageRequest request) {
        super();
        patrolRepo = new PatrolListRepo();
        this.request = request;
    }

    public void refresh() {
        loadData(BaseBoundaryCallBack.DATA_TYPE_SYNC);
    }

    public void search(String key){

    }

    /**
     * 数据初始化
     */
    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        loadData(BaseBoundaryCallBack.DATA_TYPE_INIT);
    }

    protected void loadData(final int dataType) {
        service.patrolWaitPage(request, new CallBack<PatrolWorkOrderPage>() {
            @Override
            public void call(PatrolWorkOrderPage data) {
                if (data.isEmpty()) {
                    clearAll();
                }
                PatrolListTypeConvert convert = new PatrolListTypeConvert();
                List<Patrol> patrols = convert.stringToSomeObject(new Gson().toJson(data.getRows()));
                wrapList(patrols);
                if (dataType == BaseBoundaryCallBack.DATA_TYPE_INIT) {
                    patrolRepo.initData(patrols, request.getUserId(), listType);
                } else if (dataType == BaseBoundaryCallBack.DATA_TYPE_SYNC) {
                    //同步数据
                    patrolRepo.sync(patrols, request.getUserId(), listType, null);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
            }
        });
    }

    public void clearAll() {
        patrolRepo.clearAll(request.getUserId(), listType);
    }

    /**
     * 包装userId
     *
     * @param patrols
     */
    protected void wrapList(List<Patrol> patrols) {
        for (Patrol patrol : patrols) {
            patrol.setListType(listType);
            patrol.setUserId(request.getUserId());
        }
    }

}
