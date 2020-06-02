package com.einyun.app.pms.main.core.model;

import java.util.List;

public class ScanListModel {
    private int total;
    private int page;
    private int pageSize;
    private List<ScanResItemModel> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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

    public List<ScanResItemModel> getRows() {
        return rows;
    }

    public void setRows(List<ScanResItemModel> rows) {
        this.rows = rows;
    }
}
