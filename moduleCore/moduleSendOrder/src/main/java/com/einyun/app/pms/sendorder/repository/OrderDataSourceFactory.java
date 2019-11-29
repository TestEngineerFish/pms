package com.einyun.app.pms.sendorder.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;

public class OrderDataSourceFactory extends DataSource.Factory<Integer, DistributeWorkOrder> {
    @NonNull
    @Override
    public DataSource<Integer, DistributeWorkOrder> create() {
        return new OrderItemDataSource();
    }
}
