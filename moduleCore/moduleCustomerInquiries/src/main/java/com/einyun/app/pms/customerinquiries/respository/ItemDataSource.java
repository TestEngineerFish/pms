package com.einyun.app.pms.customerinquiries.respository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;

import com.einyun.app.pms.customerinquiries.module.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesListModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesRequestBean;
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
public class ItemDataSource extends BaseDataSource<InquiriesItemModule> {

    private InquiriesRequestBean requestBean;
     String tag;
    public ItemDataSource(InquiriesRequestBean requestBean,String tag) {
        super();
        this.requestBean = requestBean;
        this.tag=tag;
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        CustomerInquiriesRepository repository=new CustomerInquiriesRepository();
        requestBean.setPage(pageBean);
        repository.pageQuery(requestBean,tag, new CallBack<InquiriesListModule>() {
            @Override
            public void call(InquiriesListModule data) {
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
