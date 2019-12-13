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
    private Integer page;
    private Integer pageSize;
    private Integer total;
    private List<T> rows;

    public Boolean hasNextPage(){
        if(rows!=null&&rows.size()>=0){
            return true;
        }
        return false;
    }

    public int nextPage(){
        page++;
        return page;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        if (total == -1) {
            if (rows == null){
                total = 0;
            }else{
                total = rows.size();
            }
        }
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
