package com.einyun.app.pms.pointcheck.repository;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.pointcheck.model.PointCheckDetialModel;
import com.einyun.app.pms.pointcheck.net.CheckPointServiceApi;
import com.einyun.app.pms.pointcheck.net.URLs;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.repository
 * @ClassName: PointCheckDetialRepository
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:47
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:47
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckDetialRepository {
    CheckPointServiceApi serviceApi;

    public PointCheckDetialRepository() {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(CheckPointServiceApi.class);
    }

    public void detial(String id, CallBack<PointCheckDetialModel> callBack) {
        String url = URLs.URL_POINT_CHECK_DETIAL + id;
        serviceApi.detial(url).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
                    if(response.isState()){
                        callBack.call(response.getData());
                    }else{
                        callBack.onFaild(new Exception(response.getCode()));
                    }
                }, error -> {
                    callBack.onFaild(error);
                });
    }
}
