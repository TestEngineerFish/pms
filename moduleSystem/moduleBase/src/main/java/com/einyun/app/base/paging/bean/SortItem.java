package com.einyun.app.base.paging.bean;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.paging.bean
 * @ClassName: SortItem
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/11 16:00
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/11 16:00
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SortItem {
    private String direction;
    private String property;

    public SortItem(String direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
