package com.einyun.app.pms.sendorder.repository;

import com.einyun.app.base.db.converter.DistributeListConvert;
import com.einyun.app.base.db.dao.DistributeDao;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.CallBack;
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
    protected DistributeDao dao;
    protected ResourceWorkOrderService service;
    protected int orderType = Distribute.ORDER_TYPE_PENDING;

    public PendingBoundaryCallBack(DistributePageRequest request) {
        super(request);
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        dao = db.distributeDao();
    }

    public void refresh() {
        initData();
    }

    public void switchCondition() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll(request.getUserId(), orderType);
            }
        });
        initData();
    }

    /**
     * 加载数据
     *
     * @param dataType
     */
    protected void loadData(int dataType, CallBack<Integer> callBack) {
        lock.lock();
        request.setPage(pageBean.getPage());
        service.distributeWaitPage((DistributePageRequest) request, new CallBack<DistributeWorkOrderPage>() {
            @Override
            public void call(DistributeWorkOrderPage data) {
                if (data.hasNextPage()) {
                    callBack.call(data.nextPage());
                }
                lock.unlock();
                DistributeListConvert convert = new DistributeListConvert();
                List<Distribute> rows = convert.stringToSomeObjectList(new Gson().toJson(data.getRows()));
                if (rows.size() > 0) {
                    wrapList(rows);
                    persistence(rows, dataType);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                lock.unlock();
            }
        });
    }

    /**
     * 数据持久化
     *
     * @param rows
     * @param dataType
     */
    protected void persistence(List<Distribute> rows, int dataType) {
        db.runInTransaction(() -> {
            if (dataType == DATA_TYPE_INIT) {//初始化清空数据
                dao.deleteAll(request.getUserId(), orderType);
            }
            dao.insert(rows);//追加数据
        });

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
