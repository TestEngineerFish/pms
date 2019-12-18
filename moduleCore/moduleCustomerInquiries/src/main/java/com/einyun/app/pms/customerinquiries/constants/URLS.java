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
}
