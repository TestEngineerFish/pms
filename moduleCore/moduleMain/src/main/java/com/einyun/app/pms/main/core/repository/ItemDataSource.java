package com.einyun.app.pms.main.core.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.pms.main.core.model.ScanListModel;
import com.einyun.app.pms.main.core.model.ScanRequest;
import com.einyun.app.pms.main.core.model.ScanResItemModel;


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
public class ItemDataSource extends BaseDataSource<ScanResItemModel> {

    private ScanRequest requestBean;
     String tag;
     String type;
    public ItemDataSource(ScanRequest requestBean, String tag,String type) {
        super();
        this.requestBean = requestBean;
        this.tag=tag;
        this.type=type;
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        MainRepository repository=new MainRepository();
//        requestBean.setPage(pageBean);
        requestBean.setPage(pageBean.getPage());
        requestBean.setPageSize(pageBean.getPageSize());

        repository.pageQuery(requestBean,tag,type, new CallBack<ScanListModel>() {
            @Override
            public void call(ScanListModel data) {
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
