package com.einyun.app.pms.sendorder.repository;

import com.einyun.app.base.db.converter.DistributeListConvert;
import com.einyun.app.base.db.dao.DistributeDao;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageResult;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.repository.BaseBoundaryCallBack;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.google.gson.Gson;

import java.util.List;

/**
 * 派工单代办数据源
 */
public class PendingBoundaryCallBack extends BaseBoundaryCallBack<Distribute> {
    protected ResourceWorkOrderService service;
    protected int orderType = Distribute.ORDER_TYPE_PENDING;

    public PendingBoundaryCallBack(DistributePageRequest request) {
        super(request);
        repo=new SendOrderRespository();
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }


    /**
     * 清空数据缓存
     */
    protected void clearAll() {
       repo.deleteAll(request.getUserId(),orderType);
    }

    /**
     * 加载数据
     *
     * @param dataType
     */
    protected void loadData(int dataType, CallBack<Integer> callBack) {
        service.distributeWaitPage((DistributePageRequest) request, new CallBack<DistributeWorkOrderPage>() {
            @Override
            public void call(DistributeWorkOrderPage data) {
                onDataLoaded(dataType,orderType,data,callBack);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    protected void onDataLoaded(int dataType,int orderType, PageResult data, CallBack<Integer> callBack) {
        if(data.isEmpty()){
            clearAll();
        }
        if (data.hasNextPage()) {
            callBack.call(data.nextPage());
        }
        DistributeListConvert convert = new DistributeListConvert();
        List<Distribute> rows = convert.stringToSomeObjectList(new Gson().toJson(data.getRows()));
        if (rows.size() > 0) {
            wrapList(rows);
            repo.persistence(rows,request.getUserId(),orderType,dataType);
        }
    }


    /**
     * 包装userId,代办/已办区分
     *
     * @param list
     */
    protected void wrapList(List<Distribute> list) {
        for (Distribute distribute : list) {
            distribute.setUserId(request.getUserId());
            distribute.setOrderType(orderType);
        }
    }

}
