package com.einyun.app.common.constants;

public class URLS {
    //单个已读
    public static final String URL_GET_SINGLE_READ="portal/innermsg/messageReceiver/v1/get?id=";
    //计划工单接单
    public static final String URL_GET_RECEIVE_ORDER="resource-workorder/res-order/plan/receiveOrder ";
    //计划工单派单
    public static final String URL_GET_ASSIGNE_ORDER="resource-workorder/res-order/plan/zhipai";
    //审批详情
//    public static final String URL_GET_APPROVAL_BASIC_INFO="bpm-runtime/runtime/instance/v1/getInstBO?proInstId=";
    public static final String URL_GET_APPROVAL_BASIC_INFO="/bpm-runtime/runtime/task/v1/taskDetailMini?taskId=";

    //待跟进详情
    public static final String URL_GET_TO_FOLLOW_UP_DETAIL ="/bpm-runtime/runtime/task/v1/taskDetailMini?taskId=";
}