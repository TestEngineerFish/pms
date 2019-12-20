package com.einyun.app.pms.customerinquiries.constants;

public class URLS {
    //获取问询类型
    public static final String URL_GET_QUIRIES_TYPES ="workOrder/workOrder/bizData/v1/getBaseList?categoryKey=workorder_type_and_line";

    //待跟进列表
    public static final String URL_GET_TO_FOLLOW_UP_LIST ="work-order/workOrder/task/v1/getTodoListAliaForApp/customer_enquiry_flow/NORMAL,DELIVERTO";

    //待反馈
    public static final String URL_GET_TO_FEED_BACK_LIST ="work-order/workOrder/task/v1/getTodoListAliaForApp/customer_enquiry_flow/COMMU";

    //已跟进
    public static final String URL_GET_HAVE_TO_FOLLOW_UP_LIST ="work-order/workOrder/task/v1/getDoneListAliaForApp/customer_enquiry_flow";

    //已办结
    public static final String URL_GET_TRANSFERRED_TO_LIST ="work-order/workOrder/task/v1/getCompletedAliaForApp/customer_enquiry_flow";

    //抄送我
    public static final String URL_GET_COPY_ME_LIST ="work-order/workOrder/task/v1/getReceiverCopyToForApp/customer_enquiry_flow";
    //获取详情基本信息
    public static final String URL_GET_INQUIRIES_DETAIL_INFO ="/bpm-runtime/runtime/instance/v1/getInstBO?proInstId=";
    //处理提交
    public static final String URL_GET_INQUIRIES_DEAL ="/workOrder/workOrder/customerEnquiry/v1/complete";
    //处理保存
    public static final String URL_GET_INQUIRIES_DEAL_SAVE ="/workOrder/workOrder/saveDraft/v1/saveHandle";
    //评价
    public static final String URL_GET_INQUIRIES_DEAL_EVALUATION ="/workOrder/workOrder/customerEnquiry/v1/complete";
    //获取反馈
    public static final String URL_GET_FEEDBACK_INFO ="/bpm-runtime/runtime/task/v1/getTaskCommu?taskId=";
}
