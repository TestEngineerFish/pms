package com.einyun.app.pms.pointcheck.model;

import java.util.List;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.model
 * @ClassName: ProjectContentModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/08 16:57
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/08 16:57
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ProjectContentModel {
    private String objectId;
    private List<ProjectContentItemModel> contentList;
    private String specificLocation;
    private String resourceName;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public List<ProjectContentItemModel> getContentList() {
        return contentList;
    }

    public void setContentList(List<ProjectContentItemModel> contentList) {
        this.contentList = contentList;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
