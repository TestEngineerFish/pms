package com.einyun.app.library.uc.usercenter.net

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.usercenter.net
 * @ClassName:      URLs
 * @Description:    user-center urls
 *                  《客服工单流程的接口v1.2》
 * @Author:         chumingjun
 * @CreateDate:     2019/10/14 17:43
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/14 17:43
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class URLs {
    companion object {
        const val DOMAIN = "user-center"
        //组织架构数据列表
        const val URL_USER_CENTER_USER_LIST: String = "/$DOMAIN/api/usercenter/v1/ucOrg/userList/"

        //获取组织列表
        const val URL_USER_CENTER_CHILD_BY_USER_ID: String = "/$DOMAIN/api/usercenter/v1/ucOrg/queryChildrenByUserId"

        //获取满意度
        const val URL_STATISFACTION: String = "/$DOMAIN/api/Satisfaction/v1/satisfaction/getAllSatisfactionNoAuth?time="

    }
}