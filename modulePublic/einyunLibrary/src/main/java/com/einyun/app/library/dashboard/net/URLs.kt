package com.einyun.app.library.dashboard.net

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.dashboard.net
 * @ClassName:      URLs
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/11/4 0004 上午 11:19
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/11/4 0004 上午 11:19
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class URLs {
    companion object {
        const val DOMAIN = "dashboard"
        //工单处理情况总览
        const val URL_DASHBOARD_WORK_ORDER_DATA = "/$DOMAIN/dashboard/api/workOrderData"
        //运营收缴率
        const val URL_DASHBOARD_OPERATE_CAPTURE_RATE = "/app/fee/proxy/api/fee/fee-center/api/getTotalRate"
        //首页权限配置
        const val URL_DASHBOARD_USER_MENU = "/portal/sys/sysMenu/v1/getCurrentUserMenu"
        //全部收费项目
        const val URL_ALL_CHARGED_PROJECT = "app/fee/proxy/api/fee/fee-center/api/getAmountDetail"
    }
}