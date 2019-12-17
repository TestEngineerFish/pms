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

        //获取工作状态
        const val URL_GET_WORK_STATUS: String = "/$DOMAIN/api/usercenter/v1/ucWorkHistory/getUserHisStatus"

        //更新工作状态
        const val URL_UPDATE_WORK_STATUS: String = "/$DOMAIN/api/usercenter/v1/ucWorkHistory/save"

        //获取处理人
        const val URL_GET_DISPOSE_PERSON: String = "/$DOMAIN/api/usercenter/v1/ucUser/users/getByJobCodeAndOrgIdAndDimCodeDeeplyWithPost"

        //通过条件搜索负责人
        const val URL_SEARCH_USER_BY_CONDITION: String = "/$DOMAIN/api/usercenter/v1/ucUser/searchUserByCondition"

        const val URL_GET_HOUSE_BY_CONDITION:String = "/$DOMAIN/grid-api/grid-basic-info/getHouseByCondition"

    }
}