package com.einyun.app.pms.notice.repository;

import androidx.annotation.NonNull;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.datasource.BaseDataSource;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.LayoutListPageStateBinding;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest;
import com.einyun.app.library.mdm.net.response.NoticeListPageResult;
import com.einyun.app.library.mdm.repository.MdmRepository;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;


/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.repository
 * @ClassName: NoticeDataSource
 * @Description: Paging数据源
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:45
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:45
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class NoticeDataSource extends BaseDataSource<DictDataModel> {
    private NoticeListPageRequest request;
    private LayoutListPageStateBinding pageStateBinding;
    public NoticeDataSource(NoticeListPageRequest request, LayoutListPageStateBinding pageState) {
        this.request = request;
        this.pageStateBinding = pageState;
    }

    protected void updatePageUIState(int total) {
        if (total == 0) {
            pageStateBinding.setPageState(PageUIState.EMPTY.getState());
        } else {
            pageStateBinding.setPageState(PageUIState.FILLDATA.getState());
        }
    }

    //根据页数获取数据
    public  <T> void loadData(PageBean pageBean,@NonNull T callback) {
        MdmRepository repository = new MdmRepository();
//        pageBean.setPageSize(10);
        request.setPageBean(pageBean);
        repository.getNoticeList(request, new CallBack<NoticeListPageResult>() {
            @Override
            public void call(NoticeListPageResult data) {
                if (callback instanceof LoadInitialCallback) {
                    LoadInitialCallback loadInitialCallback = (LoadInitialCallback) callback;
                    loadInitialCallback.onResult(data.getRows(), 0, (int) data.getTotal());
                    updatePageUIState(data.getTotal());
                } else if (callback instanceof LoadRangeCallback) {
                    LoadRangeCallback loadInitialCallback = (LoadRangeCallback) callback;
                    loadInitialCallback.onResult(data.getRows());
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
        return;
        }
    }

