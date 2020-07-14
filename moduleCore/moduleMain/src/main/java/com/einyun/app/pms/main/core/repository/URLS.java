package com.einyun.app.pms.main.core.repository;

public class URLS {
    public static final String URL_GET_USER_STARS="user-center/api/usercenter/v1/ucUser/userDetails";
    //消息是否已读
    public static final String URL_GET_HAS_READ="portal/innermsg/messageReceiver/v1/getIsReadMsg";
    //扫码资源代办工单
    public static final String URL_GET_RES_WAIT="resource/res-order/resource/v2/merge/wait";
    //扫码资源代办工单
    public static final String URL_GET_PATROL_HISTORY="resource/res-order/resource/v2/merge/history";
    //扫码巡更点基本信息
    public static final String URL_GET_PATROL_BASIC="resource/res-order/resource/v2/detail/merge";
    //获取环境分类
    public static final String URL_GET_ENVIROMENT_TYPE="sys/sysType/v1/getTypesByTypeKeyAndGroupKey?typeKey=environmental_classification&groupKey=RESOURCE_TYPE";

}
