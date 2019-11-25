package com.example.shimaostaff.pointcheck.model;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.model
 * @ClassName: ProjectModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 15:27
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 15:27
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ProjectModel {
    private String id;
    private String checkName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }
}
