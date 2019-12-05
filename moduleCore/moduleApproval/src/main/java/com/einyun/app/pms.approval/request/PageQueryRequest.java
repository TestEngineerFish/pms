package com.einyun.app.pms.approval.request;


import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.pms.approval.module.ApprovalBean;

public class PageQueryRequest {
    private ApprovalBean pageBean;

    public ApprovalBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(ApprovalBean pageBean) {
        this.pageBean = pageBean;
    }
}
