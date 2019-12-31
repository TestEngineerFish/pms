package com.einyun.app.library.resource.workorder.net.request;

import com.einyun.app.base.paging.bean.Query;

import java.util.ArrayList;
import java.util.List;

public class OrderPreviewRequest extends PageRquest {
    private List<query> querys = new ArrayList();
    private List<sorter> sorters = new ArrayList<>();
    private String typeRe = "";

    public OrderPreviewRequest() {
        querys.add(new query());
        sorters.add(new sorter());
    }

    public void addTiaoXian(String txId) {
        query query = new query("line", txId);
        querys.add(query);
    }

    public class query {
        private String property = "queryRange";
        private String relation = "AND";
        private String value = "3";
        private String operation = "EQUAL";
        private String group = "";

        public query() {
        }

        public query(String property, String value) {
            this.property = property;
            this.value = value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private class sorter {
        private String direction = "ASC";
        private String property = "planExecutionDate";
    }

    public String getTypeRe() {
        return typeRe;
    }

    public void setTypeRe(String typeRe) {
        this.typeRe = typeRe;
    }

    public List<query> getQuerys() {
        return querys;
    }

    public void setQuerys(List<query> querys) {
        this.querys = querys;
    }

    public List<sorter> getSorters() {
        return sorters;
    }

    public void setSorters(List<sorter> sorters) {
        this.sorters = sorters;
    }
}
