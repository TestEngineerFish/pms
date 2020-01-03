package com.einyun.app.pms.patrol.repository;

import androidx.annotation.NonNull;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.convert.PatrolListTypeConvert;
import com.google.gson.Gson;

import java.util.List;

public class ItemDataSource extends BaseDataSource<Patrol> {
    ResourceWorkOrderService service;
    private PatrolPageRequest request;

    public ItemDataSource(PatrolPageRequest request) {
        this.request = request;
        service= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        request.setPage(pageBean.getPage());
        service.patrolClosedPage(request, new CallBack<PatrolWorkOrderPage>() {
            @Override
            public void call(PatrolWorkOrderPage data) {
                PatrolListTypeConvert convert=new PatrolListTypeConvert();
                List<Patrol> patrols=convert.stringToSomeObject(new Gson().toJson(data.getRows()));
                if(callback instanceof LoadInitialCallback){
                    LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(patrols,0, data.getTotal());
                }else if(callback instanceof LoadRangeCallback){
                    LoadRangeCallback loadInitialCallback= (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(patrols);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }
}
