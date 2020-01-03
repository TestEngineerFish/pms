package com.einyun.app.base.paging.bean;

import com.orhanobut.logger.Logger;

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
        if(total>0){
            int totalPage=total/pageSize;
            if(total%pageSize!=0){
                totalPage=totalPage+1;
                Logger.d("totalPage->"+totalPage);
            }
            if(totalPage+1>page){
                return true;
            }
        }
        if(rows!=null&&rows.size()>0){
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        if(total<=0&&page<=1){
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
        if (total == null || total ==-1)
            if (rows == null){
                total = 0;
            }else{
                total = rows.size();
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
