package com.einyun.app.pms.sendorder.repository;

import android.nfc.Tag;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

public class OrderItemDataSource extends BaseDataSource<DistributeWorkOrder> {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    private DistributePageRequest request;
    private String tag;

    public OrderItemDataSource(DistributePageRequest request,String tag) {
        this.request = request;
        this.tag=tag;
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        Logger.d(request.getTypeRe());
        request.setPage(pageBean.getPage());
        request.setPageSize(pageBean.getPageSize());
        ResourceWorkOrderRepo repository = new ResourceWorkOrderRepo();
        if (tag.equals(RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING)) {
            request.setPage(pageBean.getPage());
            request.setPageSize(pageBean.getPageSize());
            //代办
            repository.distributeWaitPage(request, new CallBack<DistributeWorkOrderPage>() {
                @Override
                public void call(DistributeWorkOrderPage data) {
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
                    ThrowableParser.onFailed(throwable);
                    LiveDataBusUtils.postStopRefresh();
                }
            });
        } else {
            //已办
            repository.distributeDonePage(request, new CallBack<DistributeWorkOrderPage>() {
                @Override
                public void call(DistributeWorkOrderPage data) {
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
}
