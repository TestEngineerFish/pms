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

    public static final String FORCE_CLOSE_REPAIR="FORCE_CLOSE_REPAIR";//强制闭单 客户报修
    public static final String FORCE_CLOSE_COMPLAIN="FORCE_CLOSE_COMPLAIN";//强制闭单 客户投诉
    public static final String FORCE_CLOSE_ENQUIRY="FORCE_CLOSE_ENQUIRY";//强制闭单 客户问询

    public static final String POSTPONED_COMPLAIN="POSTPONED_COMPLAIN";//工单延期 客户投诉
    public static final String POSTPONED_REPAIR="POSTPONED_REPAIR";//工单延期 客户报修




}
