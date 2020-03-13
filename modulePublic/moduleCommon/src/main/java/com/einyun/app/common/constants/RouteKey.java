package com.einyun.app.common.constants;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.constants
 * @ClassName: RouteKey
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 16:44
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 16:44
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RouteKey {
    public static final String KEY_PATH="path";
    public static final String KEY_PUSH_JUMP="pushJump";
    public static final String KEY_USER_ID="userId";
    public static final String KEY_WEB_URL="webUrl";
    public static final String KEY_WEB_TITLE="webUrlTitle";
    public static final String KEY_PARAMS="params";
    public static final String KEY_TASK_ID="taskId";
    public static final String KEY_CATE_NAME="cateName";
    public static final String KEY_PHONE="phone";
    public static final String KEY_TASK_NODE_ID="taskNodeId";
    public static final String KEY_PRO_INS_ID="proInsId";
    public static final String KEY_ID="ID";
    public static final String KEY_STATE="state";
    public static final String KEY_MAP_SERIALIZABLE="serializable_map";
    public static final String KEY_FRAGEMNT_TAG="fragmentTag";
    public static final String KEY_ORDER_ID="orderId";
    public static final String KEY_ORDER_NO="orderNo";
    public static final String KEY_LINE="orderLine";
    public static final String KEY_RESOUSE="orderResouse";
    public static final String KEY_LIST_TYPE="listType";
    public static final String FRAGMENT_SEND_OWRKORDER_PENDING="SEND_WORKORDER_PENDING";
    public static final String FRAGMENT_SEND_OWRKORDER_DONE="SEND_WORKORDER_DONE";
    public static final String FRAGMENT_WORK_PREVIEW_PLAN="WORK_PREVIEW_PLAN";
    public static final String FRAGMENT_WORK_PREVIEW_PATRO="WORK_PREVIEW_PATRO";
    public static final String KEY_SEND_ORDER_DETAIL="KEY_SEND_ORDER_DETAIL";
    public static final String FRAGMENT_PLAN_OWRKORDER_PENDING="FRAGMENT_PLAN_OWRKORDER_PENDING";
    public static final String FRAGMENT_PLAN_OWRKORDER_DONE="FRAGMENT_PLAN_OWRKORDER_DONE";
    public static final String KEY_ORDER_DETAIL_EXTEN="KEY_ORDER_DETAIL_EXTEN";
    public static final String FRAGMENT_REPAIR_GRAB="FRAGMENT_REPAIR_GRAB";
    public static final String FRAGMENT_REPAIR_WAIT_FOLLOW="FRAGMENT_REPAIR_WAIT_FOLLOW";
    public static final String FRAGMENT_REPAIR_WAIT_FEED="FRAGMENT_REPAIR_WAIT_FEED";
    public static final String FRAGMENT_REPAIR_ALREADY_FOLLOW="FRAGMENT_REPAIR_ALREADY_FOLLOW";
    public static final String FRAGMENT_REPAIR_ALREDY_DONE="FRAGMENT_REPAIR_ALREDY_DONE";
    public static final String FRAGMENT_REPAIR_COPY_ME="FRAGMENT_REPAIR_COPY_ME";
    public static final String KEY_TIAO_XIAN_ID="tiaoXianId";
    public static final String KEY_PARENT_ID="parentId";
    public static final String KEY_DIVIDE_ID="divideId";
    public static final String KEY_DIVIDE_NAME="divideName";
    public static final String KEY_PROJECT_ID="projectId";
    public static final String KEY_LATER_ID="KEY_LATER_ID";
    public static final String KEY_CLOSE_ID="KEY_CLOSE_ID";
    public static final String KEY_PATROL_TIME_WORKNODE ="workNode";
    public static final String KEY_QR_ID="qrId";
    //工单列表
    public static final String ORDER_LIST_DISTRIBUTE="ORDER_LIST_DISTRIBUTE";
    public static final String ORDER_LIST_PLAN="ORDER_LIST_PLAN";
    public static final String ORDER_LIST_PATRO="ORDER_LIST_PATRO";
    public static final String ORDER_LIST_REPAIR="ORDER_LIST_REPAIR";
    public static final String ORDER_LIST_COMPLAIN="ORDER_LIST_COMPLAIN";
    public static final String ORDER_LIST_ASK="ORDER_LIST_ASK";
    public static final String ORDER_LIST_UNWELL="ORDER_LIST_UNWELL";
    public static final String KEY_PLAN = "KEY_PLAN";
    public static final String KEY_SEND_ORDER = "KEY_SEND_ORDER";
    public static final String KEY_CUSTOMER_COMPLAIN = "KEY_CUSTOMER_COMPLAIN";
    public static final String KEY_CUSTOMER_REPAIRS = "KEY_CUSTOMER_REPAIRS";
    //选择处理人
    public static final String KEY_ORG_ID = "org_id";
    public static final String KEY_DIM_CODE = "dimCode";

    //选择处理人
    public static final String KEY_ORG_ID_LIST = "orgIdList";

    public static final String KEY_ROLE_ID_LIST = "roleIdList";

    public static final String KEY_WORK_ORDER_LIST_TYPE = "KEY_WORK_ORDER_LIST_TYPE";
    //用户信息
    public static final String ACCOUNT = "account";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";

    //审批
    public static final String APPROVAL_LIST_FROM = "APPROVAL_LIST_FROM";
    public static final String APPROVAL_ITEM_DATA="data";
    public static final String APPROVAL_DETAIL_TYPE_VALUE="typeValue";
    //客户问询
    public static final String FRAGMENT_TO_FOLLOW_UP = "FRAGMENT_TO_FOLLOW_UP";
    public static final String FRAGMENT_TO_FEED_BACK = "FRAGMENT_TO_FEED_BACK";
    public static final String FRAGMENT_HAVE_TO_FOLLOW_UP = "FRAGMENT_HAVE_TO_FOLLOW_UP";
    public static final String FRAGMENT_TRANSFERRED_TO= "FRAGMENT_TRANSFERRED";//已办结
    public static final String FRAGMENT_COPY_ME = "FRAGMENT_COPY_ME";
    public static final String FRAGMENT_INQUIRIES_ORDER_LIST = "FRAGMENT_INQUIRIES_ORDER_LIST";//问询工单列表
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    //三大类强制关闭midurl
    public static final String KEY_MID_URL = "KEY_MID_URL";
    public static final String KEY_MID_URL_INQUIRIES = "customerEnquiry";
    public static final String KEY_MID_URL_COMPLAIN = "taskRun";
    public static final String KEY_MID_URL_REPAIRS= "customerRepair";
    //客户报修状态
    public static final String REPAIR_STATUS_SEND_ORDER = "ConfirmCateAndAssignOrBrab";
    public static final String REPAIR_STATUS_SEND_ORDER_LATE = "OvertimeMandatoryAssign";
    public static final String REPAIR_STATUS_RESPONSE = "Response";
    public static final String REPAIR_STATUS_HANDLE = "Handle";
    public static final String REPAIR_STATUS_EVALUATE= "ReturnVisit";
    public static final String REPAIR_STATUS_WAIT_GRAB= "WorkOrderPoolGrab";
    //    客服三类转派
    public static final String KEY_CUSTOMER_RESEND_ORDER = "KEY_CUSTOMER_RESEND_ORDER";
    //有偿
    public static final String KEY_PAID="1";
    //无偿
    public static final String KEY_NOT_PAID="0";
    //已解决
    public static final int KEY_IS_SOLVED=1;
    //未解决
    public static final int KEY_NO_SOLVED=0;

    //不合格单
    public static final String FRAGMENT_DISQUALIFIED_WAIT_FOLLOW = "FRAGMENT_DISQUALIFIED_WAIT_FOLLOW";
    public static final String FRAGMENT_DISQUALIFIED_HAD_FOLLOW = "FRAGMENT_DISQUALIFIED_HAD_FOLLOW";
    public static final String FRAGMENT_DISQUALIFIED_ORDER_LIST = "FRAGMENT_DISQUALIFIED_ORDER_LIST";
    public static final String KEY_IS_UNQUALITY = "KEY_IS_UNQUALITY";
    public static final String KEY_MODEL_DATA = "KEY_MODEL_DATA";
    public static final String CODE = "CODE";
    //工单列表-客服三大工单状态
    public static final String LIST_STATUS_SEND_ORDER = "for_comfirm";
    public static final String LIST_STATUS_RESPONSE = "for_response";
    public static final String LIST_STATUS_HANDLE = "dealing";
    public static final String LIST_STATUS_EVALUATE= "return_visit";
    public static final String LIST_STATUS_WAIT_GRAB= "for_grab";
    public static final String LIST_STATUS_CLOSED= "closed";

    //运营收缴率
    public static final String FRAGMENT_PERCENT_GET = "FRAGMENT_PERCENT_GET";
    public static final String FRAGMENT_PERCENT_OWE = "FRAGMENT_PERCENT_OWE";
    public static final String ORGCODE = "ORGCODE";
    //条线code
    public static final String ORDER = "order_classification";
    public static final String MULTI = "multi_battalion_classification";
    public static final String ENGINER = "engineering_classification";
    public static final String ENVIRONMENTAL = "environmental_classification";
    public static final String SERVICE = "customer_service_classification";

    //收费
    public static final String HOUSE_ID = "houseid";
    public static final String HOUSE_FEE_ID = "houseFeeid";
    public static final String HOUSE_TITLE = "house_title";
    public static final String KEY_ALL_NAME = "ALL_NAME";
    public static final String KEY_CLIENT_NAME = "CLIENT_NAME";
    //消息中心
    public static final String KEY_START_TIME = "START_TIME";
    public static final String KEY_END_TIME = "EDN_TIME";
}
