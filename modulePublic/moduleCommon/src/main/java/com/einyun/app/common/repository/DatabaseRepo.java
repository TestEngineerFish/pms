package com.einyun.app.common.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.common.application.CommonApplication;

import java.util.List;

public abstract class DatabaseRepo<T> implements IDatabaseRepo<T>{
    public final static int DATA_TYPE_INIT=1;
    public final static int DATA_TYPE_APPPEND=2;
    public final static int DATA_TYPE_SYNC=3;
    protected AppDatabase db=AppDatabase.getInstance(CommonApplication.getInstance());

    public AppDatabase getDb(){
        return db;
    }

    /**
     * 执行事务
     * @param runnable
     */
    public void runInTransaction(Runnable runnable){
        db.runInTransaction(runnable);
    }

    /**
     * 清空数据
     * @param userId
     */
    protected  void deleteAll(String userId){

    }

    /**
     * 清空数据
     * @param userId
     */
    public abstract void deleteAll(String userId,int listType);

    /**
     * 插入数据
     * @param rows
     */
    public abstract void insert(List<T> rows);

    public abstract DataSource.Factory<Integer, T> queryAll(@NonNull String userId, int listype);

    /**
     * 获取数据源
     * @param userId
     * @return
     */
    public  DataSource.Factory<Integer, T> queryAll(@NonNull String userId){
        return null;
    }

    @Override
    public void persistence(List<T> rows,String userId,int listType, int dataType){
        if (dataType == DATA_TYPE_INIT) {//初始化清空数据
            if(listType<0){
                deleteAll(userId);
            }else{
                deleteAll(userId,listType);
            }
        }
        insert(rows);//追加数据
    }
}
