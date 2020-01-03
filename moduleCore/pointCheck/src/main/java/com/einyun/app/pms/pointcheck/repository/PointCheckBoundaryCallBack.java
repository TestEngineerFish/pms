package com.einyun.app.pms.pointcheck.repository;

import com.einyun.app.base.db.entity.CheckPoint;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageResult;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.repository.BaseBoundaryCallBack;
import com.einyun.app.library.resource.workorder.net.request.PageRquest;
import com.einyun.app.pms.pointcheck.model.CheckPointPage;

import java.util.List;

public class PointCheckBoundaryCallBack extends BaseBoundaryCallBack<CheckPoint> {
    public PointCheckBoundaryCallBack(PageRquest request) {
        super(request);
        repo=new PointCheckListRepository();
    }

    @Override
    protected void loadData(int dataType, CallBack<Integer> callBack) {
        ((PointCheckListRepository)repo).pageQuery(pageBean, new CallBack<CheckPointPage>() {
            @Override
            public void call(CheckPointPage data) {
                onDataLoaded(dataType,-1,data,callBack);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    protected void clearAll() {
        repo.runInTransaction(() -> ((PointCheckListRepository)repo).deleteAll(request.getUserId()));
    }


    @Override
    protected void wrapList(List<CheckPoint> list) {
        for(CheckPoint checkPoint:list){
            checkPoint.setUserId(request.getUserId());
        }
    }

    @Override
    protected void onDataLoaded(int dataType,int listType, PageResult data, CallBack<Integer> callBack) {
        if(data.isEmpty()){
            clearAll();
        }
        if (data.hasNextPage()) {
            callBack.call(data.nextPage());
        }
        if (data.getRows().size() > 0) {
            wrapList(data.getRows());
            repo.persistence(data.getRows(),request.getUserId(),listType, dataType);
        }
    }
}
