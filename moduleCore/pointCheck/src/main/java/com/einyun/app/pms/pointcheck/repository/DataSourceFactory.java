package com.einyun.app.pms.pointcheck.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.pms.pointcheck.model.CheckPointModel;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.pms.pointcheck.repository
 * @ClassName: DataSourceFactory
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/20 0020 下午 14:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/20 0020 下午 14:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DataSourceFactory extends DataSource.Factory<Integer, CheckPointModel>{
    @NonNull
    @Override
    public DataSource<Integer, CheckPointModel> create() {
        return new ItemDataSource();
    }
}
