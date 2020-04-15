package com.einyun.app.pms.mine.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.pms.mine.model.MsgModel;
import com.einyun.app.pms.mine.model.RequestPageBean;


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
public class DataSourceFactory extends DataSource.Factory<Integer, MsgModel>{

    private RequestPageBean bean;
    String  table;
    public DataSourceFactory(RequestPageBean requestBean, String table) {
        bean = requestBean;
        this.table=table;
    }

    @NonNull
    @Override
    public DataSource<Integer, MsgModel> create() {
        return new ItemDataSource(bean,table);
    }
}
