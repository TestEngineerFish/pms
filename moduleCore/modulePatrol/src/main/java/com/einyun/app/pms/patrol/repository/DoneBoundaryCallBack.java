package com.einyun.app.pms.patrol.repository;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.repository.BaseBoundaryCallBack;
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrderPage;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.convert.PatrolListTypeConvert;
import com.google.gson.Gson;

import java.util.List;

public class DoneBoundaryCallBack extends PatrolPendingBoundaryCallBack{
    public DoneBoundaryCallBack(PatrolPageRequest request) {
        super(request);
        listType=ListType.DONE.getType();
    }

    @Override
    protected void loadData(final int dataType) {
        service.patrolClosedPage(request, new CallBack<PatrolWorkOrderPage>() {
            @Override
            public void call(PatrolWorkOrderPage data) {
                PatrolListTypeConvert convert=new PatrolListTypeConvert();
                List<Patrol> patrols=convert.stringToSomeObject(new Gson().toJson(data.getRows()));
                wrapList(patrols);
                if(dataType== BaseBoundaryCallBack.DATA_TYPE_INIT){
                    patrolRepo.initData(patrols,request.getUserId(), listType);
                }else if(dataType==BaseBoundaryCallBack.DATA_TYPE_SYNC){
                    //同步数据
                    patrolRepo.sync(patrols, request.getUserId(),listType, null);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }
}
