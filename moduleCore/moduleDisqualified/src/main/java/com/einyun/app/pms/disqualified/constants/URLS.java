package com.einyun.app.pms.disqualified.constants;

public class URLS {
    //获取问询类型
    public static final String URL_GET_QUIRIES_TYPES ="workOrder/workOrder/bizData/v1/getBaseList?categoryKey=workorder_type_and_line";
    //获取 条线 状态 严重程度
    public static final String URL_GET_LINE_STATE_LIST ="/portal/sys/dataDict/v1/getByTypeKey?typeKey=";
    //获取工单编号
    public static final String URL_GET_ORDER_CODE ="/portal/sys/identity/v1/getNextIdByAlias?alias=bhggdbh";
    //创建不合格单
    public static final String URL_CREATE_ORDER ="/workOrder/workOrder/unqualified/v1/start";
    //待跟进列表
    public static final String URL_GET_TO_FOLLOW_UP_LIST ="/workOrder/workOrder/task/v1/getTodoListAliaForApp/unqualified_key/NORMAL";
    //已跟进列表
    public static final String URL_GET_HAVE_TO_FOLLOW_UP_LIST ="/workOrder/workOrder/task/v1/getDoneListAliaForApp/unqualified_key";
    //工单列表
    public static final String URL_GET_ORDER_LIST ="/workOrder/workOrder/task/v1/getWorkListBydefKeyForApp?defKey=unqualified_key";
    //待跟进详情
    public static final String URL_GET_TO_FOLLOW_UP_DETAIL ="/bpm-runtime/runtime/task/v1/taskDetailMini?taskId=";
    //已跟进详情
    public static final String URL_GET_HAVE_TO_FOLLOW_UP_DETAIL ="/bpm-runtime/runtime/instance/v1/getInstBO?proInstId=";
    //反馈接口
    public static final String URL_FEED_BACH ="/workOrder/workOrder/unqualified/v1/feedback";
    //验证接口
    public static final String URL_VERIFICATION ="/workOrder/workOrder/unqualified/v1/validate";
}
