package com.einyun.app.pms.complain.repository;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.model.ComplainPage;
import com.einyun.app.library.workorder.model.RepairGrabPage;
import com.einyun.app.library.workorder.model.RepairsPage;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.library.workorder.repository.WorkOrderRepository;
import com.einyun.app.base.paging.bean.PageBean;

import org.jetbrains.annotations.NotNull;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREDY_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;

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
public class ComplainDataSource extends BaseDataSource<DictDataModel> {
    private RepairsPageRequest request;
    private String tag;
    public ComplainDataSource(RepairsPageRequest request,String tag) {
        this.request = request;
        this.tag=tag;
    }

    //根据页数获取数据
    public  <T> void loadData(PageBean pageBean,@NonNull T callback){
        WorkOrderRepository repository = new WorkOrderRepository();
        request.setPageBean(pageBean);
        //待跟进
        if (tag.equals(FRAGMENT_REPAIR_WAIT_FOLLOW)) {
            repository.getComplainWaitFollow(request, getCallBack(callback));
        }
        //待反馈
        if (tag.equals(FRAGMENT_REPAIR_WAIT_FEED)) {
            repository.getComplainWaitFeed(request, getCallBack(callback));
        }
        //已跟进
        if (tag.equals(FRAGMENT_REPAIR_ALREADY_FOLLOW)) {
            repository.getComplainAlreadyFollow(request, getCallBack(callback));
        }
        //已办结
        if (tag.equals(FRAGMENT_REPAIR_ALREDY_DONE)) {
            repository.getComplainAlreadyDone(request, getCallBack(callback));
        }
        //抄送我
        if (tag.equals(FRAGMENT_REPAIR_COPY_ME)) {
            repository.getComplainCopyMe(request, getCallBack(callback));
        }
    }

    @NotNull
    private <T> CallBack<ComplainPage> getCallBack(@NonNull T callback) {
        return new CallBack<ComplainPage>() {
            @Override
            public void call(ComplainPage data) {
                if (callback instanceof PositionalDataSource.LoadInitialCallback) {
                    LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                } else if (callback instanceof PositionalDataSource.LoadRangeCallback) {
                    LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        };
    }
}

