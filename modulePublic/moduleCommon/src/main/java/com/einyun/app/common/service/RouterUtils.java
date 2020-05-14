package com.einyun.app.common.service;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.service
 * @ClassName: RouterUtils
 * @Description: 共享服务路由
 * @Author: chumingjun
 * @CreateDate: 2019/0 8/29 18:44
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/29 18:44
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RouterUtils {
    /**********************Service API******************************/
    public static final String SERVICE_USER="/user/service";
    public static final String SERVICE_MAIN="/main/service";
    public static final String SERVICE_EXTRA="/extra/service";

    /**********************Activity*********************************/
    public static final String ACTIVITY_USER_SPLASH="/user/SplashActivity";
    public static final String ACTIVITY_MAIN_HOME="/main/Home";
    public static final String ACTIVITY_SYSTEM_NOTICE_DETAIL = "/main/SystemNoticeDetailActivity";
    public static final String ACTIVITY_USER_LOGIN="/user/LoginActivity";
    public static final String ACTIVITY_REPAIRS_PAGING="/repairs/RepairsActivity";
    public static final String ACTIVITY_HEALTH_WORK="/health/HealthActivity";
    public static final String ACTIVITY_BLOCK_CHOOSE="/choose/BlockChooseActivity";
    public static final String ACTIVITY_X5_WEBVIEW="/webview/X5WebViewActivity";
    public static final String ACTIVITY_SCANNER="/common/ScannerActivity";
    public static final String ACTIVITY_ORDER_LIST="/orderList/OrderListActivity";
    public static final String ACTIVITY_ORDER_LIST_ALL="/orderList/OrderListAllActivity";
    public static final String ACTIVITY_ORDER_CONDITION_PANDECT="/main/OrderConditionPandectActivity";

    /**********************点检******************************/
    public static final String ACTIVITY_POINT_CHECK_ACTIVITY="/pointCheck/PointCheckListActivity";
    public static final String ACTIVITY_POINT_CHECK_DETIAL="/pointCheck/PointCheckDetialActivity";
    public static final String ACTIVITY_POINT_CHECK_CREATE="/pointCheck/PointCheckCreatActivity";

    /**********************巡查工单*************************/
    public static final String ACTIVITY_PATROL_LIST ="/patrol/PatrolListActivity";
    public static final String ACTIVITY_PATROL_DETIAL="/patrol/PatrolDetialActivity";
    public static final String ACTIVITY_PATROL_HANDLE="/patrol/PatrolHandleActivity";
    public static final String ACTIVITY_PATROL_TIME_HANDLE="/patrol/PatrolTimeHandleActivity";
    public static final String ACTIVITY_PATROL_TIME_DETIAL="/patrol/PatrolTimeDtialActivity";
    public static final String ACTIVITY_PATROL_TIME_QR_SIGNIN_HANDLE ="/patrol/PatrolQRSignInActivity";
    public static final String ACTIVITY_PATROL_TIME_QR_SIGNIN_DETIAL="/patrol/PatrolQRSignInDetialActivity";
    public static final String ACTIVITY_PATROL_TIME_QR_SCANNER="/patrol/PatrolSignInScannerActivity";
    public static final String ACTIVITY_PATROL_FORCE_CLOSE="/patrol/PatrolForceCloseActivity";
    public static final String ACTIVITY_PATROL_POSTPONE="/patrol/PatrolPostponeActivity";
    public static final String ACTIVITY_PATROL_PHOTO="/patrol/PatrolTimePhotoActivity";


    //计划工单
    public static final String ACTIVITY_PLAN_ORDER="/plan/PlanOrderActivity";
    public static final String ACTIVITY_PLAN_ORDER_DETAIL="/plan/PlanOrderDetailActivity";
//    public static final String ACTIVITY_LATE="/sendOrder/ApplyLateActivity";
//    public static final String ACTIVITY_CLOSE="/sendOrder/ApplyForceCloseActivity";
//    public static final String ACTIVITY_HISTORY="/sendOrder/HistoryActivity";
    /*********************工单预览*******************************/
    public static final String ACTIVITY_ORDER_PREVIEW ="/orderPreview/OrderPreviewActivity";
    /***********************运营收缴率************************************/
    public static final String ACTIVITY_OPERATE_PERCENT ="/operatePercent/OperatePercentActivity";
    public static final String ACTIVITY_OPERATE_TODAY_ALL_GET ="/operatePercent/AllChargeActivity";

    /**********************派工单****************************/
    //派工单首页
    public static final String ACTIVITY_SEND_ORDER="/sendOrder/SendOrderActivity";
    public static final String ACTIVITY_RESEND_ORDER="/sendOrder/ReSendOrderActivity";
    public static final String ACTIVITY_SEND_ORDER_DETAIL="/sendOrder/SendOrderDetailActivity";
    public static final String ACTIVITY_APPROVAL="/approval/ApprovalActivity";
    public static final String ACTIVITY_APPROVAL_DETAIL="/approval/ApprovalDetailActivity";
    public static final String ACTIVITY_SELECT_PEOPLE="/sendOrder/SelectPeopleActivity";
    public static final String ACTIVITY_LATE="/sendOrder/ApplyLateActivity";
    public static final String ACTIVITY_CLOSE="/sendOrder/ApplyForceCloseActivity";
    public static final String ACTIVITY_HISTORY="/sendOrder/HistoryActivity";
    public static final String ACTIVITY_CHOOSE_DISPOSE_PERSON_SEND_ORDER = "/sendOrder/choosePerson";
    /**********************创建工单****************************/
    public static final String ACTIVITY_CREATE_SEND_ORDER = "/create/sendOrder";
    public static final String ACTIVITY_CREATE_CLIENT_COMPLAIN_ORDER = "/create/clientComplainOrder";
    public static final String ACTIVITY_CREATE_CLIENT_REPAIRS_ORDER = "/create/clientRepairsOrder";
    public static final String ACTIVITY_CREATE_CLIENT_ENQUIRY_ORDER = "/create/clientEnquiryOrder";
    public static final String ACTIVITY_CHOOSE_DISPOSE_PERSON = "/create/choosePerson";
    public static final String ACTIVITY_ADD_COMPLAIN_INFO = "/create/AddComplainInfoActivity";
    /**********************Mine****************************/
    public static final String ACTIVITY_MINE_SETTING = "/mine/setting";
    public static final String ACTIVITY_USER_INFO = "/mine/userinfo";
    public static final String ACTIVITY_SIGN_SET = "/mine/signset";
    public static final String ACTIVITY_MESSAGE_CENTER = "/mine/messageCenter";
    public static final String ACTIVITY_USER_HEAD_SHOT = "/mine/userHeadShot";
    public static final String ACTIVITY_ADVICE_FEED_BACK = "/mine/advcieFeedBack";

    /**********************客户问询****************************/
    public static final String ACTIVITY_CUSTOMER_INQUIRIES = "/customerInquiries/CustomerInquiriesActivity";
    public static final String ACTIVITY_INQUIRIES_DETAIL = "/customerInquiries/InquiriesDetailActivity";
    public static final String ACTIVITY_INQUIRIES_MSG_DETAIL = "/customerInquiries/InquiriesDetailMsgActivity";
    public static final String ACTIVITY_INQUIRIES_ORDER_DETAIL = "/customerInquiries/InquiriesOrderDetailActivity";
    public static final String ACTIVITY_INQUIRIES_FEEDBACK = "/customerInquiries/FeedBackActivity";
    public static final String ACTIVITY_INQUIRIES_ORDER_LIST = "/customerInquiries/InquiriesOrderList";
    /**********************客户报修****************************/
    public static final String ACTIVITY_CUSTOMER_REPAIR_DETAIL = "/repairs/RepairsDetailActivity";
    public static final String ACTIVITY_CUSTOMER_ADD_MATERIAL = "/repairs/AddMaterialActivity";

    //客户投诉
    public static final String ACTIVITY_COMPLAIN_PAGING="/complain/ComplainActivity";
    public static final String ACTIVITY_CUSTOMER_COMPLAIN_DETAIL = "/complain/ComplainDetailActivity";
    //不合格单
    public static final String ACTIVITY_DISQUALIFIED = "/disqualified/DisqualifiedActivity";
    public static final String ACTIVITY_DISQUALIFIED_ORDER_LIST = "/disqualified/DisqualifiedOrderList";
    public static final String ACTIVITY_DISQUALIFIED_DETAIL= "/disqualified/DisqualifiedDetailActivity";
    public static final String ACTIVITY_PROPERTY = "/property/PropertyActivity";
    public static final String ACTIVITY_PROPERTY_CREATE = "/property/CreateActivity";
    /**************************公告************************************/
    public static final String ACTIVITY_NOTICE_LIST = "/notice/NoticeListActivity";
    public static final String ACTIVITY_NOTICE_DETAIL = "/notice/NoticeDetailActivity";
    //收费
    public static final String ACTIVITY_TOLL = "/toll/TollActivity";
    public static final String ACTIVITY_TOLL_BUILD = "/toll/TollBuildActivity";
    public static final String ACTIVITY_TOLL_UNIT = "/toll/TollUnitActivity";
    public static final String ACTIVITY_LACK_DETAIL = "/lack/LackDetailActivity";
    public static final String ACTIVITY_PAYMENT_ADVANCE = "/payment/PaymentAdvanceActivity";
    public static final String ACTIVITY_LACK_LIST = "/lack/LackListActivity";
    public static final String ACTIVITY_FEE = "/fee/FeeActivity";
    public static final String ACTIVITY_FEE_SUC = "/feesuc/FeeSucActivity";
    public static final String ACTIVITY_ADD_HOUSER = "/feesuc/AddHouserActivity";
    public static final String ACTIVITY_ADD_WORTH_REMINDER = "/feesuc/AddWorthReminderActivity";
    public static final String ACTIVITY_SET_SIGN = "/feesuc/SetSignActivity";
    public static final String ACTIVITY_SET_SIGN2 = "/feesuc/SetSignActivity2";
//    public static final String ACTIVITY_CREATE_DISQUALIFIED = "/create/CreateDisqualifiedActivity";
    /***********************ACTIVITY REQUEST_CODE********************/
    public static final int ACTIVITY_REQUEST_BLOCK_CHOOSE =101;
    public static final int ACTIVITY_REQUEST_CAMERA_OK =102;
    public static final int ACTIVITY_REQUEST_REQUEST_PIC_PICK = 103;
    public static final int ACTIVITY_REQUEST_SCANNER = 104;
    public static final int ACTIVITY_REQUEST_PERSON_CHOOSE = 105;
    public static final int ACTIVITY_REQUEST_SIGN_IN = 106;
    public static final int ACTIVITY_REQUEST_OPTION = 107;
    public static final int ACTIVITY_REQUEST_REQUEST_PIC_PICK_SECOND = 108;


    public static final String ACTIVITY_COMMUNICATION="/complain/CommunicationActivity";
    /**********************Fragment*********************************/

}



