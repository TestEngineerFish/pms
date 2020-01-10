package com.einyun.app.pms.disqualified.constants;

public class URLS {
    //获取问询类型
    public static final String URL_GET_QUIRIES_TYPES ="workOrder/workOrder/bizData/v1/getBaseList?categoryKey=workorder_type_and_line";

    //待跟进列表
    public static final String URL_GET_TO_FOLLOW_UP_LIST ="workOrder/workOrder/task/v1/getTodoListAliaForApp/customer_enquiry_flow/NORMAL,DELIVERTO";
    //获取 条线 状态 严重程度
    public static final String URL_GET_LINE_STATE_LIST ="/portal/sys/dataDict/v1/getByTypeKey?typeKey=";
    //获取工单编号
    public static final String URL_GET_ORDER_CODE ="/portal/sys/identity/v1/getNextIdByAlias?alias=bhggdbh";
    //创建不合格单
    public static final String URL_CREATE_ORDER ="/workOrder/workOrder/unqualified/v1/start";
}
