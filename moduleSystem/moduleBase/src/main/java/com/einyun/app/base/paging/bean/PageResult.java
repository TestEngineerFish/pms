package com.einyun.app.base.paging.bean;

import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.paging.bean
 * @ClassName: PageResult
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/11 15:34
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/11 15:34
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PageResult<T> {
    private int page;
    private int pageSize;
    private int total;
    private List<T> rows;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
