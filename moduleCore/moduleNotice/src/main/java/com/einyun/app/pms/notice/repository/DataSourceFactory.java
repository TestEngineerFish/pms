package com.einyun.app.pms.notice.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.common.databinding.LayoutListPageStateBinding;
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.repository
 * @ClassName: DataSourceFactory
 * @Description: 数据源工厂
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:34
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:34
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DataSourceFactory extends DataSource.Factory<Integer, DictDataModel> {
    NoticeListPageRequest request;
    LayoutListPageStateBinding pageState;
    public DataSourceFactory(NoticeListPageRequest request,LayoutListPageStateBinding pageState) {
        this.request = request;
        this.pageState = pageState;
    }

    @NonNull
    @Override
    public DataSource<Integer, DictDataModel> create() {
        return new NoticeDataSource(request,pageState);
    }
}
