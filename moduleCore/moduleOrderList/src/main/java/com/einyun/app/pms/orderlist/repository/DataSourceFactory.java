package com.einyun.app.pms.orderlist.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.OrderListPageRequest;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;

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
    OrderListPageRequest request;
    String tag;
    public DataSourceFactory(OrderListPageRequest request,String tag) {
        this.request = request;
        this.tag=tag;
    }

    @NonNull
    @Override
    public DataSource<Integer, DictDataModel> create() {
        return new OrderListDataSource(request,tag);
    }
}
