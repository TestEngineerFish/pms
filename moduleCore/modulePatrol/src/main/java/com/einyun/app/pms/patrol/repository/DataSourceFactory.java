package com.einyun.app.pms.patrol.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;

public class DataSourceFactory extends DataSource.Factory<Integer, Patrol>{

    private PatrolPageRequest request;

    public DataSourceFactory(PatrolPageRequest request) {
        this.request = request;
    }

    @NonNull
    @Override
    public DataSource<Integer, Patrol> create() {
        return new ItemDataSource(request);
    }
}
