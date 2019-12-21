package com.einyun.app.common.model;

import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.resource.model.LineType;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicData {
    private List<ResourceTypeBean> resources; //所有资源
    private List<WorkOrderTypeModel> lines;  //获取条线
    private List<LineType> listLineTypes;  //获取分类 环境，工程，秩序
    private Map<String, DivideGrid> divideGridMap=new HashMap<>();//网格-楼栋-单元

    public Map<String, DivideGrid> getDivideGridMap() {
        return divideGridMap;
    }

    public void setDivideGridMap(Map<String, DivideGrid> divideGridMap) {
        this.divideGridMap = divideGridMap;
    }

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
