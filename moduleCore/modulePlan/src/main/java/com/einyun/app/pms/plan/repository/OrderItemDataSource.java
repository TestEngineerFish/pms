package com.einyun.app.pms.plan.repository;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;

public class OrderItemDataSource extends BaseDataSource<DistributeWorkOrder> {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    private DistributePageRequest request;
    ResourceWorkOrderRepo repository;
    private String tag;

    public OrderItemDataSource(DistributePageRequest request,String tag) {
        this.request = request;
        this.tag=tag;
        repository = new ResourceWorkOrderRepo();
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        request.setPage(pageBean.getPage());
        request.setPageSize(pageBean.getPageSize());
        request.setShowTotal(true);
        if (tag.equals(RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)) {
            request.setPage(pageBean.getPage());
            request.setPageSize(pageBean.getPageSize());
            //代办
            loadPending(callback);
        } else {
            //已办
            loadDone(callback);
        }
    }

    /**
     * 获取代办列表
     * @param callback
     * @param <T>
     */
    protected <T> void loadPending(@NonNull T callback) {
        repository.planWaitPage(request, new CallBack<PlanWorkOrderPage>() {
            @Override
            public void call(PlanWorkOrderPage data) {
                LiveDataBusUtils.postStopRefresh();
                if (callback instanceof PositionalDataSource.LoadInitialCallback) {
                    LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                } else if (callback instanceof PositionalDataSource.LoadRangeCallback) {
                    LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
                LiveDataBusUtils.postStopRefresh();
            }
        });
    }

    /**
     * 获取已办列表
     * @param callback
     * @param <T>
     */
    protected <T> void loadDone(@NonNull T callback) {
        repository.planClosedPage(request, new CallBack<PlanWorkOrderPage>() {
            @Override
            public void call(PlanWorkOrderPage data) {
                LiveDataBusUtils.postStopRefresh();
                if (callback instanceof LoadInitialCallback) {
                    LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                } else if (callback instanceof LoadRangeCallback) {
                    LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                LiveDataBusUtils.postStopRefresh();
                ThrowableParser.onFailed(throwable);
            }
        });
    }
}
