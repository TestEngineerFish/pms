package com.example.shimaostaff.pointcheck.model;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.model
 * @ClassName: ProjectResultModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 10:53
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 10:53
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ProjectResultModel {
    private String checkContentId;
    private String checkResult;

    public String getCheckContentId() {
        return checkContentId;
    }

    public void setCheckContentId(String checkContentId) {
        this.checkContentId = checkContentId;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }
}
