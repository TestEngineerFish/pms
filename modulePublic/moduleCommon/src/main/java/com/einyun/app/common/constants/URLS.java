package com.einyun.app.common.constants;

public class URLS {
    //单个已读
    public static final String URL_GET_SINGLE_READ="portal/innermsg/messageReceiver/v1/get?id=";
    //计划工单接单
    public static final String URL_GET_RECEIVE_ORDER="resource-workorder/res-order/plan/receiveOrder";
    //计划工单派单
    public static final String URL_GET_ASSIGNE_ORDER="resource-workorder/res-order/plan/zhipai";
    //巡查巡更工单接单
    public static final String URL_GET_PRATROL_RECEIVE_ORDER="resource-workorder/res-order/patrol/receiveOrder";
    //巡查巡更工单派单
    public static final String URL_GET_PATROL_ASSIGNE_ORDER="resource-workorder/res-order/patrol/zhipai";
    //审批详情
//    public static final String URL_GET_APPROVAL_BASIC_INFO="bpm-runtime/runtime/instance/v1/getInstBO?proInstId=";
    public static final String URL_GET_APPROVAL_BASIC_INFO="/bpm-runtime/runtime/task/v1/taskDetailMini?taskId=";

    //待跟进详情
    public static final String URL_GET_TO_FOLLOW_UP_DETAIL ="/bpm-runtime/runtime/task/v1/taskDetailMini?taskId=";
    //判断是否可以处理
    public static final String URL_IS_CAN_DEAL ="/workOrder/workOrder/task/v1/getMsgAction?taskId=";
}