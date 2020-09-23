package com.einyun.app.pms.toll.constants;

import com.einyun.app.common.BuildConfig;

public class URLS {
//    public static final String uatBaseUrl="";
//    public static final String relaseBasesUrl="fee-center-api";//生产环境
    public static final String relaseBasesUrl= BuildConfig.FEE_MID_URL;//uat环境
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
    public static final String URL_GET_FEE_QR_CODE=relaseBasesUrl+"/payInfo/getQRCode?orderId=";
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
    //获取Defauft园区Id
    public static final String URL_GET_DEFAUFT_DIVIDE_ID ="/uc/api/org/v1/getCurrentOrg/";
    //获取Defauft园区名字
    public static final String URL_GET_DEFAUFT_DIVIDE_NAME ="/user-center/api/usercenter/v1/ucOrg/userList/";
    //获取上次催缴时间
    public static final String URL_GET_LAST_WORTH_TIME =relaseBasesUrl+"/payment/getOverduePaymentByDivideId/";
    //根据手机号获取姓名
    public static final String URL_GET_NAME_FROM_PHONE =relaseBasesUrl+"/v1/client/getClientByPhone";
    //添加住户
    public static final String URL_GET_ADD_HOUSER =relaseBasesUrl+"/v1/house/bindHouseOwner";
    //添加住户
    public static final String URL_GET_DICKEY =relaseBasesUrl+"/v1/house/getOwnerType/";
    //获取标签
    public static final String URL_GET_SIGN=relaseBasesUrl+"/v1/house/getLableByHouse";
    //打标签
    public static final String URL_SET_SIGN =relaseBasesUrl+"/v1/house/AddLable";
    //获取租户logo
    public static final String URL_GET_TENANT_LOGO ="/datasource/api/saas/v1/tenant/get/";

}
