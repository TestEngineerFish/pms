package com.einyun.app.common.repository;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.bean.PageResult;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.library.resource.workorder.net.request.PageRquest;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.einyun.app.common.repository.DatabaseRepo.DATA_TYPE_APPPEND;
import static com.einyun.app.common.repository.DatabaseRepo.DATA_TYPE_INIT;

/**
 * BaseBoundaryCallBack
 * @param <M>
 */
public abstract class BaseBoundaryCallBack<M> extends PagedList.BoundaryCallback<M>{
    protected PageRquest request;
    protected PageBean pageBean;
    protected Lock lock =new ReentrantLock();
    protected IDatabaseRepo<M> repo;
    public PageRquest getRequest() {
        return request;
    }
    protected boolean hasInit=false;

    public void setRequest(PageRquest request) {
        this.request = request;
    }

    public BaseBoundaryCallBack(PageRquest request){
        setRequest(request);
        pageBean=new PageBean();
    }

    /**
     * 切换筛选条件
     */
    public void switchCondition() {
        clearAll();
        initData();
    }

    /**
     * 网络加载数据
     * @param dataType
     */
    protected abstract void loadData(int dataType, CallBack<Integer> callBack);

    /**
     * 清空数据
     */
    protected abstract void clearAll();

    /**
     * 刷新数据
     */
    public void refresh(){
        initData();
    }

    /**
     * 数据包装，包装userId等
     * @param list
     */
    protected abstract void wrapList(List<M> list);

    protected void initData(){
        pageBean.setPage(PageBean.DEFAULT_PAGE);
        request.setPage(PageBean.DEFAULT_PAGE);
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
        request.setPage(pageBean.getPage());
        int dataType=DATA_TYPE_INIT;
        if(hasInit){
            dataType=DATA_TYPE_APPPEND;
        }
        loadData(dataType, new CallBack<Integer>() {
            @Override
            public void call(Integer data) {
                Logger.d("setPage->"+this.toString()+":"+data);
                pageBean.setPage(data);
                hasInit=true;
            }

            @Override
            public void onFaild(Throwable throwable) {
            }
        });
    }

    protected abstract void onDataLoaded(int dataType,int listType, PageResult data, CallBack<Integer> callBack);
}
