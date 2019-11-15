package com.einyun.app.pms.main.model;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.main.model
 * @ClassName: HomeModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/02 15:26
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/02 15:26
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class HomeModel {
    private String workBeanTagName;
    private String mineTagName;

    public HomeModel(String workBeanTagName, String mineTagName) {
        this.workBeanTagName = workBeanTagName;
        this.mineTagName = mineTagName;
    }

    public String getWorkBeanTagName() {
        return workBeanTagName;
    }

    public void setWorkBeanTagName(String workBeanTagName) {
        this.workBeanTagName = workBeanTagName;
    }

    public String getMineTagName() {
        return mineTagName;
    }

    public void setMineTagName(String mineTagName) {
        this.mineTagName = mineTagName;
    }
}
