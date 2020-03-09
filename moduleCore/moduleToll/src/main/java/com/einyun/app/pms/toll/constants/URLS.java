package com.einyun.app.pms.toll.constants;

public class URLS {
    public static final String uatBaseUrl="";
    public static final String relaseBasesUrl="fee-center-api";
//    public static final String relaseBasesUrl="";
    //获取问询类型
    public static final String URL_GET_QUIRIES_TYPES ="workOrder/workOrder/bizData/v1/getBaseList?categoryKey=workorder_type_and_line";
    //获取欠费信息 栋 单元  房产通用
    public static final String URL_GET_FEE_INFO =relaseBasesUrl+"/payment/getFeeByQuery";
    //一键催缴
    public static final String URL_GET_FEE_ALL_WORTH =relaseBasesUrl+"/payment/overduePaymentCall";

    //获取欠费详情信息
    public static final String URL_GET_FEE_DETAIL_INFO =relaseBasesUrl+"/payment/getFeeInfoByHouse";
    //获取预交费列表
    public static final String URL_GET_FEE_ADVANCE =relaseBasesUrl+"/payment/advanceFeeItemIn";
    //跳缴验证
    public static final String URL_GET_FEE_JUMP_VERIFY =relaseBasesUrl+"/payment/check_jump";
    //收款二维码
    public static final String URL_GET_FEE_QR_CODE=":10018/payInfo/getQRCode?orderId=";
    //预缴验证
    public static final String URL_GET_FEE_JUMP_ADVANCE_VERIFY =relaseBasesUrl+"/payment/prepaycheck_in";
    //生成订单
    public static final String URL_GET_FEE_CREATE_ORDER=relaseBasesUrl+"/payInfo/immediatePayment";
    //查询已付费明细
    public static final String URL_GET_FEE_QUERY_FEEDDETAILS=relaseBasesUrl+"/payment/paidamountDetailOut";
    //查询订单状态
    public static final String URL_GET_FEE_QUERY_ORDER_STATE=relaseBasesUrl+"/payInfo/confirmationOrder/";
    //获取欠费 详单
    public static final String URL_GET_FEE_LACK_DETAIL_LIST =relaseBasesUrl+"/payment/uppaidAmountOutV2";
    //获取feedid
    public static final String URL_GET_FEE_DIVIDE_ID =relaseBasesUrl+"/payInfo/queryIdByMdmid/";
    //获取Defauft分期Id
    public static final String URL_GET_DEFAUFT_DIVIDE_ID ="/uc/api/org/v1/getCurrentOrg/";
    //获取Defauft分期名字
    public static final String URL_GET_DEFAUFT_DIVIDE_NAME ="/user-center/api/usercenter/v1/ucOrg/userList/";
    //获取上次催缴时间
    public static final String URL_GET_LAST_WORTH_TIME =relaseBasesUrl+"/payment/getOverduePaymentByDivideId/";

}
