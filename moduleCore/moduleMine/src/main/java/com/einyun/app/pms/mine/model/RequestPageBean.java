package com.einyun.app.pms.mine.model;

import com.einyun.app.base.paging.bean.PageBean;

public class RequestPageBean {

    /**
     * pageBean : {"page":1,"pageSize":5,"showTotal":true}
     */

    private PageBean pageBean;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }


}
