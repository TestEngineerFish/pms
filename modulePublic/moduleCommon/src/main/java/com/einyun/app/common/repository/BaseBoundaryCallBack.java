package com.einyun.app.common.repository;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.library.resource.workorder.net.request.PageRquest;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BaseBoundaryCallBack
 * @param <M>
 */
public abstract class BaseBoundaryCallBack<M> extends PagedList.BoundaryCallback<M>{
    protected AppDatabase db;
    public final static int DATA_TYPE_INIT=1;
    public final static int DATA_TYPE_APPPEND=2;
    protected PageRquest request;
    protected PageBean pageBean;
    protected Lock lock =new ReentrantLock();

    public PageRquest getRequest() {
        return request;
    }

    public void setRequest(PageRquest request) {
        this.request = request;
    }

    public BaseBoundaryCallBack(PageRquest request){
        setRequest(request);
        pageBean=new PageBean();
        db = AppDatabase.getInstance(CommonApplication.getInstance());
    }


    /**
     * 网络加载数据
     * @param dataType
     */
    protected abstract void loadData(int dataType, CallBack<Integer> callBack);

    /**
     * 数据持久化
     * @param rows
     * @param dataType
     */
    protected abstract void persistence(List<M> rows, int dataType);

    /**
     * 数据包装，包装userId等
     * @param list
     */
    protected abstract void wrapList(List<M> list);

    protected void initData(){
        pageBean.setPage(PageBean.DEFAULT_PAGE);
        loadData(DATA_TYPE_INIT, new CallBack<Integer>() {
            @Override
            public void call(Integer data) {
                pageBean.setPage(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
       initData();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull M itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull M itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        loadData(DATA_TYPE_APPPEND, new CallBack<Integer>() {
            @Override
            public void call(Integer data) {
                Logger.d("setPage->"+this.toString()+":"+data);
                pageBean.setPage(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
            }
        });
    }
}
