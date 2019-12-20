package com.einyun.app.base.paging.bean;

import android.text.TextUtils;

import org.mockito.internal.matchers.Any;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.paging.bean
 * @ClassName: QueryBuilder
 * @Description: 查询构造器
 * @Author: chumingjun
 * @CreateDate: 2019/10/17 15:29
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/17 15:29
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class QueryBuilder {
    QueryBuilder builder;
    private Query query;
    private PageBean pageBean;
    private List<QueryItem<?>> items;
    private List<SortItem> sorts;

    public QueryBuilder() {
        query = new Query();
        builder = this;
    }

    public QueryBuilder setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
        query.setPageBean(pageBean);
        return builder;
    }

    public QueryBuilder setPageBean(int page, int pageSize) {
        this.pageBean = new PageBean(page, pageSize);
        query.setPageBean(pageBean);
        return builder;
    }

    public QueryBuilder setPageBean(int page, int pageSize, boolean showTotal) {
        this.pageBean = new PageBean(page, pageSize, showTotal);
        query.setPageBean(pageBean);
        return builder;
    }

    public QueryBuilder addQueryItem(String property, Object value) {
        QueryItem<Object> item = new QueryItem();
        item.setProperty(property);
        item.setValue(value);
        checkItems();
        items.add(item);
        return builder;
    }



    private void checkItems() {
        if (items == null) {
            items = new ArrayList<>();
            query.setQuerys(items);
        }
    }

    public QueryBuilder addQueryItem(String property, String value, String relation) {
        if (!TextUtils.isEmpty(value)) {
            QueryItem<String> item = new QueryItem();
            item.setProperty(property);
            item.setValue(value);
            item.setRelation(relation);
            checkItems();
            items.add(item);
        }
        return builder;
    }

    public QueryBuilder addQueryItem(String property, String value, String operation, String relation) {
        if (!TextUtils.isEmpty(value)) {
            QueryItem<String> item = new QueryItem();
            item.setProperty(property);
            item.setValue(value);
            item.setOperation(operation);
            item.setRelation(relation);
            checkItems();
            items.add(item);
        }
        return builder;
    }

    public QueryBuilder addSort(String property, String direction) {
        SortItem sortItem = new SortItem(property, direction);
        if (sorts == null) {
            sorts = new ArrayList<>();
            query.setSorter(sorts);
        }
        sorts.add(sortItem);
        return builder;
    }

    public Query build() {
        return query;
    }
}
