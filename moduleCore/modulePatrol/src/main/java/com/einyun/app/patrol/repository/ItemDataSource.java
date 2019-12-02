package com.einyun.app.patrol.repository;

import androidx.annotation.NonNull;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;

import java.util.List;

public class ItemDataSource extends BaseDataSource<Patrol> {


    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        PatrolListRepo repo=new PatrolListRepo();
        List<Patrol> patrolList=repo.queryPage(pageBean.current(),pageBean.getPageSize());
        if(callback instanceof LoadInitialCallback){
            LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
            loadInitialCallback.onResult(patrolList,0, patrolList.size());
        }else if(callback instanceof LoadRangeCallback){
            LoadRangeCallback loadInitialCallback= (LoadRangeCallback) callback;
            loadInitialCallback.onResult(patrolList);
        }
    }
}
