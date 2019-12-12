package com.einyun.app.pms.sendorder.repository;


import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.converter.DistributeListConvert;
import com.einyun.app.base.db.dao.DistributeDao;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.google.gson.Gson;

import java.util.List;

public class OrderItemDataSource extends BaseDataSource<Distribute> {

    AppDatabase db;
    DistributeDao dao;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    private DistributePageRequest request;
    ResourceWorkOrderRepo repository = new ResourceWorkOrderRepo();
    private String tag;

    private List<Distribute> loadPage(int current, int pageSize, int orderType) {
        return dao.page(current, pageSize, userModuleService.getUserId(), orderType);
    }

    public OrderItemDataSource(DistributePageRequest request, String tag) {
        db = AppDatabase.getInstance(CommonApplication.getInstance());
        dao = db.distributeDao();
        ARouter.getInstance().inject(this);
        this.request = request;
        this.tag = tag;
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        request.setPage(pageBean.getPage());
        request.setPageSize(pageBean.getPageSize());
        int type = Distribute.ORDER_TYPE_PENDING;
        if (tag.equals(RouteKey.FRAGMENT_SEND_OWRKORDER_DONE)) {
            type = Distribute.ORDER_TYPE_DONE;
        }
        List<Distribute> rows = loadPage(pageBean.current(), pageBean.getPageSize(), type);
        if (callback instanceof PositionalDataSource.LoadInitialCallback) {
            LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
            loadInitialCallback.onResult(rows, 0, rows.size());
        } else if (callback instanceof PositionalDataSource.LoadRangeCallback) {
            LoadRangeCallback loadRangeCallback = (LoadRangeCallback) callback;
            loadRangeCallback.onResult(rows);
            if (tag.equals(RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING)) {
                handlePending();
            } else {
                handleDone();
            }
        }
    }

    /**
     * 代办处理
     *
     */
    protected void handlePending() {
        //代办
        repository.distributeWaitPage(request, new CallBack<DistributeWorkOrderPage>() {
            @Override
            public void call(DistributeWorkOrderPage data) {
                DistributeListConvert convert = new DistributeListConvert();
                List<Distribute> rows = convert.stringToSomeObjectList(new Gson().toJson(data.getRows()));
                wrapList(rows, Distribute.ORDER_TYPE_PENDING);
                db.runInTransaction(() -> {
                    dao.insert(rows);
                });

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    /**
     * 已办数据
     *
     */
    protected void handleDone() {
        repository.distributeDonePage(request, new CallBack<DistributeWorkOrderPage>() {
            @Override
            public void call(DistributeWorkOrderPage data) {
                DistributeListConvert convert = new DistributeListConvert();
                List<Distribute> rows = convert.stringToSomeObjectList(new Gson().toJson(data.getRows()));
                wrapList(rows, Distribute.ORDER_TYPE_PENDING);
                db.runInTransaction(() -> {
                    dao.insert(rows);
                });
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    private void wrapList(List<Distribute> list, int orderType) {
        for (Distribute distribute : list) {
            distribute.setUserId(userModuleService.getUserId());
            distribute.setOrderType(orderType);
        }
    }
}
