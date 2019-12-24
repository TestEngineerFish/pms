package com.einyun.app.common.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.entity.Distribute;

import java.util.List;

public interface IDatabaseRepo<T> {
    void runInTransaction(Runnable runnable);
    void deleteAll(String userId,int listType);
    void insert(List<T> rows);
    DataSource.Factory<Integer, T> queryAll(@NonNull String userId, int listype);
    void persistence(List<T> rows,String userId, int dataType);
}
