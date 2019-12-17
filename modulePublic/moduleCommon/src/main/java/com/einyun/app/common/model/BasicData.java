package com.einyun.app.common.model;

import com.einyun.app.library.resource.model.LineType;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;

import java.util.List;

public class BasicData {
    private List<ResourceTypeBean> resources; //所有资源
    private List<WorkOrderTypeModel> lines;  //获取条线
    private List<LineType> listLineTypes;  //获取分类 环境，工程，秩序

    public List<ResourceTypeBean> getResources() {
        return resources;
    }

    public void setResources(List<ResourceTypeBean> resources) {
        this.resources = resources;
    }

    public List<WorkOrderTypeModel> getLines() {
        return lines;
    }

    public void setLines(List<WorkOrderTypeModel> lines) {
        this.lines = lines;
    }

    public List<LineType> getListLineTypes() {
        return listLineTypes;
    }

    public void setListLineTypes(List<LineType> listLineTypes) {
        this.listLineTypes = listLineTypes;
    }
}
