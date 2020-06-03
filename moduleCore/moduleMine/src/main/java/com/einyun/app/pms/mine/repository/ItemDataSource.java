package com.einyun.app.pms.mine.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.pms.mine.model.MsgListModel;
import com.einyun.app.pms.mine.model.MsgModel;
import com.einyun.app.pms.mine.model.RequestPageBean;
import com.jeremyliao.liveeventbus.LiveEventBus;


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
public class ItemDataSource extends BaseDataSource<MsgModel> {

    private RequestPageBean requestBean;
     String tag;
    public ItemDataSource(RequestPageBean requestBean, String tag) {
        super();
        this.requestBean = requestBean;
        this.tag=tag;
    }

    @Override
    public <T> void loadData(PageBean pageBean, @NonNull T callback) {
        FeedBackRepository repository=new FeedBackRepository();
        requestBean.setPageBean(pageBean);
        repository.pageQuery(requestBean,tag, new CallBack<MsgListModel>() {
            @Override
            public void call(MsgListModel data) {
                if(callback instanceof LoadInitialCallback){
                    LoadInitialCallback loadInitialCallback= (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(),0, (int) data.getRows().size());
                    Log.e("tag"+data.getRows().size(), "call:data.getTotal() " +data.getTotal());
                        LiveDataBusUtils.postLiveBusData(LiveDataBusKey.MSGCENTER_EMPTY,data.getTotal());
                }else if(callback instanceof LoadRangeCallback){
                    LoadRangeCallback loadInitialCallback= (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
                if (data.getTotal()!=0) {
                    LiveEventBus.get(LiveDataBusKey.MSG_EMPTY_FRESH,String.class).post("");
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

}
