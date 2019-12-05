package com.einyun.app.pms.sendorder.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.resource.workorder.model.Distribute;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;

public class OrderItemDataSource extends BaseDataSource<DistributeWorkOrder> {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        ResourceWorkOrderRepo repository=new ResourceWorkOrderRepo();
        DistributePageRequest request=new DistributePageRequest();
        request.setPageBean(pageBean);
        request.setUserId("63879813097586693");
        repository.distributeWaitPage(request, new CallBack<DistributeWorkOrderPage>() {
            @Override
            public void call(DistributeWorkOrderPage data) {
                if(callback instanceof LoadInitialCallback){
                    LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(),0, (int) data.getTotal());
                }else if(callback instanceof LoadRangeCallback){
                    LoadRangeCallback loadInitialCallback= (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
}
