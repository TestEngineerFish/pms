package com.einyun.app.pms.approval.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.pms.approval.model.ApprovalBean;
import com.einyun.app.pms.approval.model.ApprovalItemmodule;


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
public class DataSourceFactory extends DataSource.Factory<Integer, ApprovalItemmodule>{

    private final ApprovalBean bean;
    int table;
    public DataSourceFactory(ApprovalBean approvalBean,int table) {
        bean = approvalBean;
        this.table=table;
    }

    @NonNull
    @Override
    public DataSource<Integer, ApprovalItemmodule> create() {
        return new ItemDataSource(bean,table);
    }
}
