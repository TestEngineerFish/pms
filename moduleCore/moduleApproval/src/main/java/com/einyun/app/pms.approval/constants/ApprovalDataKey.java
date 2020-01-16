package com.einyun.app.pms.approval.constants;

public class ApprovalDataKey {
    public static final String APPROVAL_SUMIT_AGREE="agree";
    public static final String APPROVAL_SUMIT_REJECT="reject";
    public static final String APPROVAL_FRAGMENT_REFRESH="APPROVAL_FRAGMENT_REFRESH";


    public static final String APPROVAL_STATE_HAD_PASS="approve";//已通过
    public static final String APPROVAL_STATE_HAD_UNPASS="reject";//已驳回
    public static final String APPROVAL_STATE_PENDING="submit";// 待审批
    public static final String APPROVAL_STATE_IN_APPROVAL="in_approval";//审批中

    public static final String APPROVAL_SUMIT_PARMS="APPROVAL_SUMIT_PARMS";//提交参数
    public static final String APPROVAL_SUMIT_URL="APPROVAL_SUMIT_URL";//提交url

    /**
     * 强制闭单子类
     */
    public static final String FORCE_CLOSE_REPAIR="FORCE_CLOSE_REPAIR";//强制闭单 客户报修
    public static final String FORCE_CLOSE_COMPLAIN="FORCE_CLOSE_COMPLAIN";//强制闭单 客户投诉
    public static final String FORCE_CLOSE_ENQUIRY="FORCE_CLOSE_ENQUIRY";//强制闭单 客户问询
    public static final String FORCE_CLOSE_PATROL="FORCE_CLOSE_PATROL";//强制闭单 巡查工单
    public static final String FORCE_CLOSE_PLAN="FORCE_CLOSE_PLAN";//强制闭单 计划工单
    public static final String FORCE_CLOSE_ALLOCATE="FORCE_CLOSE_ALLOCATE";//强制闭单 派工单
    /**
     * 工单延期子类
     */
    public static final String POSTPONED_COMPLAIN="POSTPONED_COMPLAIN";//工单延期 客户投诉
    public static final String POSTPONED_REPAIR="POSTPONED_REPAIR";//工单延期 客户报修
    public static final String POSTPONED_PATROL="POSTPONED_PATROL";//工单延期 巡查工单
    public static final String POSTPONED_PLAN="POSTPONED_PLAN";//工单延期 计划工单
    public static final String POSTPONED_ALLOCATE="POSTPONED_ALLOCATE";//工单延期 派工单
    /**
     * 修改计划子类
     */
    public static final String UPDATE_PATROL_PLAN="UPDATE_PATROL_PLAN";//修改计划 巡查计划
    public static final String UPDATE_WORK_PLAN="UPDATE_WORK_PLAN";//修改计划 工作计划

    /**
     * 创建计划子类
     */
    public static final String CREATE_PATROL_PLAN="CREATE_PATROL_PLAN";//创建计划 巡查计划
    public static final String CREATE_WORK_PLAN="CREATE_WORK_PLAN";//创建计划 工作计划

    /**
     * 大类
     */
    public static final String INNER_AUDIT_CREATE_PLAN="INNER_AUDIT_CREATE_PLAN";//创建计划大类
    public static final String INNER_AUDIT_POSTPONED="INNER_AUDIT_POSTPONED";//工单延期大类
    public static final String INNER_AUDIT_UPDATE_PLAN="INNER_AUDIT_UPDATE_PLAN";//修改计划大类
    public static final String INNER_AUDIT_FORCE_CLOSE="INNER_AUDIT_FORCE_CLOSE";//强制闭单大类
}
