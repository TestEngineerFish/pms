package com.einyun.app.base.paging.bean;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.paging.bean
 * @ClassName: QueryItem
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/11 15:58
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/11 15:58
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class QueryItem<T> {
    private String group;
    private boolean hasInitValue;
    private String operation=Query.OPERATION_EQUAL;
    private String paramName;
    private String property;
    private String relation;
    private T value;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isHasInitValue() {
        return hasInitValue;
    }

    public void setHasInitValue(boolean hasInitValue) {
        this.hasInitValue = hasInitValue;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
