package com.einyun.app.pms.orderpreview.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.OrderPreviewModel;
import com.einyun.app.library.resource.workorder.model.PreviewSelectModel;
import com.einyun.app.library.resource.workorder.net.request.OrderPreviewRequest;
import com.einyun.app.library.resource.workorder.net.request.ResendOrderRequest;
import com.einyun.app.library.resource.workorder.net.response.ResendOrderResponse;
import com.einyun.app.library.resource.workorder.repository.ResourceWorkOrderRepo;
import com.einyun.app.pms.orderpreview.repository.OrderPreviewDataSourceFactory;

import java.util.List;
import java.util.Map;


import static com.einyun.app.common.ui.widget.SelectPopUpView.PREVIEW_SELECT_TIAOXIAN;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_TIME_CIRCLE;

public class OrderPreviewViewModel extends BasePageListViewModel<OrderPreviewModel> {
    private ResourceWorkOrderRepo resourceWorkOrderRepo;
    LiveData<PagedList<OrderPreviewModel>> previewList;
    private ResourceWorkOrderService resourceWorkOrderService;
    private OrderPreviewRequest request = new OrderPreviewRequest();

    public OrderPreviewViewModel() {
        this.resourceWorkOrderRepo = new ResourceWorkOrderRepo();
        this.resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }


    public OrderPreviewRequest getRequest() {
        return request;
    }

    public void setRequest(OrderPreviewRequest request) {
        this.request = request;
    }

    /**
     * * 获取计划工单
     *
     * @return LiveData
     */
    public LiveData<PagedList<OrderPreviewModel>> loadPadingData(OrderPreviewRequest request, String tag) {
        pageList = new LivePagedListBuilder(new OrderPreviewDataSourceFactory(request, tag), config)
                .build();
        return pageList;
    }

    public void onConditionSelected(Map<String, SelectModel> selected) {
        if (selected.get(SELECT_TIME_CIRCLE) != null) {
            String id = selected.get(SELECT_TIME_CIRCLE).getId();
            request.getQuerys().get(0).setValue(id);
        }
       /* if (selected.get(PREVIEW_SELECT_TIAOXIAN) != null) {
            String id = selected.get(SELECT_TIME_CIRCLE).getId();
            request.getQuerys().get(1).setValue(id);
        }*/
        refreshUI();
    }

    /**
     * * 获取条线数据
     *
     * @return LiveData
     */

    public MutableLiveData<List<PreviewSelectModel>> getOrderPreviewSelect() {
        showLoading();
        MutableLiveData<List<PreviewSelectModel>> select = new MutableLiveData<>();
        resourceWorkOrderService.getOrderPreviewSelect(new CallBack<List<PreviewSelectModel>>() {
            @Override
            public void call(List<PreviewSelectModel> data) {
                hideLoading();
                select.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();

            }
        });
        return select;
    }
}
