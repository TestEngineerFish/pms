package com.einyun.app.pms.mine.constants;

public class URLS {
    public static final String URL_SUMIT_FEED_BACK="appcenter/api/appcenter/v1/feedback/add";
    public static final String URL_GET_USER_INFO_BY_ACCOUNT="uc/api/user/v1/user/getUser?account=";
    public static final String URL_GET_USER_SIGN_TEXT="appcenter/api/appcenter/v1/userSloganRef/getByUserId/";
    public static final String URL_GET_NEW_USER_SIGN_TEXT="appcenter/api/appcenter/v1/userSloganRef/newGetByUserId/";
    public static final String URL_GET_USER_STARS="user-center/api/usercenter/v1/ucUser/userDetails";
    public static final String URL_GET_UPLOAD_PIC="/uc/api/user/v1/user/updateUser";
    public static final String URL_SET_SIGN_TEXT="appcenter/api/appcenter/v1/userSloganRef/addOrUpdateSlogan";
    //消息列表
    public static final String URL_GET_MSG_LIST="portal/innermsg/messageReceiver/v1/list";
    //单个已读
    public static final String URL_GET_SINGLE_READ="portal/innermsg/messageReceiver/v1/get?id=";
    //全部已读
    public static final String URL_GET_ALL_READ="/portal/innermsg/messageReceiver/v1/signAllReadMsg";

}