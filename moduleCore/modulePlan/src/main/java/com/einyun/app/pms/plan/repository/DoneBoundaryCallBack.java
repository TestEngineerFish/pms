package com.einyun.app.pms.plan.repository;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.model.ListType;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;

public class DoneBoundaryCallBack extends PendingBoundaryCallBack {
    public DoneBoundaryCallBack(DistributePageRequest request) {
        super(request);
        listType= ListType.DONE.getType();
    }

    @Override
    protected void loadData(int dataType, CallBack<Integer> callBack) {
        workOrderService.planClosedPage((DistributePageRequest) request, new CallBack<PlanWorkOrderPage>() {
            @Override
            public void call(PlanWorkOrderPage data) {
                onDataLoaded(dataType,listType,data,callBack);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }
}
