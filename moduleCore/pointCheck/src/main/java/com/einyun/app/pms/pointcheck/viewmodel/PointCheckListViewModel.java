package com.einyun.app.pms.pointcheck.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.pms.pointcheck.model.CheckPointModel;
import com.einyun.app.pms.pointcheck.repository.DataSourceFactory;

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
public class PointCheckListViewModel extends BasePageListViewModel<CheckPointModel> {
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<CheckPointModel>> loadPadingData(){
        if(pageList ==null){
            pageList = new LivePagedListBuilder(new DataSourceFactory(), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                    .build();
        }
        return pageList;
    }

}
