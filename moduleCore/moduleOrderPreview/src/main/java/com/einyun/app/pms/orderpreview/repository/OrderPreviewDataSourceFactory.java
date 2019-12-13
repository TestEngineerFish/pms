package com.einyun.app.pms.orderpreview.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.library.resource.workorder.model.OrderPreviewModel;
import com.einyun.app.library.resource.workorder.net.request.OrderPreviewRequest;

public class OrderPreviewDataSourceFactory extends DataSource.Factory<Integer, OrderPreviewModel> {
    private OrderPreviewRequest request;
    private String tag;
    public OrderPreviewDataSourceFactory(OrderPreviewRequest request,String tag) {
        this.request = request;
        this.tag=tag;
    }

    @NonNull
    @Override
    public DataSource<Integer, OrderPreviewModel> create() {
        return new OrderPreviewItemDataSource(request,tag);
    }
}
