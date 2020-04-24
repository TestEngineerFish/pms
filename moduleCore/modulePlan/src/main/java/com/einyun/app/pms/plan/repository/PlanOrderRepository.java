package com.einyun.app.pms.plan.repository;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;

import com.einyun.app.pms.plan.model.PlanOrderResLineModel;
import com.google.gson.JsonObject;

import java.util.List;



public class PlanOrderRepository {

    PlanOrderServiceApi serviceApi2;

    public PlanOrderRepository(String ss) {
        serviceApi2 = EinyunHttpService.Companion.getInstance().getServiceApi(PlanOrderServiceApi.class);
    }
    /**
     * get
     * 获取上次催缴时间
     */
    public void getLastWorthTime(String did, CallBack<List<PlanOrderResLineModel>> callBack) {
        String url= "/portal/sys/dataDict/v1/getByTypeKey?typeKey="+did;
        serviceApi2.getLastWorthTime(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
//                        callBack.onFaild(new Exception(response));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
}
