package com.example.shimaostaff.pointcheck.repository;

import com.example.shimaostaff.http.CallBack;
import com.example.shimaostaff.http.HttpService;
import com.example.shimaostaff.http.RxSchedulers;
import com.example.shimaostaff.pointcheck.model.PageModel;
import com.example.shimaostaff.pointcheck.net.CheckPointServiceApi;
import com.example.shimaostaff.pointcheck.net.request.PageQueryRequest;
import com.example.shimaostaff.pointcheck.model.CheckPointPage;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.repository
 * @ClassName: PointCheckListRepository
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:46
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:46
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckListRepository {
    CheckPointServiceApi serviceApi;

    public PointCheckListRepository() {
        serviceApi = HttpService.getInstance().getServiceApi(CheckPointServiceApi.class);
    }

    public void pageQuery(PageModel page, CallBack<CheckPointPage> callback) {
        PageQueryRequest request = new PageQueryRequest();
        request.setPageBean(page);
        serviceApi.query(request).compose(RxSchedulers.inIoMain())
                .subscribe(response -> {
//                    if(response.isState()){
                    callback.call(response.getData());
//                    }
                }, error -> {
                    callback.onFaild(error);
                    error.printStackTrace();
                });
    }
}
