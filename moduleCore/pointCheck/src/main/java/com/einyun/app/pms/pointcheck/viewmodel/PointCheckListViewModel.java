package com.einyun.app.pms.pointcheck.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.entity.CheckPoint;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.resource.workorder.net.request.PageRquest;
import com.einyun.app.pms.pointcheck.repository.DataSourceFactory;
import com.einyun.app.pms.pointcheck.repository.PointCheckBoundaryCallBack;
import com.einyun.app.pms.pointcheck.repository.PointCheckListRepository;

import java.util.concurrent.Executors;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.viewmodel
 * @ClassName: PointCheckListViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:43
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:43
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckListViewModel extends BasePageListViewModel<CheckPoint> {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    PointCheckListRepository repository=new PointCheckListRepository();
    PointCheckBoundaryCallBack pointCheckBoundaryCallBack;
    protected PageRquest request;

    public PointCheckListViewModel(){
        request=new PageRquest();
        request.setUserId(userModuleService.getUserId());
    }

    @Override
    public void refresh() {
        if(pointCheckBoundaryCallBack!=null){
            pointCheckBoundaryCallBack.refresh();
        }
    }

    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<CheckPoint>> loadPagingData(){
        if(pointCheckBoundaryCallBack==null){
            pointCheckBoundaryCallBack=new PointCheckBoundaryCallBack(request);
        }
        if(pageList ==null){
            pageList = new LivePagedListBuilder(repository.queryAll(request.getUserId()), config)
                .setBoundaryCallback(pointCheckBoundaryCallBack)
                    .setFetchExecutor(Executors.newFixedThreadPool(2))
                    .build();
        }
        return pageList;
    }

}
