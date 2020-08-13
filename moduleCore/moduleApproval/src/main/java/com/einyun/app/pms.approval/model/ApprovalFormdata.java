package com.einyun.app.pms.approval.model;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ApprovalFormdata {
    private String code;
    private String apply_reason;
    private String applyReason;
    private String applicationDescription;
    private String parent_id;
    private int delay_sum_time;
    private int delay_number;
    private String task_id;
    private String delay_time;
    private String node_id;

    private String line;
    private String lineName;
    private String flowType;
    private String dispatchFlowType;
    private String procName;

    private String attachment;
    private String workOrderCategory;
    private String category;
    private String repairArea;
    private String creationTime;
    private String deadlineTime;
    private String extensionDays;

    private String planName;

    private String resourceName;
    private String invalidReasonCategory;

    private String property;
    private String setToInvalid;

    private String workPlanName;
    private String resourceClassificationName;
    private String workGuidanceName;
    private String effectivePeriod;

    private String gridName;
    private String buildingName;
    private String unitName;
    private String floor;

    private String principal;

    private String inspectionName;
    private String inspectionTypeName;
    private String inspectionWorkGuidanceName;

    private String gridHousekeeper;

    private String adaptationFloor;

    private String applyFiles;

    private String extensionApplicationId;

    private String applyTaskId;

    private String approvalId;

    private List<String> frequency;
    private String work_ascription="";

    public String getWork_ascription() {
        return work_ascription;
    }

    public void setWork_ascription(String work_ascription) {
        this.work_ascription = work_ascription;
    }

    public ApprovalFormdata() {

    }

    public List<String> getFrequency() {
        return frequency;
    }

    public void setFrequency(List<String> frequency) {
        this.frequency = frequency;
    }

    public String getApprovalId() {
        return approvalId == null ? "" : approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApplyTaskId() {
        return applyTaskId == null ? "" : applyTaskId;
    }

    public void setApplyTaskId(String applyTaskId) {
        this.applyTaskId = applyTaskId;
    }

    public String getExtensionApplicationId() {
        return extensionApplicationId == null ? "" : extensionApplicationId;
    }

    public void setExtensionApplicationId(String extensionApplicationId) {
        this.extensionApplicationId = extensionApplicationId;
    }

    public String getAdaptationFloor() {
        return adaptationFloor == null ? "" : adaptationFloor;
    }

    public void setAdaptationFloor(String adaptationFloor) {
        this.adaptationFloor = adaptationFloor;
    }

    public String getGridHousekeeper() {
        return gridHousekeeper == null ? "" : gridHousekeeper;
    }

    public void setGridHousekeeper(String gridHousekeeper) {
        this.gridHousekeeper = gridHousekeeper;
    }

    public String getInspectionWorkGuidanceName() {
        return inspectionWorkGuidanceName == null ? "" : inspectionWorkGuidanceName;
    }

    public void setInspectionWorkGuidanceName(String inspectionWorkGuidanceName) {
        this.inspectionWorkGuidanceName = inspectionWorkGuidanceName;
    }

    public String getInspectionName() {
        return inspectionName == null ? "" : inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getInspectionTypeName() {
        return inspectionTypeName == null ? "" : inspectionTypeName;
    }

    public void setInspectionTypeName(String inspectionTypeName) {
        this.inspectionTypeName = inspectionTypeName;
    }

    public String getPrincipal() {
        return principal == null ? "" : principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getGridName() {
        return gridName == null ? "" : gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getBuildingName() {
        return buildingName == null ? "" : buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName == null ? "" : unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFloor() {
        return floor == null ? "" : floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getWorkPlanName() {
        if (workPlanName==null) {
            workPlanName=inspectionName;
        }
        return workPlanName == null ? "" : workPlanName;
    }

    public void setWorkPlanName(String workPlanName) {
        this.workPlanName = workPlanName;
    }

    public String getResourceClassificationName() {
        if (resourceClassificationName==null) {
            resourceClassificationName=inspectionTypeName;
        }
        return resourceClassificationName == null ? "" : resourceClassificationName;
    }

    public void setResourceClassificationName(String resourceClassificationName) {
        this.resourceClassificationName = resourceClassificationName;
    }

    public String getWorkGuidanceName() {
        if (workGuidanceName==null) {
            workGuidanceName=inspectionWorkGuidanceName;
        }
        return workGuidanceName == null ? "" : workGuidanceName;
    }

    public void setWorkGuidanceName(String workGuidanceName) {
        this.workGuidanceName = workGuidanceName;
    }

    public String getEffectivePeriod() {
        return effectivePeriod == null ? "" : effectivePeriod;
    }

    public void setEffectivePeriod(String effectivePeriod) {
        this.effectivePeriod = effectivePeriod;
    }

    public String getSetToInvalid() {
        return setToInvalid == null ? "" : setToInvalid;
    }

    public void setSetToInvalid(String setToInvalid) {
        this.setToInvalid = setToInvalid;
    }

    public String getInvalidReasonCategory() {
        return invalidReasonCategory == null ? "" : invalidReasonCategory;
    }

    public void setInvalidReasonCategory(String invalidReasonCategory) {
        this.invalidReasonCategory = invalidReasonCategory;
    }

    public String getProperty() {
        return property == null ? "" : property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getResourceName() {
        return resourceName == null ? "" : resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApplyFiles() {
        return applyFiles == null ? "" : applyFiles;
    }

    public void setApplyFiles(String applyFiles) {
        this.applyFiles = applyFiles;
    }

    public String getAttachment() {
        if (attachment==null||attachment.isEmpty()) {
            attachment=applyFiles;
        }
        return attachment==null?"":attachment;
    }

    public List<ApprovalAttachment> getShownAttachment() {
        if (attachment == null || attachment.length() == 0) {
            List<ApprovalAttachment> applyFilesAttachment = new ArrayList<>();
            String applyFiles = getApplyFiles();
            if (applyFiles.length() > 0) {
                String files[] = applyFiles.split(",");

                for (int i = 0; i < files.length; i++) {
                    applyFilesAttachment.add(new ApprovalAttachment("-1", "", 0, files[i]));
                }
            }

            return applyFilesAttachment;
        } else {
            return JSON.parseArray(attachment, ApprovalAttachment.class);
        }
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getWorkOrderCategory() {
        return workOrderCategory == null ? "" : workOrderCategory;
    }

    public void setWorkOrderCategory(String workOrderCategory) {
        this.workOrderCategory = workOrderCategory;
    }

    public String getApplyReason() {
        String lkj = applyReason == null ? "" : applyReason;

        return lkj;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getApplicationDescription() {
        if (applicationDescription==null) {
            applicationDescription=apply_reason;
        }
        return applicationDescription == null ? "" : applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public String getCategory() {
        return category == null ? "" : category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRepairArea() {
        return repairArea == null ? "" : repairArea;
    }

    public void setRepairArea(String repairArea) {
        this.repairArea = repairArea;
    }

    public String getApply_reason() {
        return apply_reason == null ? "" : apply_reason;
    }

    public void setApply_reason(String apply_reason) {
        this.apply_reason = apply_reason;
    }

    public String getParent_id() {
        return parent_id == null ? "" : parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getDelay_sum_time() {
        return delay_sum_time;
    }

    public void setDelay_sum_time(int delay_sum_time) {
        this.delay_sum_time = delay_sum_time;
    }

    public int getDelay_number() {
        return delay_number;
    }

    public void setDelay_number(int delay_number) {
        this.delay_number = delay_number;
    }

    public String getTask_id() {
        return task_id == null ? "" : task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getDelay_time() {
        return delay_time == null ? "" : delay_time;
    }

    public void setDelay_time(String delay_time) {
        this.delay_time = delay_time;
    }

    public String getNode_id() {
        return node_id == null ? "" : node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getLine() {
        if (line==null) {
            line=lineName;
        }
        return line == null ? "" : line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getFlowType() {
        if (flowType==null) {
            flowType=category;
        }
        return flowType == null ? "" : flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getDispatchFlowType() {
        return dispatchFlowType == null ? "" : dispatchFlowType;
    }

    public void setDispatchFlowType(String dispatchFlowType) {
        this.dispatchFlowType = dispatchFlowType;
    }

    public String getProcName() {
        return procName == null ? "" : procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getPlanName() {
        return planName == null ? "" : planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCreationTime() {
        if (creationTime == null) {
            return "";
        }

        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDeadlineTime() {
        if (deadlineTime == null) {
            return "";
        }

        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getExtensionDays() {
        if (extensionDays==null) {
            extensionDays=delay_time;
        }
        return extensionDays == null ? "" : extensionDays;
    }

    public void setExtensionDays(String extensionDays) {
        this.extensionDays = extensionDays;
    }

    public String getType() {
        if (flowType != null) {
            return flowType;
        }

        if (category != null) {
            return category;
        }

        if (workOrderCategory != null) {
            return workOrderCategory;
        }

        return "";
    }


    public String getDelayDays() {
        if (extensionDays != null) {
            return extensionDays;
        }

        return String.format("%d", delay_sum_time);
    }

//        public String getReason() {
//            if (applyReason != null && applyReason.length() > 0) {
//                return applyReason;
//            }
//
//            if (applicationDescription != null && applicationDescription.length() > 0) {
//                return applicationDescription;
//            }
//
//            if (apply_reason != null && apply_reason.length() > 0) {
//                return apply_reason;
//            }
//
//            return "";
//        }
public static class ApprovalAttachment {
    private String id;
    private String name;
    private int size;
    private String path;

    public ApprovalAttachment() {

    }

    public ApprovalAttachment(String id, String name, int size, String path) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.path = path;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
}
