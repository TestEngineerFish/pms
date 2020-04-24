package com.einyun.app.pms.approval.net;

public class URL {
    //获取审批详情页基本信息
    public static final String URL_GET_APPROVAL_BASIC_INFO="bpm-runtime/runtime/instance/v1/getInstBO?proInstId=";
    //获取审批详情页 审批信息
    public static final String URL_GET_APPROVAL_DETAIL_INFO="workOrder/workOrder/workOrderInnerAudit/v1/getAuditHisByAuditId?id=";
    //提交审批
    public static final String URL_CREATE_WORK_PLAN="resource/api/resource/v1/workPlan/approve";
    //
    public static final String URL_GET_PATROL_TYPE="resource-workorder/res-order/patrol/doneDetail";
}
