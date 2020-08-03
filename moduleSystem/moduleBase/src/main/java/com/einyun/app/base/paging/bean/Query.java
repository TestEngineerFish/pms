package com.einyun.app.base.paging.bean;

import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.paging.bean
 * @ClassName: Query
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/11 15:54
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/11 15:54
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class Query {
    public static String OPERATION_EQUAL="EQUAL";
    public static String RELATION_AND="AND";
    public static String GROUP="one";
    public static String SORT_DESC="DESC";
    public static String SORT_ASC="ASC";
    public static String IN="IN";
    private PageBean pageBean;
    private QueryBuilder.Params params;
    private List<QueryItem<?>> querys;
    private List<SortItem> sorter;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }


    public QueryBuilder.Params getParams() {
        return params;
    }

    public void setParams(QueryBuilder.Params params) {
        this.params = params;
    }

    public List<QueryItem<?>> getQuerys() {
        return querys;
    }

    public void setQuerys(List<QueryItem<?>> querys) {
        this.querys = querys;
    }

    public List<SortItem> getSorter() {
        return sorter;
    }

    public void setSorter(List<SortItem> sorter) {
        this.sorter = sorter;
    }
}
