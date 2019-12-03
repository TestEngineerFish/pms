package com.einyun.app.pms.patrol.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.entity.Patrol;

public class DataSourceFactory extends DataSource.Factory<Integer, Patrol>{
    @NonNull
    @Override
    public DataSource<Integer, Patrol> create() {
        return new ItemDataSource();
    }
}
