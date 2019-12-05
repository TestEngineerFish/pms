package com.einyun.app.pms.approval.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.pms.approval.module.ApprovalBean;
import com.einyun.app.pms.approval.module.ApprovalItemmodule;
import com.einyun.app.pms.approval.module.ApprovalListModule;
import com.orhanobut.logger.Logger;


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
public class ItemDataSource extends BaseDataSource<ApprovalItemmodule> {

    private  ApprovalBean approvalBean;
     int table;
    public ItemDataSource(ApprovalBean bean,int table) {
        super();
        approvalBean = bean;
        this.table=table;
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        Logger.d("ItemDataSourcce..."+approvalBean.getPageBean().getPage());
        ApprovalkListRepository repository=new ApprovalkListRepository();
        approvalBean.setPageBean(pageBean);
        repository.pageQuery(approvalBean,table, new CallBack<ApprovalListModule>() {
            @Override
            public void call(ApprovalListModule data) {
                if(callback instanceof LoadInitialCallback){
                    LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(),0, (int) data.getTotal());
                    Log.e("tag"+data.getRows().size(), "call: " );
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
