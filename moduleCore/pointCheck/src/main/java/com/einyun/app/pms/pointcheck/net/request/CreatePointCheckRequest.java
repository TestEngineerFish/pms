package com.einyun.app.pms.pointcheck.net.request;

import com.einyun.app.pms.pointcheck.model.ProjectResultModel;

import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.net.request
 * @ClassName: CreatePointCheckRequest
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 10:20
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 10:20
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CreatePointCheckRequest {
    private String checkProjectId;
    private String remark;
    private String picUrl;
    private String massifId;
    private String createId;
    private List<ProjectResultModel> results;
    private int recordSource=2;  // 1.web 2.app

    public String getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(String checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMassifId() {
        return massifId;
    }

    public void setMassifId(String massifId) {
        this.massifId = massifId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public List<ProjectResultModel> getResults() {
        return results;
    }

    public void setResults(List<ProjectResultModel> results) {
        this.results = results;
    }

    public int getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(int recordSource) {
        this.recordSource = recordSource;
    }
}
