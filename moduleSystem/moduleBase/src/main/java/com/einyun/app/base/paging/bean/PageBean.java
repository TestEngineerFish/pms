package com.einyun.app.base.paging.bean;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.repairs.model
 * @ClassName: PageBean
 * @Description: paging bean
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 09:54
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 09:54
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PageBean {
    public static final int DEFAULT_PAGE_SIZE=10;
    public static final int DEFAULT_PAGE=1;
    private int page=DEFAULT_PAGE;
    private int pageSize=DEFAULT_PAGE_SIZE;
    private boolean showTotal=true;

    public PageBean() {
    }

    public PageBean(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageBean(int page, int pageSize,boolean showTotal) {
        this.page = page;
        this.pageSize = pageSize;
        this.showTotal=showTotal;
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

    public boolean isShowTotal() {
        return showTotal;
    }

    public void setShowTotal(boolean showTotal) {
        this.showTotal = showTotal;
    }
}
