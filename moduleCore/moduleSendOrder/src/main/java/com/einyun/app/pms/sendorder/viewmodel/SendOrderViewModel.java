package com.einyun.app.pms.sendorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.repository.OrderDataSourceFactory;

import java.util.List;

public class SendOrderViewModel extends BasePageListViewModel {
    private ResourceWorkOrderRepo resourceWorkOrderRepo;
    private ResourceWorkOrderService resourceWorkOrderService;
    private LiveData<DistributeWorkOrderPage> workOrderListViewModel;

    public SendOrderViewModel() {
        this.resourceWorkOrderRepo = new ResourceWorkOrderRepo();
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<DistributeWorkOrder>> loadPadingData(){
        if(pageList ==null){
            pageList = new LivePagedListBuilder(new OrderDataSourceFactory(), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                    .build();
        }
        return pageList;
    }
    /**
     * 获取派工单代办列表
     * @return
     */
    public LiveData<DistributeWorkOrderPage> distributeWaitPage(DistributePageRequest request) {
        //网络数据交互，显示Loading
        showLoading();
        workOrderListViewModel = resourceWorkOrderService.distributeWaitPage(request, new CallBack<DistributeWorkOrderPage>() {

            @Override
            public void call(DistributeWorkOrderPage data) {
                //关闭Loading
                hideLoading();
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });//数据获取
        return workOrderListViewModel;
    }
}
