package com.example.shimaostaff.pointcheck.repository;

import com.example.shimaostaff.http.CallBack;
import com.example.shimaostaff.http.HttpService;
import com.example.shimaostaff.http.RxSchedulers;
import com.example.shimaostaff.pointcheck.model.PointCheckDetialModel;
import com.example.shimaostaff.pointcheck.net.CheckPointServiceApi;
import com.example.shimaostaff.pointcheck.net.URLs;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.repository
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
        serviceApi = HttpService.getInstance().getServiceApi(CheckPointServiceApi.class);
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
