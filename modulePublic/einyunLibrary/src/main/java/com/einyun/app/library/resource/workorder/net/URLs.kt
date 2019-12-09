package com.einyun.app.library.resource.workorder.net

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.net
 * @ClassName:      URLs
 * @Description:     资源工单接口
 *                  请参考《资源工单接口文档》
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 09:47
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 09:47
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class URLs {
    companion object{
        const val DOMAIN="resource-workorder"

        //2.6待办统计-计划、巡查、派工单 GETres-order/plan/waitCount
        const val URL_RESOURCE_WORKORDER_WIAIT_COUNT="/$DOMAIN/res-order/plan/waitCount"
        //2.7计划工单-待办
        const val URL_RESOURCE_WORKORDER_WAIT_PROCESS_LIST="/$DOMAIN/res-order/plan/wait-process-list"
        //2.14计划工单-已办
        const val URL_RESOURCE_WORKORDER_PLAN_DONE="/$DOMAIN/res-order/plan/done"
        //2.54巡查工单-待办
        const val URL_RESOURCE_WORKORDER_PATROL_WAIT="/$DOMAIN/res-order/patrol/wait"
        //2.55巡查工单-已办
        const val URL_RESOURCE_WORKORDER_PATROL_DONE="/$DOMAIN/res-order/patrol/done"
        //2.31派工单-待办列表
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_WAIT="/$DOMAIN/res-order/distribute/wait"
        //2.32派工单-已办列表
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DONE="/$DOMAIN/res-order/distribute/done"
        //2.30派工单-列表（根据地块id、条线编码）
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_LIST_BY_RES="/$DOMAIN/res-order/distribute/listByResource"
        //2.17计划工单-地块和条线组合查询
        const val URL_RESOURCE_WORKORDER_PLAN_QUERY_LIST="/$DOMAIN/res-order/plan/queryList"
        //2.62巡查工单-地块和条线组合查询
        const val URL_RESOURCE_WORKORDER_PATROL_QUEYR_LIST="/$DOMAIN/res-order/patrol/queryList"
        //2.56巡查工单-待办详情
        const val URL_RESOURCE_WORKORDER_PATROL_WAIT_DETAIL="/$DOMAIN/res-order/patrol/waitDetail"
        //2.60巡查工单-闭单
        const val URL_RESOURCE_WORKORDER_PATROL_CLOSE="/$DOMAIN/res-order/patrol/close"
        //2.9计划工单-已办详情
        const val URL_RESOURCE_WORKORDER_PATROL_DONE_DETAIL="/$DOMAIN/res-order/patrol/doneDetail"
        //2.58巡查工单-处理
        const val URL_RESOURCE_WORKORDER_PATROL_PROCESS="/$DOMAIN/res-order/patrol/process"
        //2.63巡查工单-工单详情
        const val URL_RESOURCE_WORKORDER_PATROL_LIST_DETIAL="/$DOMAIN/res-order/patrol/listDetail/"
        //2.22派工单-新增工单
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_NEW="/$DOMAIN/res-order/distribute/new"
        //计划工单完成数量 GET(userId=)
        const val URL_RESOURCE_WORKORDER_PLAN_ORDER_NUMS_DONE="/$DOMAIN/res-order/plan/getOrderDoneCount"
        //派工单是否关闭
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_CLOSE="/$DOMAIN/res-order/distribute/isClosed"

        //派工单代办详情 GET
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DETIAL="/$DOMAIN/res-order/distribute/detail?taskId="

        //派工单已办详细 POST   param["proInsId"] = proInsID param["taskNodeId"] = taskNodeID
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DONE_DETAIL="/$DOMAIN/res-order/distribute/doneDetail"
        //获取条线
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_TIAOXIAN="/portal/sys/sysType/v1/getTypesListByKey?typeKey=RESOURCE_TYPE"
        //获取工单类型
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_ORDER_TYPE="/portal/sys/dataDict/v1/getByTypeKey?typeKey=pgdlx"
        //获取资源类型
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DISPATCH="/resource/resource-api/v1/resource-basic-info/dispatch"
        //获取组织架构
        const val URL_SELECT_BY_ORGNIZATION="/uc/api/org/v1/org/get"


    }
}
