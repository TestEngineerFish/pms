package com.einyun.app.pms.mine.model;

import java.util.List;

public class MsgListModel {
    private int total;
    private int page;
    private int pageSize;
    private List<MsgModel> rows;

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

    public List<MsgModel> getRows() {
        return rows;
    }

    public void setRows(List<MsgModel> rows) {
        this.rows = rows;
    }
}
