package com.einyun.app.pms.pointcheck.repository;

import androidx.annotation.NonNull;

import com.einyun.app.base.db.entity.CheckPoint;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.pms.pointcheck.model.CheckPointPage;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.pms.pointcheck.repository
 * @ClassName: ItemDataSource
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/20 0020 下午 14:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/20 0020 下午 14:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ItemDataSource extends BaseDataSource<CheckPoint> {

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
//        if(callback instanceof LoadInitialCallback){
//            ((LoadInitialCallback) callback).onResult(loadCache(),0, getCacheTotal());
//        }
        PointCheckListRepository repository=new PointCheckListRepository();
        repository.pageQuery(pageBean, new CallBack<CheckPointPage>() {
            @Override
            public void call(CheckPointPage data) {
                if(callback instanceof LoadInitialCallback){
                    LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
                    saveCached(data.getRows(),(int)data.getTotal());
                    loadInitialCallback.onResult(data.getRows(),0, (int) data.getTotal());
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
