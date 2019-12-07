package com.einyun.app.pms.sendorder.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;

public class OrderDataSourceFactory extends DataSource.Factory<Integer, DistributeWorkOrder> {
    private DistributePageRequest request;
    private String tag;
    public OrderDataSourceFactory(DistributePageRequest request,String tag){
        this.request=request;
        this.tag=tag;
    }
    @NonNull
    @Override
    public DataSource<Integer, DistributeWorkOrder> create() {
        return new OrderItemDataSource(request,tag);
    }
}
