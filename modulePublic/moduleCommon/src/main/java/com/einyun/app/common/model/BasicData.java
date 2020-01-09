package com.einyun.app.common.model;

import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.model.LineType;
import com.einyun.app.library.resource.workorder.model.PreviewSelectModel;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.workorder.model.AreaModel;
import com.einyun.app.library.workorder.model.TypeAndLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicData {
    private List<ResourceTypeBean> resources; //所有资源
    private List<WorkOrderTypeModel> lines;  //获取条线
    private List<LineType> listLineTypes;  //获取分类 环境，工程，秩序
    private Map<String, DivideGrid> divideGridMap = new HashMap<>();//网格-楼栋-单元
    private List<TypeAndLine> complainTypes; //投诉类型
    private List<DictDataModel> complainPropertys; //投诉性质
    private AreaModel repairArea;//报修区域
    private List<PreviewSelectModel> previewSelect = new ArrayList<>();//工单预览筛选
    private Map<String, List<DictDataModel>> dictDataModelMap = new HashMap<>();//网格-楼栋-单元

    public Map<String, List<DictDataModel>> getDictDataModelMap() {
        return dictDataModelMap;
    }

    public void setDictDataModelMap(Map<String, List<DictDataModel>> dictDataModelMap) {
        this.dictDataModelMap = dictDataModelMap;
    }

    public void setDivideGridMap(Map<String, DivideGrid> divideGridMap) {
        this.divideGridMap = divideGridMap;
    }

    public void setResources(List<ResourceTypeBean> resources) {
        this.resources = resources;
    }

    public void setLines(List<WorkOrderTypeModel> lines) {
        this.lines = lines;
    }

    public void setListLineTypes(List<LineType> listLineTypes) {
        this.listLineTypes = listLineTypes;
    }

    public void setComplainPropertys(List<DictDataModel> complainPropertys) {
        this.complainPropertys = complainPropertys;
    }

    public void setComplainTypes(List<TypeAndLine> complainTypes) {
        this.complainTypes = complainTypes;
    }

    public void setRepairArea(AreaModel repairArea) {
        this.repairArea = repairArea;
    }

    public void setPreviewSelect(List<PreviewSelectModel> previewSelect) {
        this.previewSelect = previewSelect;
    }

    public Map<String, DivideGrid> getDivideGridMap() {
        return divideGridMap;
    }

    public List<ResourceTypeBean> getResources() {
        return resources;
    }

    public List<WorkOrderTypeModel> getLines() {
        return lines;
    }

    public List<LineType> getListLineTypes() {
        return listLineTypes;
    }

    public List<TypeAndLine> getComplainTypes() {
        return complainTypes;
    }

    public List<DictDataModel> getComplainPropertys() {
        return complainPropertys;
    }

    public AreaModel getRepairArea() {
        return repairArea;
    }

    public List<PreviewSelectModel> getPreviewSelect() {
        return previewSelect;
    }

}
