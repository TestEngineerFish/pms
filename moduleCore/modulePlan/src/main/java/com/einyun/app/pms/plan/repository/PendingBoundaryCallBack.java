package com.einyun.app.pms.plan.repository;

import com.einyun.app.base.db.converter.DistributeListConvert;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.db.entity.Plan;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageResult;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.repository.BaseBoundaryCallBack;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.pms.plan.convert.PlanListConvert;

import java.util.List;


public class PendingBoundaryCallBack extends BaseBoundaryCallBack<Plan> {

    protected ResourceWorkOrderService workOrderService;
    protected int listType= ListType.PENDING.getType();
    public PendingBoundaryCallBack(DistributePageRequest request) {
        super(request);
        repo=new PlanRepository();
        workOrderService= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    @Override
    protected void loadData(int dataType, CallBack<Integer> callBack) {
        workOrderService.planWaitPage((DistributePageRequest) request, new CallBack<PlanWorkOrderPage>() {
            @Override
            public void call(PlanWorkOrderPage data) {
                onDataLoaded(dataType,listType,data,callBack);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    @Override
    protected void clearAll() {
        repo.deleteAll(request.getUserId(),listType);
    }

    @Override
    protected void wrapList(List<Plan> list) {
        for(Plan plan:list){
            plan.setUserId(request.getUserId());
            plan.setListType(listType);
        }
    }

    @Override
    protected void onDataLoaded(int dataType,int listType, PageResult data, CallBack<Integer> callBack) {
        if(data.isEmpty()){
            clearAll();
        }
        if (data.hasNextPage()) {
            callBack.call(data.nextPage());
        }
        PlanListConvert convert = new PlanListConvert();
        List<Plan> rows = convert.stringToSomeObjectList(convert.getGson().toJson(data.getRows()));
        if (rows.size() > 0) {
            wrapList(rows);
            repo.persistence(rows,request.getUserId(),listType,dataType);
        }
    }
}
