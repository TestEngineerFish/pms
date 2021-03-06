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
    companion object {
        const val DOMAIN = "resource-workorder"

        //2.6待办统计-计划、巡查、派工单 GETres-order/plan/waitCount
        const val URL_RESOURCE_WORKORDER_WIAIT_COUNT = "/$DOMAIN/res-order/plan/waitCount"
        //2.7计划工单-待办列表
        const val URL_RESOURCE_WORKORDER_WAIT_PROCESS_LIST =
            "/$DOMAIN/res-order/plan/wait-process-list"
        //2.14计划工单-已办列表
        const val URL_RESOURCE_WORKORDER_PLAN_DONE = "/$DOMAIN/res-order/plan/done"
        //2.54巡查工单-待办
        const val URL_RESOURCE_WORKORDER_PATROL_WAIT = "/$DOMAIN/res-order/patrol/wait"
        //2.55巡查工单-已办
        const val URL_RESOURCE_WORKORDER_PATROL_DONE = "/$DOMAIN/res-order/patrol/done"
        //巡查工单处理
        const val URL_RESOURCE_WORKORDER_PATROL_SUBMIT = "/$DOMAIN/res-order/patrol/process"
        //计划工单处理
        const val URL_RESOURCE_WORKORDER_PLAN_SUBMIT = "/$DOMAIN/res-order/plan/process"
        //计划工单强制扫码
        const val URL_RESOURCE_WORKORDER_PLAN_QRCODE = "/resource/resource-api/v1/resource-basic-info/get"
        //2.31派工单-待办列表
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_WAIT = "/$DOMAIN/res-order/distribute/wait"
        //2.32派工单-已办列表
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DONE = "/$DOMAIN/res-order/distribute/done"
        //2.30派工单-列表（根据地块id、条线编码）
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_LIST_BY_RES =
            "/$DOMAIN/res-order/distribute/listByResource"
        //2.17计划工单-地块和条线组合查询
        const val URL_RESOURCE_WORKORDER_PLAN_QUERY_LIST = "/$DOMAIN/res-order/plan/queryList"
        //2.62巡查工单-地块和条线组合查询
        const val URL_RESOURCE_WORKORDER_PATROL_QUEYR_LIST = "/$DOMAIN/res-order/patrol/queryList"
        //2.56巡查工单-待办详情
        const val URL_RESOURCE_WORKORDER_PATROL_WAIT_DETAIL = "/$DOMAIN/res-order/patrol/waitDetail"
        //2.60巡查工单-闭单
        const val URL_RESOURCE_WORKORDER_PATROL_CLOSE = "/$DOMAIN/res-order/patrol/close"
        //2.9巡查工单-已办详情
        //2.63计划工单-工单详情
        const val URL_RESOURCE_WORKORDER_PLAN_LIST_DETIAL = "/$DOMAIN/res-order/plan/waitDetail"
        //2.9计划工单-已办详情
        const val URL_RESOURCE_WORKORDER_PATROL_DONE_DETAIL = "/$DOMAIN/res-order/patrol/doneDetail"
        //2.58巡查工单-处理
        const val URL_RESOURCE_WORKORDER_PATROL_PROCESS = "/$DOMAIN/res-order/patrol/process"
        //2.63巡查工单-工单详情
        const val URL_RESOURCE_WORKORDER_PATROL_LIST_DETIAL =
            "/$DOMAIN/res-order/patrol/listDetail/"
        //2.22派工单-新增工单
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_NEW = "/$DOMAIN/res-order/distribute/new"
        //计划工单完成数量 GET(userId=)
        const val URL_RESOURCE_WORKORDER_PLAN_ORDER_NUMS_DONE =
            "/$DOMAIN/res-order/plan/getOrderDoneCount"
        //工单是否关闭
        const val URL_RESOURCE_WORKORDER_IS_CLOSE = "/$DOMAIN/res-order/distribute/isClosed"
        //派工单代办详情 GET
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DETIAL_INFO =
            "/$DOMAIN/res-order/distribute/detailById/"
        //派工单代办详情 GET
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DETIAL =
            "/$DOMAIN/res-order/distribute/detail?taskId="

        //派工单已办详细 POST   param["proInsId"] = proInsID param["taskNodeId"] = taskNodeID
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DONE_DETAIL =
            "/$DOMAIN/res-order/distribute/doneDetail"

        //派工单已办详细 POST   param["proInsId"] = proInsID param["taskNodeId"] = taskNodeID
        const val URL_RESOURCE_WORKORDER_PLAN_DONE_DETAIL = "/$DOMAIN/res-order/plan/doneDetail"
        //派工单接单
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_RESPONSE =
            "/$DOMAIN/res-order/distribute/response"
        //派工单处理
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_PROCESS =
            "/$DOMAIN/res-order/distribute/process"
        //派工单-验收
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_APPLY = "/$DOMAIN/res-order/distribute/check"
        //派工单-批复
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_REPLY = "/$DOMAIN/res-order/distribute/reply/{taskId}"
        //获取条线
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_TIAOXIAN =
            "/portal/sys/sysType/v1/getTypesListByKey?typeKey=RESOURCE_TYPE"
        //获取工单类型
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_ORDER_TYPE =
            "/portal/sys/dataDict/v1/getByTypeKey"
        //获取资源类型
        const val URL_RESOURCE_WORKORDER_DISTRIBUTE_DISPATCH =
            "/resource/resource-api/v1/resource-basic-info/dispatch"
        //获取组织架构
        const val URL_SELECT_BY_ORGNIZATION = "/user-center/api/usercenter/v1/ucOrg/getAllDimOrgListByOrg"
        //获取审批角色
        const val URL_SELECT_BY_JOB = "/uc/api/role/v1/roles/getAll"
        //转派
        const val URL_RESEND_ORDER = "/$DOMAIN/res-order/distribute/direct"
        //客服类转派
        const val URL_RESEND_ORDER_CUS = "/bpm-runtime/runtime/task/v1/delegate"
        //申请闭单
        const val URL_CLOSE_ORDER = "/$DOMAIN/res-order/distribute/close"
        //派工单-延期
        const val URL_EXTEN = "/$DOMAIN/res-order/distribute/exten"
        //计划工单闭单
        const val URL_PLAN_CLOSE_ORDER = "/$DOMAIN/res-order/plan/close"
        //计划工单延期
        const val URL_PLAN_EXTEN = "/$DOMAIN/res-order/plan/exten"
        //历史流程
        const val URL_HIDTROY = "bpm-runtime/runtime/instance/v1/instanceFlowOpinions?instId="
        //工作预览-计划工单
        const val URL_WORK_PREVIEW_PLAN_ORDER = "resource/api/resource/v1/workPlanPreview/list"
        //工作预览-巡查工单
        const val URL_WORK_PREVIEW_PATRO_ORDER =
            "resource/api/resource/v1/inspectionWorkOrderPreview/list"

        /**
         * 通用强制关闭
         */
        const val URL_WORK_ORDER_FORCE_CLOSE ="/$DOMAIN/res-order/{workOrder}/close"

        /**
         * 客户端通用延期
         */
        const val URL_WORK_ORDER_FORCE_POSTPONE_CUSTOME ="/workOrder/workOrder/workOrderInnerAudit/v1/startSubProcess"

        /**
         * 客户端通用获取延期信息
         */
        const val URL_WORK_ORDER_FORCE_POSTPONE_CUSTOME_INFO ="/workOrder/workOrder/task/v1/getDelayByCondition"

        /**
         * 通过申请延期
         */
        const val URL_WORK_ORDER_FORCE_POSTPONE ="/$DOMAIN/res-order/{workOrder}/exten"

        //客户问询申请闭单
        const val URL_CLOSE_CUSTOMER_ORDER = "/workOrder/workOrder/{workOrder}/v1/applyFclose"
        //工作预览筛选跳线数据
        const val URL_ORDER_PREVIEW_SELECT = "/portal/sys/sysType/v1/queryLinkByGroupKey?groupKey=RESOURCE_TYPE"
        /**
         * 工单列表-派工单
         * */
        const val URL_ORDER_LIST_DISTRIBUTE="/$DOMAIN/res-order/distribute/v2/listByResource"

        /**
         *工单列表-计划工单
         * */
        const val URL_ORDER_LIST_PLAN="/$DOMAIN/res-order/plan/v2/queryList"

        /**
         * 工单列表-巡查工单
         * */
        const val URL_ORDER_LIST_PATRO="$DOMAIN/res-order/patrol/v2/queryList"

        /**
         * 工单列表-客户报修
         * */
        const val URL_ORDER_LIST_REPAIR="workOrder/workOrder/task/v1/getWorkListBydefKeyForApp?defKey=customer_repair_flow"

        /**
         * 工单列表-客户投诉
         * */
        const val URL_ORDER_LIST_COMPLAIN="workOrder/workOrder/task/v1/getWorkListBydefKeyForApp?defKey=customer_complain_flow"

        /**
         * 工单列表-客户问询
         * */
        const val URL_ORDER_LIST_ASK="workOrder/workOrder/task/v1/getWorkListBydefKeyForApp?defKey=customer_enquiry_flow"

        /**
         * 工单列表-获取nodeid
         * */
        const val URL_ORDER_LIST_GET_NODEID="/workOrder/workOrder/task/v1/getInstNodeByID"

    }
}
