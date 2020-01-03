package com.einyun.app.pms.sendorder.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;

public class OrderDataSourceFactory extends DataSource.Factory<Integer, Distribute> {
    private DistributePageRequest request;
    private String tag;
    public OrderDataSourceFactory(DistributePageRequest request,String tag){
        this.request=request;
        this.tag=tag;
    }
    @NonNull
    @Override
    public DataSource<Integer, Distribute> create() {
        return new OrderItemDataSource(request,tag);
    }
}
