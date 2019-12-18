package com.einyun.app.pms.repairs.repository;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.model.RepairGrabPage;
import com.einyun.app.library.workorder.model.RepairsPage;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.library.workorder.repository.WorkOrderRepository;
import com.einyun.app.base.paging.bean.PageBean;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.repository
 * @ClassName: RepairsDataSource
 * @Description: Paging数据源
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:45
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:45
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RepairsDataSource extends BaseDataSource<DictDataModel> {
    private RepairsPageRequest request;

    public RepairsDataSource(RepairsPageRequest request) {
        this.request = request;
    }

    //根据页数获取数据
    public  <T> void loadData(PageBean pageBean,@NonNull T callback){
        WorkOrderRepository repository = new WorkOrderRepository();
        request.setPageBean(pageBean);
        repository.getRepairGrab(request,new CallBack<RepairsPage>() {
            @Override
            public void call(RepairsPage data) {
                if(callback instanceof LoadInitialCallback){
                    LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(),0,(int)data.getTotal());
                }else if(callback instanceof LoadRangeCallback){
                    LoadRangeCallback loadInitialCallback= (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
}
