package com.einyun.app.pms.pointcheck.repository;

import com.einyun.app.base.db.dao.CheckPointDao;
import com.einyun.app.base.db.entity.CheckPoint;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.repository.BaseBoundaryCallBack;
import com.einyun.app.library.resource.workorder.net.request.PageRquest;
import com.einyun.app.pms.pointcheck.model.CheckPointPage;

import java.util.List;

public class PointCheckBoundaryCallBack extends BaseBoundaryCallBack<CheckPoint> {
    PointCheckListRepository repository=new PointCheckListRepository();
    CheckPointDao dao;
    public PointCheckBoundaryCallBack(PageRquest request) {
        super(request);
        dao=db.checkPointDao();
    }

    @Override
    protected void loadData(int dataType, CallBack<Integer> callBack) {
        repository.pageQuery(pageBean, new CallBack<CheckPointPage>() {
            @Override
            public void call(CheckPointPage data) {
                if(data.isEmpty()){
                    clearAll();
                }
                if (data.hasNextPage()) {
                    callBack.call(data.nextPage());
                }
                if (data.getRows().size() > 0) {
                    wrapList(data.getRows());
                    persistence(data.getRows(), dataType);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    protected void clearAll() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll(request.getUserId());
            }
        });
    }

    @Override
    protected void persistence(List<CheckPoint> rows, int dataType) {
        lock.lock();
        db.runInTransaction(() -> {
            if (dataType == DATA_TYPE_INIT) {//初始化清空数据
                dao.deleteAll(request.getUserId());
            }
            dao.insert(rows);//追加数据
            lock.unlock();
        });
    }

    @Override
    protected void wrapList(List<CheckPoint> list) {
        for(CheckPoint checkPoint:list){
            checkPoint.setUserId(request.getUserId());
        }
    }
}
