package com.einyun.app.pms.pointcheck.model;

import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.net.response
 * @ClassName: CheckPointPage
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 16:06
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 16:06
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CheckPointPage {
    private List<CheckPointModel> rows;
    private long total;
    private int page;
    private int pageSize;
    public List<CheckPointModel> getRows() {
        return rows;
    }

    public void setRows(List<CheckPointModel> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
