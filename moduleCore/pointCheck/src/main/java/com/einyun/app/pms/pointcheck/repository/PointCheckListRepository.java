package com.einyun.app.pms.pointcheck.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.CheckPointDao;
import com.einyun.app.base.db.entity.CheckPoint;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.RxSchedulers;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.pointcheck.model.CheckPointPage;
import com.einyun.app.pms.pointcheck.net.CheckPointServiceApi;
import com.einyun.app.pms.pointcheck.net.request.PageQueryRequest;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.repository
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
    CheckPointDao dao;

    public PointCheckListRepository() {
        serviceApi = EinyunHttpService.Companion.getInstance().getServiceApi(CheckPointServiceApi.class);
        dao= AppDatabase.getInstance(CommonApplication.getInstance()).checkPointDao();
    }

    public void pageQuery(PageBean page, CallBack<CheckPointPage> callback) {
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

    public DataSource.Factory<Integer, CheckPoint> queryAll(@NonNull String userId){
        return dao.queryAll(userId);
    }
}
