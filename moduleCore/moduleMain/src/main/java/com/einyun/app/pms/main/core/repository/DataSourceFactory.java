package com.einyun.app.pms.main.core.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import com.einyun.app.pms.main.core.model.ScanRequest;
import com.einyun.app.pms.main.core.model.ScanResItemModel;


/**
 * @ProjectName: pms
 * @Package: com.einyun.app.pms.pointcheck.repository
 * @ClassName: DataSourceFactory
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/11/20 0020 下午 14:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/20 0020 下午 14:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DataSourceFactory extends DataSource.Factory<Integer, ScanResItemModel>{

    private ScanRequest bean;
    String  table;
    String  type;
    public DataSourceFactory(ScanRequest requestBean, String table,String type) {
        bean = requestBean;
        this.table=table;
        this.type=type;
    }

    @NonNull
    @Override
    public DataSource<Integer, ScanResItemModel> create() {
        return new ItemDataSource(bean,table,type);
    }
}
